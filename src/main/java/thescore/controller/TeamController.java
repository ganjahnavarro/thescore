package thescore.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import thescore.Utility;
import thescore.model.League;
import thescore.model.Match;
import thescore.model.Team;
import thescore.model.computation.PerformanceComputation;
import thescore.service.LeagueService;
import thescore.service.MatchService;
import thescore.service.PlayerPerformanceService;
import thescore.service.PlayerService;
import thescore.service.TeamService;

@Controller
@RequestMapping("/team")
public class TeamController {
	
	private @Autowired TeamService teamService;
	private @Autowired PlayerService playerService;
	private @Autowired MatchService matchService;
	private @Autowired LeagueService leagueService;
	private @Autowired PlayerPerformanceService playerPerformanceService;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<Team> teams = teamService.findAllTeams();
		model.addAttribute("teams", teams);
		return "team/list";
	}
	
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String create(ModelMap model) {
		Team team = new Team();
		model.addAttribute("team", team);
		model.addAttribute("edit", false);
		model.addAttribute("actionParam", "/team/new?");
		return "team/dataentry";
	}
	
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String save(@Valid Team team, BindingResult result, ModelMap model,
			@RequestParam CommonsMultipartFile fileUpload) {
		if (fileUpload != null && fileUpload.getOriginalFilename() != null
    			&& !fileUpload.getOriginalFilename().isEmpty()) {
			team.setImageFileName(fileUpload.getOriginalFilename());
			team.setImage(fileUpload.getBytes());
		}

		if (result.hasErrors()) {
			Utility.parseErrors(result, model);
			model.addAttribute("actionParam", "/team/new?");
			return "team/dataentry";
		}

		teamService.saveTeam(team);
		model.addAttribute("infoMessage", "Team " + team.getDisplayString() + " registered successfully");
		return "redirect:/team/list";
	}
	
	@RequestMapping(value = { "/edit-{id}-team" }, method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model) {
		Team team = teamService.findById(id);
		model.addAttribute("team", team);
		model.addAttribute("edit", true);
		model.addAttribute("actionParam", "/team/edit-" + id + "-team?");
        return "team/dataentry";
    }
     
    @RequestMapping(value = { "/edit-{id}-team" }, method = RequestMethod.POST)
    public String update(@Valid Team team, BindingResult result, ModelMap model, @PathVariable Integer id,
            @RequestParam CommonsMultipartFile fileUpload) {
    	Boolean updateImage = false;
    	
    	if (fileUpload != null && fileUpload.getOriginalFilename() != null
    			&& !fileUpload.getOriginalFilename().isEmpty()) {
			team.setImageFileName(fileUpload.getOriginalFilename());
			team.setImage(fileUpload.getBytes());
			updateImage = true;
		}
    	
        if (result.hasErrors()) {
        	Utility.parseErrors(result, model);
        	model.addAttribute("actionParam", "/team/edit-" + id + "-team?");
            return "team/dataentry";
        }
 
        teamService.updateTeam(team, updateImage);
        model.addAttribute("success", "Team " + team.getDisplayString()  + " updated successfully");
        return "redirect:/team/list";	
    }
 
    @RequestMapping(value = { "/delete-{id}-team" }, method = RequestMethod.GET)
    public String delete(@PathVariable Integer id, ModelMap model) {
    	try{
    		teamService.deleteTeamById(id);
    	} catch (ConstraintViolationException e){
    		model.addAttribute("errorMessage", "This record can't be deleted because it is referenced by other records.");
    		List<Team> teams = teamService.findAllTeams();
    		model.addAttribute("teams", teams);
    		return "team/list";
    	}
        return "redirect:/team/list";
    }
    
    @RequestMapping(value = { "/view-{id}-team" }, method = RequestMethod.GET)
    public String view(@PathVariable Integer id, ModelMap model,
    		@RequestParam(required = false) Boolean allLeague,
    		@RequestParam(required = false) Boolean allMatch) {
    	Team team = teamService.findById(id);

		Integer leagueCount = allLeague != null && allLeague ? null : 5;
		Integer matchCount = allMatch != null && allMatch ? null : 5;
		
		model.addAttribute("team", team);
		model.addAttribute("championships", leagueService.findChampionships(team.getId()));
		model.addAttribute("players", playerService.findPlayersByTeamId(team.getId()));
		model.addAttribute("matches", matchService.findMatchesByTeamId(team.getId()));
		
		model.addAttribute("overAllRecords", playerPerformanceService.findTeamOverallPerformanceComputations(team.getId()));
        model.addAttribute("perLeagueRecords", generateLeagueRecords(team, leagueCount));
        model.addAttribute("perMatchRecords", generatePerMatchRecords(team, matchCount));
        
        model.addAttribute("allLeague", allLeague);
        model.addAttribute("allMatch", allMatch);
        
        return "team/info";
    }
    
	private Map<Match, List<PerformanceComputation>> generatePerMatchRecords(Team team, Integer maxResult) {
		Map<Match, List<PerformanceComputation>> matchRecords = new LinkedHashMap<Match, List<PerformanceComputation>>();
        List<PerformanceComputation> perMatchPerformanceComputations = playerPerformanceService.findTeamPerMatchPerformanceComputations(team.getId());
        
        for(PerformanceComputation computation : perMatchPerformanceComputations){
        	if(matchRecords.get(computation.getMatch()) != null){
        		matchRecords.get(computation.getMatch()).add(computation);
        	} else if(maxResult == null || matchRecords.size() < 5){
    			List<PerformanceComputation> computations = new ArrayList<PerformanceComputation>();
    			computations.add(computation);
    			matchRecords.put(computation.getMatch(), computations);
        	}
        }
		return matchRecords;
	}

	private Map<League, List<PerformanceComputation>> generateLeagueRecords(Team team, Integer maxResult) {
		Map<League, List<PerformanceComputation>> leagueRecords = new LinkedHashMap<League, List<PerformanceComputation>>();
        List<PerformanceComputation> perLeaguePerformanceComputations = playerPerformanceService.findTeamPerLeaguePerformanceComputations(team.getId());
        
        for(PerformanceComputation computation : perLeaguePerformanceComputations){
        	if(leagueRecords.get(computation.getLeague()) != null){
        		leagueRecords.get(computation.getLeague()).add(computation);
        	} else if(maxResult == null || leagueRecords.size() < 5){
    			List<PerformanceComputation> computations = new ArrayList<PerformanceComputation>();
    			computations.add(computation);
    			leagueRecords.put(computation.getLeague(), computations);
        	}
        }
		return leagueRecords;
	}
    
	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public void showImage(@RequestParam("id") Integer id, HttpServletResponse response, HttpServletRequest request)
			throws ServletException, IOException {
		Team team = teamService.findById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(team.getImage());
		response.getOutputStream().close();
	}
    
    @InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
