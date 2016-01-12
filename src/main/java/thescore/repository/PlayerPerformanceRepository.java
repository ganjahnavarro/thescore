package thescore.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import thescore.interfaces.IPerformanceRecord;
import thescore.interfaces.IRecord;
import thescore.model.Match;
import thescore.model.Player;
import thescore.model.PlayerPerformance;
import thescore.model.Team;
import thescore.model.computation.PerformanceComputation;
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

@Repository
public class PlayerPerformanceRepository extends AbstractRepository<Integer, IRecord> {
	
	public void savePerformance(IRecord performance) {
		persist(performance);
	}

	public void deletePerformance(IRecord performance) {
		getSession().delete(performance);
	}
	
	public PlayerPerformance findPlayerPerformance(Integer matchId, Integer playerId){
		Criteria criteria = getSession().createCriteria(PlayerPerformance.class);
		criteria.add(Restrictions.eq("match.id", matchId));
		criteria.add(Restrictions.eq("player.id", playerId));
		return (PlayerPerformance) criteria.uniqueResult();
    }
	
	@SuppressWarnings("unchecked")
	public List<IPerformanceRecord> findPerformanceRecords(Integer matchId, String entityName){
		return getSession()
				.createQuery("select o from " + entityName + " o where o.performance.match.id = :matchId")
				.setParameter("matchId", matchId).list();
    }
	
	@SuppressWarnings("unchecked")
	public List<PlayerPerformance> findHighestScorePerformances(Integer matchId){
		return getSession().createQuery("select o from " + PlayerPerformance.ENTITY_NAME
			+ " o where o.match.id = :matchId and o.finalScore = "
			+ " (select max(finalScore) from " + PlayerPerformance.ENTITY_NAME + " where match.id = :matchId)")
			.setParameter("matchId", matchId)
			.list();
    }
	
	@SuppressWarnings("unchecked")
	public List<PerformanceComputation> findLeaguePlayerPerformanceComputations(Integer leagueId){
		String sessionId = UUID.randomUUID().toString();
		
		insertLeaguePerformanceComputation(leagueId, sessionId, FieldGoal.ENTITY_NAME, "FG");
		insertLeaguePerformanceComputation(leagueId, sessionId, ThreePointFieldGoal.ENTITY_NAME, "3FG");
		insertLeaguePerformanceComputation(leagueId, sessionId, FreeThrow.ENTITY_NAME, "FT");
		insertLeaguePerformanceComputation(leagueId, sessionId, Steal.ENTITY_NAME, "STL");
		insertLeaguePerformanceComputation(leagueId, sessionId, Block.ENTITY_NAME, "BLK");
		insertLeaguePerformanceComputation(leagueId, sessionId, Assist.ENTITY_NAME, "AST");
		insertLeaguePerformanceComputation(leagueId, sessionId, DefensiveRebound.ENTITY_NAME, "DEF");
		insertLeaguePerformanceComputation(leagueId, sessionId, OffensiveRebound.ENTITY_NAME, "OFF");
		insertLeaguePerformanceComputation(leagueId, sessionId, Turnover.ENTITY_NAME, "TO");
		insertLeaguePerformanceComputation(leagueId, sessionId, Foul.ENTITY_NAME, "PF");
		
		return getSession().createCriteria(PerformanceComputation.class)
				.add(Restrictions.eq("league.id", leagueId))
				.add(Restrictions.eq("sessionId", sessionId))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PerformanceComputation> findOverallPerformanceComputations(Integer playerId){
		String sessionId = UUID.randomUUID().toString();
		
		insertPerformanceComputation(playerId, sessionId, FieldGoal.ENTITY_NAME, "FG");
		insertPerformanceComputation(playerId, sessionId, ThreePointFieldGoal.ENTITY_NAME, "3FG");
		insertPerformanceComputation(playerId, sessionId, FreeThrow.ENTITY_NAME, "FT");
		insertPerformanceComputation(playerId, sessionId, Steal.ENTITY_NAME, "STL");
		insertPerformanceComputation(playerId, sessionId, Block.ENTITY_NAME, "BLK");
		insertPerformanceComputation(playerId, sessionId, Assist.ENTITY_NAME, "AST");
		insertPerformanceComputation(playerId, sessionId, DefensiveRebound.ENTITY_NAME, "DEF");
		insertPerformanceComputation(playerId, sessionId, OffensiveRebound.ENTITY_NAME, "OFF");
		insertPerformanceComputation(playerId, sessionId, Turnover.ENTITY_NAME, "TO");
		insertPerformanceComputation(playerId, sessionId, Foul.ENTITY_NAME, "PF");
		
		return getSession().createCriteria(PerformanceComputation.class)
				.add(Restrictions.eq("player.id", playerId))
				.add(Restrictions.eq("sessionId", sessionId))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PerformanceComputation> findPerLeaguePerformanceComputations(Integer playerId){
		String sessionId = UUID.randomUUID().toString();
		
		insertPerformanceComputationPerLeague(playerId, sessionId, FieldGoal.ENTITY_NAME, "FG");
		insertPerformanceComputationPerLeague(playerId, sessionId, ThreePointFieldGoal.ENTITY_NAME, "3FG");
		insertPerformanceComputationPerLeague(playerId, sessionId, FreeThrow.ENTITY_NAME, "FT");
		insertPerformanceComputationPerLeague(playerId, sessionId, Steal.ENTITY_NAME, "STL");
		insertPerformanceComputationPerLeague(playerId, sessionId, Block.ENTITY_NAME, "BLK");
		insertPerformanceComputationPerLeague(playerId, sessionId, Assist.ENTITY_NAME, "AST");
		insertPerformanceComputationPerLeague(playerId, sessionId, DefensiveRebound.ENTITY_NAME, "DEF");
		insertPerformanceComputationPerLeague(playerId, sessionId, OffensiveRebound.ENTITY_NAME, "OFF");
		insertPerformanceComputationPerLeague(playerId, sessionId, Turnover.ENTITY_NAME, "TO");
		insertPerformanceComputationPerLeague(playerId, sessionId, Foul.ENTITY_NAME, "PF");
		
		return getSession().createCriteria(PerformanceComputation.class)
				.add(Restrictions.eq("player.id", playerId))
				.add(Restrictions.eq("sessionId", sessionId))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PerformanceComputation> findPerMatchPerformanceComputations(Integer playerId){
		String sessionId = UUID.randomUUID().toString();
		
		insertPerformanceComputationPerMatch(playerId, sessionId, FieldGoal.ENTITY_NAME, "FG");
		insertPerformanceComputationPerMatch(playerId, sessionId, ThreePointFieldGoal.ENTITY_NAME, "3FG");
		insertPerformanceComputationPerMatch(playerId, sessionId, FreeThrow.ENTITY_NAME, "FT");
		insertPerformanceComputationPerMatch(playerId, sessionId, Steal.ENTITY_NAME, "STL");
		insertPerformanceComputationPerMatch(playerId, sessionId, Block.ENTITY_NAME, "BLK");
		insertPerformanceComputationPerMatch(playerId, sessionId, Assist.ENTITY_NAME, "AST");
		insertPerformanceComputationPerMatch(playerId, sessionId, DefensiveRebound.ENTITY_NAME, "DEF");
		insertPerformanceComputationPerMatch(playerId, sessionId, OffensiveRebound.ENTITY_NAME, "OFF");
		insertPerformanceComputationPerMatch(playerId, sessionId, Turnover.ENTITY_NAME, "TO");
		insertPerformanceComputationPerMatch(playerId, sessionId, Foul.ENTITY_NAME, "PF");
		
		return getSession().createCriteria(PerformanceComputation.class)
				.add(Restrictions.eq("player.id", playerId))
				.add(Restrictions.eq("sessionId", sessionId))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PerformanceComputation> findTeamOverallPerformanceComputations(Integer teamId){
		String sessionId = UUID.randomUUID().toString();
		
		insertTeamPerformanceComputation(teamId, sessionId, FieldGoal.ENTITY_NAME, "FG");
		insertTeamPerformanceComputation(teamId, sessionId, ThreePointFieldGoal.ENTITY_NAME, "3FG");
		insertTeamPerformanceComputation(teamId, sessionId, FreeThrow.ENTITY_NAME, "FT");
		insertTeamPerformanceComputation(teamId, sessionId, Steal.ENTITY_NAME, "STL");
		insertTeamPerformanceComputation(teamId, sessionId, Block.ENTITY_NAME, "BLK");
		insertTeamPerformanceComputation(teamId, sessionId, Assist.ENTITY_NAME, "AST");
		insertTeamPerformanceComputation(teamId, sessionId, DefensiveRebound.ENTITY_NAME, "DEF");
		insertTeamPerformanceComputation(teamId, sessionId, OffensiveRebound.ENTITY_NAME, "OFF");
		insertTeamPerformanceComputation(teamId, sessionId, Turnover.ENTITY_NAME, "TO");
		insertTeamPerformanceComputation(teamId, sessionId, Foul.ENTITY_NAME, "PF");
		
		return getSession().createCriteria(PerformanceComputation.class)
				.add(Restrictions.eq("team.id", teamId))
				.add(Restrictions.eq("sessionId", sessionId))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PerformanceComputation> findTeamPerLeaguePerformanceComputations(Integer teamId){
		String sessionId = UUID.randomUUID().toString();
		
		insertTeamPerformanceComputationPerLeague(teamId, sessionId, FieldGoal.ENTITY_NAME, "FG");
		insertTeamPerformanceComputationPerLeague(teamId, sessionId, ThreePointFieldGoal.ENTITY_NAME, "3FG");
		insertTeamPerformanceComputationPerLeague(teamId, sessionId, FreeThrow.ENTITY_NAME, "FT");
		insertTeamPerformanceComputationPerLeague(teamId, sessionId, Steal.ENTITY_NAME, "STL");
		insertTeamPerformanceComputationPerLeague(teamId, sessionId, Block.ENTITY_NAME, "BLK");
		insertTeamPerformanceComputationPerLeague(teamId, sessionId, Assist.ENTITY_NAME, "AST");
		insertTeamPerformanceComputationPerLeague(teamId, sessionId, DefensiveRebound.ENTITY_NAME, "DEF");
		insertTeamPerformanceComputationPerLeague(teamId, sessionId, OffensiveRebound.ENTITY_NAME, "OFF");
		insertTeamPerformanceComputationPerLeague(teamId, sessionId, Turnover.ENTITY_NAME, "TO");
		insertTeamPerformanceComputationPerLeague(teamId, sessionId, Foul.ENTITY_NAME, "PF");
		
		return getSession().createCriteria(PerformanceComputation.class)
				.add(Restrictions.eq("team.id", teamId))
				.add(Restrictions.eq("sessionId", sessionId))
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<PerformanceComputation> findTeamPerMatchPerformanceComputations(Integer teamId){
		String sessionId = UUID.randomUUID().toString();
		
		insertTeamPerformanceComputationPerMatch(teamId, sessionId, FieldGoal.ENTITY_NAME, "FG");
		insertTeamPerformanceComputationPerMatch(teamId, sessionId, ThreePointFieldGoal.ENTITY_NAME, "3FG");
		insertTeamPerformanceComputationPerMatch(teamId, sessionId, FreeThrow.ENTITY_NAME, "FT");
		insertTeamPerformanceComputationPerMatch(teamId, sessionId, Steal.ENTITY_NAME, "STL");
		insertTeamPerformanceComputationPerMatch(teamId, sessionId, Block.ENTITY_NAME, "BLK");
		insertTeamPerformanceComputationPerMatch(teamId, sessionId, Assist.ENTITY_NAME, "AST");
		insertTeamPerformanceComputationPerMatch(teamId, sessionId, DefensiveRebound.ENTITY_NAME, "DEF");
		insertTeamPerformanceComputationPerMatch(teamId, sessionId, OffensiveRebound.ENTITY_NAME, "OFF");
		insertTeamPerformanceComputationPerMatch(teamId, sessionId, Turnover.ENTITY_NAME, "TO");
		insertTeamPerformanceComputationPerMatch(teamId, sessionId, Foul.ENTITY_NAME, "PF");
		
		return getSession().createCriteria(PerformanceComputation.class)
				.add(Restrictions.eq("team.id", teamId))
				.add(Restrictions.eq("sessionId", sessionId))
				.list();
	}
	
	private void insertLeaguePerformanceComputation(Integer leagueId, String sessionId, String fromEntityName, String action) {
		insertPerformanceComputation(null, null, leagueId, sessionId, fromEntityName, action, false, false);
	}
	
	private void insertPerformanceComputation(Integer playerId, String sessionId, String fromEntityName, String action) {
		insertPerformanceComputation(playerId, null, null, sessionId, fromEntityName, action, false, false);
	}
	
	private void insertPerformanceComputationPerLeague(Integer playerId, String sessionId, String fromEntityName, String action) {
		insertPerformanceComputation(playerId, null, null, sessionId, fromEntityName, action, true, false);
	}
	
	private void insertPerformanceComputationPerMatch(Integer playerId, String sessionId, String fromEntityName, String action) {
		insertPerformanceComputation(playerId, null, null, sessionId, fromEntityName, action, false, true);
	}
	
	private void insertTeamPerformanceComputation(Integer teamId, String sessionId, String fromEntityName, String action) {
		insertPerformanceComputation(null, teamId, null, sessionId, fromEntityName, action, false, false);
	}
	
	private void insertTeamPerformanceComputationPerLeague(Integer teamId, String sessionId, String fromEntityName, String action) {
		insertPerformanceComputation(null, teamId, null, sessionId, fromEntityName, action, true, false);
	}
	
	private void insertTeamPerformanceComputationPerMatch(Integer teamId, String sessionId, String fromEntityName, String action) {
		insertPerformanceComputation(null, teamId, null, sessionId, fromEntityName, action, false, true);
	}
	
	private void insertPerformanceComputation(Integer playerId, Integer teamId, Integer leagueId,
			String sessionId, String fromEntityName, String action,
			Boolean perLeague, Boolean perMatch) {
		
		String sqlQuery = "insert into " + PerformanceComputation.ENTITY_NAME
				+ (playerId != null ? " (playerId," : "")
				+ (teamId != null ? " (teamId," : "")
				+ (leagueId != null ? " (playerId, leagueId," : "")
				
				+ (perLeague ? " leagueId," : "")
				+ (perMatch ? " matchId," : "")
				+ " action, sessionId, matches, maxOnSingleMatch, total)"
				
				+ " select"
				+ (playerId != null ? " :playerId," : "")
				+ (teamId != null ? " :teamId," : "")
				+ (leagueId != null ? " p.id, :leagueId," : "")
				
				+ (perLeague ? " m.leagueId," : "")
				+ (perMatch ? " pf.matchId," : "")
				+ " :action, :sessionId,"
				+ " cast(count(distinct o.performanceId) as unsigned),"
				
				+ " coalesce(max(subq.mx), 0), "
				
				+ " cast(count(o.id) as unsigned) from " + Player.ENTITY_NAME + " p, " + Team.ENTITY_NAME + " t," + PlayerPerformance.ENTITY_NAME + " pf "
				+ " left join " + fromEntityName + " o on o.performanceId = pf.id"
				+ (perLeague || leagueId != null ? " left join " +  Match.ENTITY_NAME + " m on pf.matchId = m.id" : "")
				
				+ " left join (select count(id) as mx, performanceId from " + fromEntityName
				+ " group by performanceId) subq on subq.performanceId = o.performanceId"
				
				+ " where pf.playerId = p.id"
				+ (playerId != null ? " and pf.playerId = :playerId" : "")
				+ (teamId != null ? " and p.teamId = :teamId" : "")
				+ (leagueId != null ? " and m.leagueId = :leagueId group by m.leagueId, p.id" : "")
				
				+ (perLeague ? " group by m.leagueId" : "")
				
				+ (perMatch ? " group by pf.matchId" : "")
				+ " order by t.name, p.lastName";
		
		Query query =getSession().createSQLQuery(sqlQuery)
				.setParameter("sessionId", sessionId)
				.setParameter("action", action); 

		if(playerId != null){
			query.setParameter("playerId", playerId);
		}
		
		if(teamId != null){
			query.setParameter("teamId", teamId);
		}
		
		if(leagueId != null){
			query.setParameter("leagueId", leagueId);
		}
		
		query.executeUpdate();
	}
	
	@SuppressWarnings("rawtypes")
	public Boolean checkAndRemoveLatestPerformance(Class clazz, Integer matchId, Integer playerId){
		IPerformanceRecord record = (IPerformanceRecord) getSession().createCriteria(clazz)
			.createAlias("performance", "pf")
			.add(Restrictions.eq("pf.match.id", matchId))
			.add(Restrictions.eq("pf.player.id", playerId))
			.addOrder(Order.desc("id"))
			.setMaxResults(1)
			.uniqueResult();

		if(record != null){
			delete(record);
			return true;
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> findPlayerPerformancePlayerPKs(Integer matchId) {
		return getSession().createQuery("select o.player.id from "
				+ PlayerPerformance.ENTITY_NAME + " o where o.match.id = :matchId")
				.setParameter("matchId", matchId)
				.list();
	}
	
}
