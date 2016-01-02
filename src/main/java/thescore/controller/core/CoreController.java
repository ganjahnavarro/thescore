package thescore.controller.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import thescore.Utility;
import thescore.classes.TeamPerformance;
import thescore.enums.UserType;
import thescore.model.Match;
import thescore.model.MatchCommittee;
import thescore.model.Player;
import thescore.model.PlayerPerformance;
import thescore.model.performance.Assist;
import thescore.model.performance.Block;
import thescore.model.performance.DefensiveRebound;
import thescore.model.performance.FieldGoal;
import thescore.model.performance.Foul;
import thescore.model.performance.FreeThrow;
import thescore.model.performance.OffensiveRebound;
import thescore.model.performance.Steal;
import thescore.model.performance.ThreePointFieldGoal;
import thescore.model.performance.Turnover;
import thescore.service.MatchService;
import thescore.service.NewsfeedService;
import thescore.service.PlayerPerformanceService;
import thescore.service.PlayerService;

@Controller
@RequestMapping("/core")
public class CoreController {
	
	private @Autowired MatchService matchService;
	private @Autowired PlayerService playerService;
	private @Autowired NewsfeedService newsfeedService;
	private @Autowired PlayerPerformanceService playerPerformanceService;
	
	private Map<Integer, Map<Integer, TeamPerformance>> statisticsMap = new ConcurrentHashMap<Integer, Map<Integer, TeamPerformance>>();
	
	@RequestMapping(value = "/play-{id}-match", method = RequestMethod.GET)
	public String openMatch(@PathVariable Integer id, ModelMap model) {
		Map<Integer, TeamPerformance> teamPerformances = null;
		Match match = matchService.findById(id);
		
		if(match.getActualStart() == null){
			match.setActualStart(new Date());
			matchService.updateMatch(match);
		}

		if(match.getWinner() != null){
			return null;
		}
		
		revalidateMatchData(match.getId());
		
		if(statisticsMap.get(id) != null){
			teamPerformances = statisticsMap.get(id);
		} else {
			teamPerformances = generateTeamPerformances(match);
			statisticsMap.put(match.getId(), teamPerformances);
		}
		model.addAttribute("match", match);
		
		List<Player> listA = playerService.findPlayersByTeamId(match.getTeamA().getId());
		List<Player> listB = playerService.findPlayersByTeamId(match.getTeamB().getId());
		
		List<Player> teamAPlayers = new ArrayList<Player>();
		List<Player> teamBPlayers = new ArrayList<Player>();
		
		List<Player> teamABench = new ArrayList<Player>();
		List<Player> teamBBench = new ArrayList<Player>();
		
		List<Player> allPlayers = new ArrayList<Player>(listA);
		allPlayers.addAll(listB);
		
		List<Player> startingPlayers = matchService.findStartingPlayers(match.getId());
		
		List<Integer> existingPlayerPKs = playerPerformanceService.findPlayerPerformancePlayerPKs(match.getId());
		
		for(Player player : allPlayers){
			if(existingPlayerPKs.contains(player.getId()) == false){
				PlayerPerformance performance = new PlayerPerformance();
				performance.setMatch(match);
				performance.setPlayer(player);
				playerPerformanceService.savePerformance(performance);
			}
			
			if(startingPlayers.contains(player)){
				if(player.getTeam().getId().equals(match.getTeamA().getId())){
					teamAPlayers.add(player);
				} else {
					teamBPlayers.add(player);
				}
			} else {
				if(player.getTeam().getId().equals(match.getTeamA().getId())){
					teamABench.add(player);
				} else {
					teamBBench.add(player);
				}
			}
		}
		
		TeamPerformance teamPerformanceA = teamPerformances.get(match.getTeamA().getId());
		TeamPerformance teamPerformanceB = teamPerformances.get(match.getTeamB().getId());
		
		model.addAttribute("teamAPlayers", teamAPlayers);
		model.addAttribute("teamBPlayers", teamBPlayers);
		
		model.addAttribute("teamABench", teamABench);
		model.addAttribute("teamBBench", teamBBench);
		
		model.addAttribute("teamPerformanceA", teamPerformanceA);
		model.addAttribute("teamPerformanceB", teamPerformanceB);

		model.addAttribute("allowedStatisticsModification", allowedStatisticsModification(match.getId()));
		
		return "core/atmosphere";
	}
	
	private Boolean allowedStatisticsModification(Integer matchId){
		int currentUserAccess = Utility.getCurrentUserAccess();
		
		if(currentUserAccess == UserType.DEFAULT.ordinal()){
			return false;
		}
		
		if(currentUserAccess >= UserType.HEAD_COMMITTEE.ordinal()){
			return true;
		}
		
		List<MatchCommittee> matchCommittees = matchService.findAllMatchCommittees(matchId);

		for(MatchCommittee matchCommittee : matchCommittees){
			if(matchCommittee.getCommittee().getUserName().equals(Utility.getSecurityPrincipal())){
				return true;
			}
		}
		return false;
	}
	
	@RequestMapping(value = "/end-{id}-match", method = RequestMethod.GET)
	public String endMatch(@PathVariable Integer id, ModelMap model) {
		Match match = matchService.findById(id);
		newsfeedService.onMatchEnd(match);
		return "redirect:/match/list";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<Integer, TeamPerformance> generateTeamPerformances(Match match) {
		Integer id = match.getId();
		Integer teamAId = match.getTeamA().getId();
		Integer teamBId = match.getTeamB().getId();
		
		Map<Integer, TeamPerformance> teamPerformances = new LinkedHashMap<Integer, TeamPerformance>();
		TeamPerformance teamPerformanceA = new TeamPerformance();
		TeamPerformance teamPerformanceB = new TeamPerformance();
		
		teamPerformanceA.setTimeout(match.getTeamATimeout());
		teamPerformanceB.setTimeout(match.getTeamBTimeout());
		
		List<FieldGoal> fgs = (List) playerPerformanceService.findPerformanceRecords(id, FieldGoal.ENTITY_NAME);
		for (FieldGoal fg : fgs) {
			TeamPerformance teamPerformance = fg.getPerformance().getPlayer().getTeam().getId() == teamAId ? teamPerformanceA : teamPerformanceB;
			
			teamPerformance.setFga(teamPerformance.getFga() + 1);

			if (fg.getMissed() == false) {
				teamPerformance.setFg(teamPerformance.getFg() + 1);
			}
		}

		List<ThreePointFieldGoal> threefgs = (List) playerPerformanceService.findPerformanceRecords(id,
				ThreePointFieldGoal.ENTITY_NAME);
		for (ThreePointFieldGoal threefg : threefgs) {
			TeamPerformance teamPerformance = threefg.getPerformance().getPlayer().getTeam().getId() == teamAId ? teamPerformanceA : teamPerformanceB;
			
			teamPerformance.setThreefga(teamPerformance.getThreefga() + 1);

			if (threefg.getMissed() == false) {
				teamPerformance.setThreefg(teamPerformance.getThreefg() + 1);
			}
		}

		List<FreeThrow> fts = (List) playerPerformanceService.findPerformanceRecords(id, FreeThrow.ENTITY_NAME);
		for (FreeThrow ft : fts) {
			TeamPerformance teamPerformance = ft.getPerformance().getPlayer().getTeam().getId() == teamAId ? teamPerformanceA : teamPerformanceB;
			
			teamPerformance.setFta(teamPerformance.getFta() + 1);

			if (ft.getMissed() == false) {
				teamPerformance.setFt(teamPerformance.getFt() + 1);
			}
		}

		List<Steal> stls = (List) playerPerformanceService.findPerformanceRecords(id, Steal.ENTITY_NAME);
		for(Steal stl : stls){
			TeamPerformance teamPerformance = stl.getPerformance().getPlayer().getTeam().getId() == teamAId ? teamPerformanceA : teamPerformanceB;
			teamPerformance.setStl(teamPerformance.getStl() + 1);
		}

		List<Block> blks = (List) playerPerformanceService.findPerformanceRecords(id, Block.ENTITY_NAME);
		for(Block blk : blks){
			TeamPerformance teamPerformance = blk.getPerformance().getPlayer().getTeam().getId() == teamAId ? teamPerformanceA : teamPerformanceB;
			teamPerformance.setBlk(teamPerformance.getBlk() + 1);
		}

		List<Assist> asts = (List) playerPerformanceService.findPerformanceRecords(id, Assist.ENTITY_NAME);
		for(Assist ast : asts){
			TeamPerformance teamPerformance = ast.getPerformance().getPlayer().getTeam().getId() == teamAId ? teamPerformanceA : teamPerformanceB;
			teamPerformance.setAst(teamPerformance.getAst() + 1);
		}

		List<DefensiveRebound> defs = (List) playerPerformanceService.findPerformanceRecords(id, DefensiveRebound.ENTITY_NAME);
		for(DefensiveRebound def : defs){
			TeamPerformance teamPerformance = def.getPerformance().getPlayer().getTeam().getId() == teamAId ? teamPerformanceA : teamPerformanceB;
			teamPerformance.setDef(teamPerformance.getDef() + 1);
		}

		List<OffensiveRebound> offs = (List) playerPerformanceService.findPerformanceRecords(id, OffensiveRebound.ENTITY_NAME);
		for(OffensiveRebound off : offs){
			TeamPerformance teamPerformance = off.getPerformance().getPlayer().getTeam().getId() == teamAId ? teamPerformanceA : teamPerformanceB;
			teamPerformance.setOff(teamPerformance.getOff() + 1);
		}

		List<Turnover> tos = (List) playerPerformanceService.findPerformanceRecords(id, Turnover.ENTITY_NAME);
		for(Turnover to : tos){
			TeamPerformance teamPerformance = to.getPerformance().getPlayer().getTeam().getId() == teamAId ? teamPerformanceA : teamPerformanceB;
			teamPerformance.setTo(teamPerformance.getTo() + 1);
		}

		List<Foul> fouls = (List) playerPerformanceService.findPerformanceRecords(id, Foul.ENTITY_NAME);
		for(Foul foul : fouls){
			TeamPerformance teamPerformance = foul.getPerformance().getPlayer().getTeam().getId() == teamAId ? teamPerformanceA : teamPerformanceB;
			teamPerformance.setFoul(teamPerformance.getFoul() + 1);
		}
		
		teamPerformanceA.updateScore();
		teamPerformanceB.updateScore();
		
		teamPerformances.put(teamAId, teamPerformanceA);
		teamPerformances.put(teamBId, teamPerformanceB);
		return teamPerformances;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

	public Map<Integer, Map<Integer, TeamPerformance>> getStatisticsMap() {
		return statisticsMap;
	}
	
	public void revalidateMatchData(Integer matchId){
		Match match = matchService.findById(matchId);
		Map<Integer, TeamPerformance> teamPerformances = generateTeamPerformances(match);
		statisticsMap.put(match.getId(), teamPerformances);
	}
	
}