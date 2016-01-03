package thescore.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import thescore.classes.TeamWinLoseRecord;
import thescore.model.Match;
import thescore.model.Team;

@Repository
public class TeamRepository extends AbstractRepository<Integer, Team> {

	public Team findById(int id) {
		return getByKey(id);
	}

	public void saveTeam(Team team) {
		persist(team);
	}

	public void deleteRecordById(Integer id) {
		deleteRecordById(Team.ENTITY_NAME, id);
	}

	@SuppressWarnings("unchecked")
	public List<Team> findAllTeams() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("name"));
		return (List<Team>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TeamWinLoseRecord> findTeamLoseRecords(Integer teamId){
		Team team = getByKey(teamId);
		
		return getSession().createQuery("select new " + TeamWinLoseRecord.class
				+ " (team, win, lose)"
				+ " select :team,"
				+ " sum(case when o.winner = :team then 1 else 0),"
				+ " sum(case when o.winner = :team then 0 else 1),"
				+ " from " + Match.ENTITY_NAME
				+ " o where o.winner is not null and (o.teamA = :team or o.teamB = :team)")
				.setParameter("team", team)
				.list();
	}
	
	public List<TeamWinLoseRecord> findTeamLoseRecordsByLeague(Integer leagueId){
		
		
		return null;
	}
	
}
