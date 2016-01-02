package thescore.controller;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import thescore.Utility;
import thescore.editor.TeamEditor;
import thescore.model.League;
import thescore.model.LeagueTeam;
import thescore.model.Match;
import thescore.model.Team;
import thescore.service.LeagueService;
import thescore.service.MatchService;
import thescore.service.TeamService;

@Controller
@RequestMapping(value = "/league")
public class LeagueController {
	
	private @Autowired LeagueService leagueService;
	private @Autowired TeamService teamService;
	private @Autowired MatchService matchService;
	
	private @Autowired TeamEditor teamEditor;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<League> leagues = leagueService.findAllLeagues();
		model.addAttribute("leagues", leagues);
		return "league/list";
	}
	
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String create(ModelMap model) {
		League league = new League();
		model.addAttribute("league", league);
		model.addAttribute("edit", false);
		model.addAttribute("teams", teamService.findAllTeams());
		model.addAttribute("existingTeamPKs", new ArrayList<Integer>());
		return "league/dataentry";
	}
	
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String save(@Valid League league, BindingResult result, ModelMap model,
			@RequestParam(required = false) String[] teams) {
		if (result.hasErrors()) {
			Utility.parseErrors(result, model);
			model.addAttribute("teams", teamService.findAllTeams());
			return "league/dataentry";
		}

		leagueService.saveLeague(league, teams);
		model.addAttribute("infoMessage", "League " + league.getDisplayString() + " registered successfully");
		return "redirect:/league/list";
	}
	
	@RequestMapping(value = { "/edit-{id}-league" }, method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model) {
        League league = leagueService.findById(id);
        model.addAttribute("league", league);
        model.addAttribute("edit", true);
        model.addAttribute("teams", teamService.findAllTeams());
        
        List<LeagueTeam> leagueTeams = leagueService.findAllLeagueTeams(league.getId());
        List<Integer> existingTeamPKs = new ArrayList<Integer>();
        
        for(LeagueTeam leagueTeam : leagueTeams){
        	existingTeamPKs.add(leagueTeam.getTeam().getId());
        }
        
        model.addAttribute("existingTeamPKs", existingTeamPKs);
        return "league/dataentry";
    }
     
    @RequestMapping(value = { "/edit-{id}-league" }, method = RequestMethod.POST)
    public String update(@Valid League league, BindingResult result, ModelMap model, @PathVariable Integer id,
            @RequestParam(required = false) String[] teams) {
        if (result.hasErrors()) {
        	Utility.parseErrors(result, model);
            return "league/dataentry";
        }
 
        leagueService.updateLeague(league, teams);
        model.addAttribute("success", "League " + league.getDisplayString()  + " updated successfully");
        return "redirect:/league/list";	
    }
 
    @RequestMapping(value = { "/delete-{id}-league" }, method = RequestMethod.GET)
    public String delete(@PathVariable Integer id) {
        leagueService.deleteLeagueById(id);
        return "redirect:/league/list";
    }
    
    @RequestMapping(value = { "/view-{id}-league" }, method = RequestMethod.GET)
    public String view(@PathVariable Integer id, ModelMap model) {
        League league = leagueService.findById(id);
        model.addAttribute("league", league);
        return "league/info";
    }
    
    @RequestMapping(value = { "/lock-{id}-league" }, method = RequestMethod.GET)
	public String lock(@PathVariable Integer id, ModelMap model) {
		League league = leagueService.findById(id);
		List<LeagueTeam> leagueTeams = leagueService.findAllLeagueTeams(league.getId());
		
		if(leagueTeams != null && leagueTeams.size() > 1){
			league.setLockedDate(new Date());
			leagueService.updateLeague(league);
		} else {
			model.addAttribute("errorMessage", "At least two teams is required on locking a league.");
		}
		
		List<League> leagues = leagueService.findAllLeagues();
		model.addAttribute("leagues", leagues);
		model.addAttribute("infoMessage", "League " + league.getDisplayString() + " has been locked.");
		return "league/list";
	}
    
    @RequestMapping(value = "/on-league-end", method = RequestMethod.GET)
	public @ResponseBody String beforeLeagueEnd(@RequestParam("leagueId") Integer leagueId) {
		List<SimpleEntry<Integer, String>> list = new ArrayList<SimpleEntry<Integer, String>>();;
		
		if(leagueId != null){
			for(LeagueTeam leagueTeam : leagueService.findAllLeagueTeams(leagueId)){
				list.add(new SimpleEntry<Integer, String>(leagueTeam.getTeam().getId(), leagueTeam.getTeam().getDisplayString()));
			}
			
			try {
				return mapper.writeValueAsString(list);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
    
	@RequestMapping(value = { "/end-{id}-league" }, method = RequestMethod.GET)
	public String end(@PathVariable Integer id, ModelMap model) {
		League league = leagueService.findById(id);
		
		league.setEndDate(new Date());
		leagueService.updateLeague(league);
		
		List<League> leagues = leagueService.findAllLeagues();
		model.addAttribute("leagues", leagues);
		model.addAttribute("infoMessage", "League " + league.getDisplayString() + " has concluded.");
		return "league/list";
	}
	
	@RequestMapping(value = { "/generate-{id}-league" }, method = RequestMethod.GET)
	public String generateMatches(@PathVariable Integer id, ModelMap model) {
		League league = leagueService.findById(id);
		
		if(league.getGenerated() == false){
			league.setGenerated(true);
			leagueService.updateLeague(league);
			
			List<LeagueTeam> leagueTeams = leagueService.findAllLeagueTeams(league.getId());
			generateMatchUps(league, leagueTeams);
		}
		
		List<League> leagues = leagueService.findAllLeagues();
		model.addAttribute("leagues", leagues);
		model.addAttribute("infoMessage", "Round robin matches for league " + league.getDisplayString() + " has been generated.");
		return "league/list";
	}
	
	private void generateMatchUps(League league, List<LeagueTeam> leagueTeams){
		for(int i = 0; i < leagueTeams.size(); i ++){
			for(int j = i + 1; j < leagueTeams.size(); j ++){
				Match match = new Match();
				match.setLeague(league);
				match.setTeamA(leagueTeams.get(i).getTeam());
				match.setTeamB(leagueTeams.get(j).getTeam());
				match.setTime(new Date());
				
				matchService.saveMatch(match);
			}
		}
	}
    
    @InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.registerCustomEditor(Team.class, teamEditor);
	}
	
}