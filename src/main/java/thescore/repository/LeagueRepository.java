package thescore.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import thescore.model.League;
import thescore.model.LeagueMythicalPlayer;
import thescore.model.LeagueTeam;
import thescore.model.Player;
import thescore.model.computation.PerformanceComputation;

@Repository
public class LeagueRepository extends AbstractRepository<Integer, League> {

	public League findById(int id) {
		return getByKey(id);
	}

	public void saveLeague(League league) {
		persist(league);
	}
	
	public void deleteRecordById(Integer id) {
		getSession().createQuery("delete from " + LeagueMythicalPlayer.ENTITY_NAME
				+ " o where o.league.id = :leagueId")
				.setParameter("leagueId", id)
				.executeUpdate();
		
		getSession().createQuery("delete from " + LeagueTeam.ENTITY_NAME
				+ " o where o.league.id = :leagueId")
				.setParameter("leagueId", id)
				.executeUpdate();
		
		getSession().createQuery("delete from " + PerformanceComputation.ENTITY_NAME
				+ " o where o.league.id = :leagueId")
				.setParameter("leagueId", id)
				.executeUpdate();
		
		deleteRecordById(League.ENTITY_NAME, id);
	}

	@SuppressWarnings("unchecked")
	public List<League> findAllLeagues() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("name"));
		return (List<League>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Player> findMythicalFive(Integer leagueId) {
		return getSession().createQuery("select o.player from " + LeagueMythicalPlayer.ENTITY_NAME
				+ " o where o.league.id = :leagueId")
				.setParameter("leagueId", leagueId)
				.setMaxResults(5)
				.list();
	}
	
	public LeagueMythicalPlayer findMythicalPlayer(Integer leagueId, Integer playerId) {
		return (LeagueMythicalPlayer) getSession().createQuery("select o from " + LeagueMythicalPlayer.ENTITY_NAME
				+ " o where o.league.id = :leagueId and o.player.id = :playerId")
				.setParameter("leagueId", leagueId)
				.setParameter("playerId", playerId)
				.setMaxResults(1)
				.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<LeagueTeam> findAllLeagueTeams(Integer leagueId) {
		Criteria criteria = getSession().createCriteria(LeagueTeam.class)
				.createAlias("team", "t")
				.add(Restrictions.eq("league.id", leagueId))
				.addOrder(Order.desc("t.name"));
		
		return (List<LeagueTeam>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<League> findChampionships(Integer teamId) {
		return createEntityCriteria()
				.add(Restrictions.eq("champion.id", teamId))
				.addOrder(Order.desc("startDate"))
				.list();
	}
	
}
