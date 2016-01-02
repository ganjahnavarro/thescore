package thescore.controller.core;

import java.io.IOException;
import java.util.Map;

import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereHandler;
import org.atmosphere.cpr.AtmosphereRequest;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.AtmosphereResourceEvent;
import org.atmosphere.cpr.AtmosphereResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;

import thescore.Utility;
import thescore.classes.ActionData;
import thescore.classes.TeamPerformance;
import thescore.interfaces.IAttempt;
import thescore.interfaces.IPerformanceRecord;
import thescore.model.Match;
import thescore.model.PlayerPerformance;
import thescore.service.MatchService;
import thescore.service.PlayerPerformanceService;
import thescore.service.PlayerService;

@AtmosphereHandlerService(path = "/core/atmosphere")
public class CoreAtmosphereHandler implements AtmosphereHandler {

	private ObjectMapper mapper = new ObjectMapper();

	@Override
	public void onRequest(AtmosphereResource atmosphereResource) throws IOException {
		AtmosphereRequest req = atmosphereResource.getRequest();
		
		if (req.getMethod().equalsIgnoreCase("GET")) {
			atmosphereResource.suspend();
		} else if (req.getMethod().equalsIgnoreCase("POST")) {
			Integer matchId = null;
			Integer teamId = null;
			
			try {
				String broadCastMessage = null;
				String message = req.getReader().readLine().trim();
				
				if(message != null){
					ActionData data = new ActionData();
					data = mapper.readValue(message, ActionData.class);
					matchId = data.getMatchId();
					
					if(data.getAction().equals("TIMEOUT")){
						ApplicationContext context = Utility.getApplicationContext();
						MatchService service = context.getBean(MatchService.class);
						Match match = service.findById(matchId);
						
						if(match.getTeamA().getId().equals(data.getTeamId())){
							match.setTeamATimeout((match.getTeamATimeout() != null ? match.getTeamATimeout() : 0) + 1);
						} else {
							match.setTeamBTimeout((match.getTeamBTimeout() != null ? match.getTeamBTimeout() : 0) + 1);
						}
						service.updateMatch(match);
						teamId = data.getTeamId();
					} else {
						if(data.getSubtract()){
							teamId = deletePerformanceAndGetTeamId(data);
						} else {
							teamId = savePerformanceAndGetTeamId(data);
						}
					}
					
					if(teamId != null){
						broadCastMessage = mapper.writeValueAsString(generateTeamPerformance(data, teamId));
						atmosphereResource.getBroadcaster().broadcast(broadCastMessage);
					}
				}
				
			} catch (Exception e){
				if(e instanceof IllegalArgumentException){
					atmosphereResource.getBroadcaster().broadcast(e.getMessage());
				} else {
					e.printStackTrace();
				}
				revalidateMatchFromStatisticsMap(matchId);
			}
		}
	}

	@Override
	public void onStateChange(AtmosphereResourceEvent event) throws IOException {
		AtmosphereResource atmosphereResource = event.getResource();
		AtmosphereResponse response = atmosphereResource.getResponse();
		
		if (atmosphereResource.isSuspended()) {
			if(event != null && event.getMessage() != null){
				atmosphereResource.getResponse().getWriter().write(event.getMessage().toString());
			}
			
			handleConnectionTypes(event, atmosphereResource, response);
		} else if (!event.isResuming()) {
			event.broadcaster().broadcast("Closing..");
		}
	}

	private void revalidateMatchFromStatisticsMap(Integer matchId) {
		if(matchId != null){
			ApplicationContext context = Utility.getApplicationContext();
			context.getBean(CoreController.class).revalidateMatchData(matchId);
		}
	}
	
	private Integer deletePerformanceAndGetTeamId(ActionData data) {
		Integer teamId = null;
		String actionName = data.getAction();
		
		ApplicationContext context = Utility.getApplicationContext();
		PlayerPerformanceService performanceService = context.getBean(PlayerPerformanceService.class);
		
		if(actionName != null && Utility.getPerformanceClasses().get(actionName) != null){
			Boolean deleted = performanceService.checkAndRemoveLatestPerformance(Utility.getPerformanceClasses().get(actionName),
					data.getMatchId(), data.getPlayerId());
			
			teamId = Utility.getApplicationContext().getBean(PlayerService.class).findById(data.getPlayerId()).getTeam().getId();
			
			if(deleted == false){
				throw new IllegalArgumentException("Player has no " + data.getAction() + ".");
			}
		}
		
		return teamId;
	}

	private Integer savePerformanceAndGetTeamId(ActionData data) throws InstantiationException, IllegalAccessException {
		Integer teamId = null;
		String actionName = data.getAction();
		
		ApplicationContext context = Utility.getApplicationContext();
		PlayerPerformanceService performanceService = context.getBean(PlayerPerformanceService.class);
		
		PlayerPerformance performance = performanceService.findPlayerPerformance(data.getMatchId(), data.getPlayerId());
		
		if(actionName != null && Utility.getPerformanceClasses().get(actionName) != null && performance != null){
			IPerformanceRecord record = (IPerformanceRecord) Utility.getPerformanceClasses().get(actionName).newInstance();
			record.setPerformance(performance);
			record.setQuarter(data.getQuarter());
			
			if(record instanceof IAttempt){
				((IAttempt) record).setMissed(data.getMissed());
			}
			
			performanceService.savePerformance(record);
			teamId = performance.getPlayer().getTeam().getId();
		}
		
		return teamId;
	}
	
	private Map<Integer, TeamPerformance> generateTeamPerformance(ActionData data, Integer teamId){
		Integer matchId = data.getMatchId();
		String action = data.getAction();
		Integer value = data.getSubtract() != null && data.getSubtract() ? -1 : 1;
		
		ApplicationContext context = Utility.getApplicationContext();
		Map<Integer, Map<Integer, TeamPerformance>> statisticsMap = context.getBean(CoreController.class).getStatisticsMap();
		
		if(statisticsMap.get(matchId) != null){
			Map<Integer, TeamPerformance> teamPerformanceMap = statisticsMap.get(matchId);
			TeamPerformance teamPerformance = teamPerformanceMap.get(teamId);
			
			if(action.equals("FG")){
				teamPerformance.setFg(teamPerformance.getFg() + value);
				teamPerformance.setFga(teamPerformance.getFga() + value);
			}
			
			if(action.equals("FGA")){
				teamPerformance.setFga(teamPerformance.getFga() + value);
			}
			
			if(action.equals("3FG")){
				teamPerformance.setThreefg(teamPerformance.getThreefg() + value);
				teamPerformance.setThreefga(teamPerformance.getThreefga() + value);
				teamPerformance.setFg(teamPerformance.getFg() + value);
				teamPerformance.setFga(teamPerformance.getFga() + value);
			}
			
			if(action.equals("3FGA")){
				teamPerformance.setThreefga(teamPerformance.getThreefga() + value);
				teamPerformance.setFga(teamPerformance.getFga() + value);
			}
			
			if(action.equals("FT")){
				teamPerformance.setFt(teamPerformance.getFt() + value);
				teamPerformance.setFta(teamPerformance.getFta() + value);
			}
			
			if(action.equals("FTA")){
				teamPerformance.setFta(teamPerformance.getFta() + value);
			}
			
			if(action.equals("STL")){
				teamPerformance.setStl(teamPerformance.getStl() + value);
			}
			
			if(action.equals("BLK")){
				teamPerformance.setBlk(teamPerformance.getBlk() + value);
			}
			
			if(action.equals("AST")){
				teamPerformance.setAst(teamPerformance.getAst() + value);
			}
			
			if(action.equals("DEF")){
				teamPerformance.setDef(teamPerformance.getDef() + value);
			}
			
			if(action.equals("OFF")){
				teamPerformance.setOff(teamPerformance.getOff() + value);
			}
			
			if(action.equals("TO")){
				teamPerformance.setTo(teamPerformance.getTo() + value);
			}
			
			if(action.equals("FOUL")){
				teamPerformance.setFoul(teamPerformance.getFoul() + value);
			}
			
			if(action.equals("TIMEOUT")){
				teamPerformance.setTimeout(teamPerformance.getTimeout() != null ? teamPerformance.getTimeout() + value : value);
			}
			
			teamPerformance.updateScore();
			teamPerformanceMap.put(teamId, teamPerformance);
			statisticsMap.put(matchId, teamPerformanceMap);
		}
		
		return statisticsMap.get(matchId);
	}

	private void handleConnectionTypes(AtmosphereResourceEvent event, AtmosphereResource atmosphereResource,
			AtmosphereResponse res) throws IOException {
		switch (atmosphereResource.transport()) {
		case JSONP:
		case LONG_POLLING:
			event.getResource().resume();
			break;
		case WEBSOCKET:
			break;
		case STREAMING:
			res.getWriter().flush();
			break;
		default:
			break;
		}
	}
	
	@Override
	public void destroy() {
		
	}
	
}