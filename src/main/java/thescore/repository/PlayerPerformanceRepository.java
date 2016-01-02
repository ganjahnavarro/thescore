package thescore.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import thescore.classes.PlayerCareerRecord;
import thescore.interfaces.IPerformanceRecord;
import thescore.interfaces.IRecord;
import thescore.model.PlayerPerformance;
import thescore.model.performance.FieldGoal;

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
	public List<PlayerCareerRecord> findPlayerCareerRecords(Integer playerId){
		List<PlayerCareerRecord> careerRecords = getSession().createQuery("select new " + PlayerCareerRecord.class.getName()
				
//				TODO 2, 10, 35 && Other records union
//				+ " (o.performance.player, 'FG', 2, 10, 35)"
				
				+ " (o.performance.player, 'FG', 2, 10, 35)"
				+ " from " + FieldGoal.ENTITY_NAME + " o where o.performance.player.id = :playerId"
				+ " group by o.performance.player")
				.setParameter("playerId", playerId).list();
		
		System.out.println(careerRecords);
		
		return careerRecords;
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
