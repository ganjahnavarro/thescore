package thescore.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import thescore.model.Match;
import thescore.model.Newsfeed;
import thescore.model.PlayerPerformance;
import thescore.model.performance.FieldGoal;
import thescore.model.performance.FreeThrow;
import thescore.model.performance.ThreePointFieldGoal;

@Repository
public class NewsfeedRepository extends AbstractRepository<Integer, Newsfeed>{
	
	public Newsfeed findById(int id) {
		return getByKey(id);
	}
	
	public void saveNewsfeed(Newsfeed newsfeed) {
		persist(newsfeed);
	}

	@SuppressWarnings("unchecked")
	public List<Newsfeed> findNewsfeeds() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.desc("date"));
		return (List<Newsfeed>) criteria.list();
	}
	
	public void updatePlayerPerformanceFinalScores(Match match){
		getSession().createQuery("update " + PlayerPerformance.ENTITY_NAME
				+ " o set finalScore = (select count(ft) from "
					+ FreeThrow.ENTITY_NAME + " ft"
					+ " where ft.performance.id = o.id and ft.missed = false)"
				+ " where o.match.id = :matchId")
				.setParameter("matchId", match.getId())
				.executeUpdate();
		
		getSession().createQuery("update " + PlayerPerformance.ENTITY_NAME
				+ " o set finalScore = o.finalScore + (select count(fg) * 2 from "
					+ FieldGoal.ENTITY_NAME + " fg"
					+ " where fg.performance.id = o.id and fg.missed = false)"
				+ " where o.match.id = :matchId")
				.setParameter("matchId", match.getId())
				.executeUpdate();
		
		getSession().createQuery("update " + PlayerPerformance.ENTITY_NAME
				+ " o set finalScore = o.finalScore + (select count(threefg) from "
					+ ThreePointFieldGoal.ENTITY_NAME + " threefg"
					+ " where threefg.performance.id = o.id and threefg.missed = false)"
				+ " where o.match.id = :matchId")
				.setParameter("matchId", match.getId())
				.executeUpdate();
	}
	
	public void deleteRecordById(Integer id) {
		deleteRecordById(Newsfeed.ENTITY_NAME, id);
	}
	
}
