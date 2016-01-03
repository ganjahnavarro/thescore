package thescore.repository;

import java.util.List;
import java.util.UUID;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import thescore.interfaces.IPerformanceRecord;
import thescore.interfaces.IRecord;
import thescore.model.Match;
import thescore.model.PlayerPerformance;
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
	
	private void insertPerformanceComputation(Integer playerId, String sessionId, String fromEntityName, String action) {
		insertPerformanceComputation(playerId, sessionId, fromEntityName, action, false, false);
	}
	
	private void insertPerformanceComputationPerLeague(Integer playerId, String sessionId, String fromEntityName, String action) {
		insertPerformanceComputation(playerId, sessionId, fromEntityName, action, true, false);
	}
	
	private void insertPerformanceComputationPerMatch(Integer playerId, String sessionId, String fromEntityName, String action) {
		insertPerformanceComputation(playerId, sessionId, fromEntityName, action, false, true);
	}
	
	private void insertPerformanceComputation(Integer playerId, String sessionId, String fromEntityName, String action,
			Boolean perLeague, Boolean perMatch) {
		String sqlQuery = "insert into " + PerformanceComputation.ENTITY_NAME
				+ " (" + (perLeague ? " leagueId," : "")
				+ " playerId, action, sessionId, matches, maxOnSingleMatch, total)"
				+ " select " + (perLeague ? " m.leagueId," : "")
				+ " :playerId, :action, :sessionId,"
				+ " cast(count(distinct o.performanceId) as int),"
				
				+ " coalesce(max(subq.mx), 0), "
				
				+ " cast(count(o.id) as int) from " + PlayerPerformance.ENTITY_NAME + " pf "
				+ " left join " + fromEntityName + " o on o.performanceId = pf.id"
				+ (perLeague ? " left join " +  Match.ENTITY_NAME + " m on pf.matchId = m.id" : "")
				
				+ " left join (select count(id) as mx, performanceId from " + fromEntityName
				+ " group by performanceId) subq on subq.performanceId = o.performanceId"
				
				+ " where pf.playerId = :playerId"
				+ (perLeague ? " group by m.leagueId" : "");
		
		getSession().createSQLQuery(sqlQuery)
			.setParameter("playerId", playerId)
			.setParameter("sessionId", sessionId)
			.setParameter("action", action)
			.executeUpdate();
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
