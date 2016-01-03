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
import org.springframework.web.servlet.ModelAndView;

import thescore.Utility;
import thescore.editor.TeamEditor;
import thescore.enums.Position;
import thescore.model.League;
import thescore.model.Match;
import thescore.model.Player;
import thescore.model.Team;
import thescore.model.computation.PerformanceComputation;
import thescore.service.PlayerPerformanceService;
import thescore.service.PlayerService;
import thescore.service.TeamService;
 
@Controller
@RequestMapping("/player")
public class PlayerController {

	private @Autowired PlayerService playerService;
	private @Autowired PlayerPerformanceService playerPerformanceService;
    
    private @Autowired TeamService teamService;
    private @Autowired TeamEditor teamEditor;
     
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<Player> players = playerService.findAllPlayers();
		model.addAttribute("players", players);
		return "player/list";
	}
 
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String create(ModelMap model) {
		Player player = new Player();
		model.addAttribute("player", player);
		model.addAttribute("edit", false);
		model.addAttribute("actionParam", "/player/new?");
		addPlayerDataEntryAttribute(model);
		return "player/dataentry";
	}

	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String save(@Valid Player player, BindingResult result, ModelMap model,
			@RequestParam CommonsMultipartFile fileUpload) {
		if (fileUpload != null && fileUpload.getOriginalFilename() != null
    			&& !fileUpload.getOriginalFilename().isEmpty()) {
			player.setImageFileName(fileUpload.getOriginalFilename());
			player.setImage(fileUpload.getBytes());
		}

		if (result.hasErrors()) {
			Utility.parseErrors(result, model);
			addPlayerDataEntryAttribute(model);
			model.addAttribute("actionParam", "/player/new?");
			return "player/dataentry";
		}

		playerService.savePlayer(player);
		model.addAttribute("infoMessage", "Player " + player.getDisplayString() + " registered successfully");
		return "redirect:/player/list";
	}
 
    @RequestMapping(value = { "/edit-{id}-player" }, method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model) {
		Player player = playerService.findById(id);
		model.addAttribute("player", player);
		model.addAttribute("edit", true);
		model.addAttribute("actionParam", "/player/edit-" + id + "-player?");
		addPlayerDataEntryAttribute(model);
		return "player/dataentry";
    }
     
    @RequestMapping(value = { "/edit-{id}-player" }, method = RequestMethod.POST)
    public String update(@Valid Player player, BindingResult result, ModelMap model, @PathVariable Integer id,
			@RequestParam CommonsMultipartFile fileUpload) {
    	if (fileUpload != null && fileUpload.getOriginalFilename() != null
    			&& !fileUpload.getOriginalFilename().isEmpty()) {
			player.setImageFileName(fileUpload.getOriginalFilename());
			player.setImage(fileUpload.getBytes());
		}
 
        if (result.hasErrors()) {
        	Utility.parseErrors(result, model);
        	addPlayerDataEntryAttribute(model);
        	model.addAttribute("actionParam", "/player/edit-" + id + "-player?");
            return "player/dataentry";
        }
 
        playerService.updatePlayer(player);
        model.addAttribute("success", "Player " + player.getDisplayString()  + " updated successfully");
        return "redirect:/player/list";	
    }
    
    private void addPlayerDataEntryAttribute(ModelMap model) {
		model.addAttribute("positions", Position.values());
		model.addAttribute("teams", teamService.findAllTeams());
	}
 
    @RequestMapping(value = { "/delete-{id}-player" }, method = RequestMethod.GET)
    public String delete(@PathVariable Integer id) {
        playerService.deletePlayerById(id);
        return "redirect:/player/list";
    }
    
    @RequestMapping(value = { "/view-{id}-player" }, method = RequestMethod.GET)
    public String view(@PathVariable Integer id, ModelMap model) {
        Player player = playerService.findById(id);
		model.addAttribute("player", player);

		model.addAttribute("overAllRecords", playerPerformanceService.findOverallPerformanceComputations(player.getId()));
        model.addAttribute("perLeagueRecords", generateLeagueRecords(player));
        model.addAttribute("perMatchRecords", generatePerMatchRecords(player));
        
        return "player/info";
    }

	private Map<Match, List<PerformanceComputation>> generatePerMatchRecords(Player player) {
		Map<Match, List<PerformanceComputation>> matchRecords = new LinkedHashMap<Match, List<PerformanceComputation>>();
        List<PerformanceComputation> perMatchPerformanceComputations = playerPerformanceService.findPerMatchPerformanceComputations(player.getId());
        
        for(PerformanceComputation computation : perMatchPerformanceComputations){
        	if(matchRecords.get(computation.getMatch()) != null){
        		matchRecords.get(computation.getMatch()).add(computation);
        	} else {
        		List<PerformanceComputation> computations = new ArrayList<PerformanceComputation>();
        		computations.add(computation);
        		matchRecords.put(computation.getMatch(), computations);
        	}
        }
		return matchRecords;
	}

	private Map<League, List<PerformanceComputation>> generateLeagueRecords(Player player) {
		Map<League, List<PerformanceComputation>> leagueRecords = new LinkedHashMap<League, List<PerformanceComputation>>();
        List<PerformanceComputation> perLeaguePerformanceComputations = playerPerformanceService.findPerLeaguePerformanceComputations(player.getId());
        
        for(PerformanceComputation computation : perLeaguePerformanceComputations){
        	if(leagueRecords.get(computation.getLeague()) != null){
        		leagueRecords.get(computation.getLeague()).add(computation);
        	} else {
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
		Player player = playerService.findById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(player.getImage());
		response.getOutputStream().close();
	}
	
	@RequestMapping(value = "/list/download", method = RequestMethod.GET)
	public ModelAndView downloadList()
			throws ServletException, IOException {
		List<Player> players = playerService.findAllPlayers();
		return new ModelAndView("pdf-player-list", "players", players);
	}
    
    @InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.registerCustomEditor(Team.class, teamEditor);
	    binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}
 
}