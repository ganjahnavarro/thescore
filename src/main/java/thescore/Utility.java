package thescore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import thescore.classes.TeamPerformance;
import thescore.enums.UserType;
import thescore.model.Match;
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
import thescore.service.PlayerPerformanceService;

@Component
@SuppressWarnings("rawtypes")
public final class Utility implements ApplicationContextAware{
	
	private static ApplicationContext context;
	private static Map<String, Class> performanceClasses;
	
	static {
		performanceClasses = new ConcurrentHashMap<String, Class>();
		performanceClasses.put("FG", FieldGoal.class);
		performanceClasses.put("FGA", FieldGoal.class);
		
		performanceClasses.put("3FG", ThreePointFieldGoal.class);
		performanceClasses.put("3FGA", ThreePointFieldGoal.class);
		
		performanceClasses.put("FT", FreeThrow.class);
		performanceClasses.put("FTA", FreeThrow.class);
		
		performanceClasses.put("AST", Assist.class);
		performanceClasses.put("BLK", Block.class);
		performanceClasses.put("STL", Steal.class);
		
		performanceClasses.put("DEF", DefensiveRebound.class);
		performanceClasses.put("OFF", OffensiveRebound.class);
		
		performanceClasses.put("TO", Turnover.class);
		performanceClasses.put("FOUL", Foul.class);
	}
	
	public static Map<String, Class> getPerformanceClasses(){
		return performanceClasses;
	}
	
	public static void parseErrors(BindingResult result, ModelMap model){
		String errorMessage = "";
		
    	for(FieldError error : result.getFieldErrors()){
    		errorMessage += error.getDefaultMessage() + " ";
    	}
    	
    	model.addAttribute("errorMessage", errorMessage);
	}

	public static ApplicationContext getApplicationContext() {
        return context;
    }
	
	public static String getSecurityPrincipal() {
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (principal instanceof UserDetails) {
				return ((UserDetails) principal).getUsername();
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static int getCurrentUserAccess() {
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (principal instanceof UserDetails) {
				Iterator<GrantedAuthority> iterator = (Iterator<GrantedAuthority>) ((UserDetails) principal).getAuthorities().iterator();
				
				List<String> roles = new ArrayList<String>();
				while(iterator.hasNext()){
					roles.add(iterator.next().toString());
				}

				List<UserType> types = Arrays.asList(UserType.values());
				Collections.reverse(types);
				
				for(UserType type : types){
					for(String role : roles){
						if(role.equalsIgnoreCase("ROLE_" + type.toString())){
							return type.ordinal();
						}
					}
				}
			}
		}
		return 0;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		Utility.context = context;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<Integer, TeamPerformance> generateTeamPerformances(PlayerPerformanceService playerPerformanceService, Match match) {
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
	
}
