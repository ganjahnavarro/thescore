package thescore.controller;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.validation.Valid;

import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.criterion.Restrictions;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import thescore.Utility;
import thescore.classes.TeamPerformance;
import thescore.editor.LeagueEditor;
import thescore.editor.TeamEditor;
import thescore.enums.UserType;
import thescore.model.League;
import thescore.model.LeagueTeam;
import thescore.model.Match;
import thescore.model.MatchCommittee;
import thescore.model.Player;
import thescore.model.Team;
import thescore.model.User;
import thescore.service.LeagueService;
import thescore.service.MatchService;
import thescore.service.PlayerPerformanceService;
import thescore.service.PlayerService;
import thescore.service.UserService;

@Controller
@RequestMapping(value = "/match")
public class MatchController {
	
	private @Autowired MatchService matchService;
	private @Autowired LeagueService leagueService;
	private @Autowired UserService userService;
	private @Autowired PlayerService playerService;
	private @Autowired PlayerPerformanceService playerPerformanceService;
	
	private @Autowired TeamEditor teamEditor;
	private @Autowired LeagueEditor leagueEditor;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String list(ModelMap model) {
		addListAttributes(model);
		return "match/list";
	}

	private void addListAttributes(ModelMap model) {
		model.addAttribute("liveMatches", matchService
				.findAllMatches(Restrictions.isNotNull("actualStart"),
						Restrictions.isNull("actualEnd")));
		
		List<Match> unplayedMatches = matchService
				.findAllMatches(Restrictions.isNull("actualStart"),
						Restrictions.isNull("actualEnd"));
		
		List<Integer> playableMatches = matchService.findPlayableMatches();
		
		for(Match match : unplayedMatches){
			match.setPlayable(false);
			
			if(playableMatches.contains(match.getId())){
				match.setPlayable(true);
				break;
			}
		}
		
		model.addAttribute("unplayedMatches", unplayedMatches);
		
		model.addAttribute("finishedMatches", matchService
				.findAllMatches(Restrictions.isNotNull("actualEnd")));
	}
	
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String create(ModelMap model) {
		Match match = new Match();
		model.addAttribute("match", match);
		model.addAttribute("edit", false);
		addMatchDataEntryAttribute(model, null);
		return "match/dataentry";
	}

	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String save(@Valid Match match, BindingResult result, ModelMap model,
			@RequestParam(required = false) String[] committees,
			@RequestParam(required = false) String[] teamAPlayers,
			@RequestParam(required = false) String[] teamBPlayers) {
		if(isMatchValid(match, result, model, teamAPlayers, teamBPlayers) == false){
			addMatchDataEntryAttribute(model, null);
			return "match/dataentry";
		}
		matchService.saveMatch(match, committees, teamAPlayers, teamBPlayers);
		model.addAttribute("infoMessage", "Match " + match.getDisplayString() + " registered successfully");
		return "redirect:/match/list";
	}

	@RequestMapping(value = { "/edit-{id}-match" }, method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model) {
        Match match = matchService.findById(id);
        model.addAttribute("match", match);
        model.addAttribute("edit", true);
        addMatchDataEntryAttribute(model, id);
        return "match/dataentry";
    }
     
    @RequestMapping(value = { "/edit-{id}-match" }, method = RequestMethod.POST)
    public String update(@Valid Match match, BindingResult result, ModelMap model, @PathVariable Integer id,
    		@RequestParam(required = false) String[] committees,
			@RequestParam(required = false) String[] teamAPlayers,
			@RequestParam(required = false) String[] teamBPlayers) {
		if(isMatchValid(match, result, model, teamAPlayers, teamBPlayers) == false){
			addMatchDataEntryAttribute(model, match.getId());
			return "match/dataentry";
		}
		matchService.updateMatch(match, committees, teamAPlayers, teamBPlayers);
        model.addAttribute("success", "Match " + match.getDisplayString()  + " updated successfully");
        return "redirect:/match/list";	
    }
    
	private void addMatchDataEntryAttribute(ModelMap model, Integer matchId) {
		model.addAttribute("leagues", leagueService.findAllLeagues());
		
		List<User> committees = userService.findAllUsers(UserType.COMMITTEE);
		
		if(matchId != null){
			List<Integer> matchCommitteePKs = new ArrayList<Integer>();
			List<MatchCommittee> matchCommittees = matchService.findAllMatchCommittees(matchId);
			
			for(MatchCommittee matchCommittee : matchCommittees){
				matchCommitteePKs.add(matchCommittee.getId());
			}
			
			for(User user : committees){
				if(matchCommitteePKs.contains(user.getId())){
					user.setIncludedOnMatch(true);
				}
			}
		}
		model.addAttribute("committees", committees);
	}
    
    private Boolean isMatchValid(Match match, BindingResult result, ModelMap model,
    		String[] teamAPlayers, String[] teamBPlayers) {
		if (result.hasErrors()) {
			Utility.parseErrors(result, model);
			return false;
		}
		
		if(match.getTeamA().equals(match.getTeamB())){
			model.addAttribute("errorMessage", "Team A and B can't be the same.");
			return false;
		}
		
		if(teamAPlayers == null || teamBPlayers == null
				|| teamAPlayers.length != 5 || teamBPlayers.length != 5){
			model.addAttribute("errorMessage", "Both Team A and B should have 5 Starting Players.");
			return false;
		}
		
		return true;
	}
 
    @RequestMapping(value = { "/delete-{id}-match" }, method = RequestMethod.GET)
    public String delete(@PathVariable Integer id, ModelMap model) {
    	try {
    		matchService.deleteMatchById(id);
    	} catch (ConstraintViolationException e){
    		model.addAttribute("errorMessage", "This record can't be deleted because it is referenced by other records.");
    		addListAttributes(model);
    		return "match/list";
    	}
        return "redirect:/match/list";
    }
    
    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
	public String calendar(ModelMap model) {
		List<Match> matches = matchService.findAllMatches(Restrictions.isNotNull("time"));
		model.addAttribute("matches", matches);
		return "match/calendar";
	}
    
    @RequestMapping(value = { "/view-{id}-match" }, method = RequestMethod.GET)
	public String view(@PathVariable Integer id, ModelMap model) {
		Match match = matchService.findById(id);

		List<Player> startingPlayers = matchService.findStartingPlayers(match.getId());
		Map<Integer, TeamPerformance> teamPerformances = Utility
				.generateTeamPerformances(playerPerformanceService, match);

		List<Player> teamAPlayers = new ArrayList<Player>();
		List<Player> teamBPlayers = new ArrayList<Player>();

		for (Player player : startingPlayers) {
			if (player.getTeam().equals(match.getTeamA())) {
				teamAPlayers.add(player);
			} else {
				teamBPlayers.add(player);
			}
		}

		model.addAttribute("match", match);
		model.addAttribute("teamPerformanceA", teamPerformances.get(match.getTeamA().getId()));
		model.addAttribute("teamPerformanceB", teamPerformances.get(match.getTeamB().getId()));
		model.addAttribute("teamAPlayers", teamAPlayers);
		model.addAttribute("teamBPlayers", teamBPlayers);

		return "match/info";
	}
    
	@RequestMapping(value = "/on-league-change", method = RequestMethod.GET)
	public @ResponseBody String onLeagueChange(@RequestParam(name = "matchId", required = false) Integer matchId,
			@RequestParam("leagueId") Integer leagueId) {
		Map<String, Object> json = new ConcurrentHashMap<String, Object>();
		
		List<SimpleEntry<Integer, String>> list = new ArrayList<SimpleEntry<Integer, String>>();;
		
		if(leagueId != null){
			for(LeagueTeam leagueTeam : leagueService.findAllLeagueTeams(Integer.valueOf(leagueId))){
				list.add(new SimpleEntry<Integer, String>(leagueTeam.getTeam().getId(), leagueTeam.getTeam().getName()));
			}
			json.put("teams", list);
		}
		
		try {
			if(matchId != null){
				Match match = matchService.findById(matchId);
				
				Team teamA = match.getTeamA();
				teamA.setImage(null);
				
				Team teamB = match.getTeamB();
				teamB.setImage(null);
				
				json.put("teamA", teamA);
				json.put("teamB", teamB);
			}
			
			return mapper.writeValueAsString(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/on-team-change", method = RequestMethod.GET)
	public @ResponseBody String onTeamAChange(@RequestParam(name = "matchId", required = false) Integer matchId,
			@RequestParam("teamId") Integer teamId) {
		Map<String, Object> json = new ConcurrentHashMap<String, Object>();
		List<SimpleEntry<Integer, String>> list = new ArrayList<SimpleEntry<Integer, String>>();
		
		if(teamId != null){
			for(Player player : playerService.findPlayersByTeamId(Integer.valueOf(teamId))){
				list.add(new SimpleEntry<Integer, String>(player.getId(), player.getDisplayString()));
			}
			json.put("players", list);
			
			List<Integer> startingPlayerPKs = new ArrayList<Integer>();
			List<Player> startingPlayers = matchService.findStartingPlayers(matchId);
			
			for(Player player : startingPlayers){
				startingPlayerPKs.add(player.getId());
			}
			json.put("startingPlayerPKs", startingPlayerPKs);
			
			try {
				return mapper.writeValueAsString(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@RequestMapping(value = "/result/download", method = RequestMethod.GET)
	public ModelAndView downloadResult(@RequestParam("matchId") Integer matchId) throws ServletException, IOException {
		Match match = matchService.findById(matchId);
		
		Map<Integer, TeamPerformance> teamPerformances = Utility
				.generateTeamPerformances(playerPerformanceService, match);
		
		ModelAndView modelAndView = new ModelAndView("pdf-match-result");
		modelAndView.getModelMap().addAttribute("match", match);
		modelAndView.getModelMap().addAttribute("teamPerformances", teamPerformances);
		return modelAndView;
	}
    
    @InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.registerCustomEditor(Team.class, teamEditor);
	    binder.registerCustomEditor(League.class, leagueEditor);
	}
	
}
