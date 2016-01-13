package thescore.repository;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import thescore.classes.TeamWinLoseRecord;
import thescore.model.LeagueTeam;
import thescore.model.Match;
import thescore.model.Player;
import thescore.model.Team;
import thescore.model.computation.PerformanceComputation;

@Repository
public class TeamRepository extends AbstractRepository<Integer, Team> {

	public Team findById(int id) {
		return getByKey(id);
	}

	public void saveTeam(Team team) {
		persist(team);
	}

	public void deleteRecordById(Integer id) {
		getSession().createQuery("delete from " + PerformanceComputation.ENTITY_NAME
				+ " o where o.team.id = :teamId")
				.setParameter("teamId", id)
				.executeUpdate();
		
		deleteRecordById(Team.ENTITY_NAME, id);
	}

	@SuppressWarnings("unchecked")
	public List<Team> findAllTeams() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("name"));
		return (List<Team>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Team> findAllValidTeams() {
		return (List<Team>) getSession().createQuery("select t from " + Team.ENTITY_NAME + " t, " + Player.ENTITY_NAME
				+ " p where t.id = p.team.id group by t.id having count(p.id) >= 5").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TeamWinLoseRecord> findTeamLoseRecords(Integer teamId){
		Team team = getByKey(teamId);
		
		return getSession().createQuery("select new " + TeamWinLoseRecord.class.getName()
				+ " (:team,"
				+ " cast(sum(case when o.winner = :team then 1 else 0 end) as int),"
				+ " cast(sum(case when o.winner = :team then 0 else 1 end) as int))"
				+ " from " + Match.ENTITY_NAME
				+ " o where o.winner is not null and (o.teamA = :team or o.teamB = :team)")
				.setParameter("team", team)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<TeamWinLoseRecord> findTeamLoseRecordsByLeague(Integer leagueId){
		List<Team> teams = getSession().createQuery("select o.team from " + LeagueTeam.ENTITY_NAME
				+ " o where o.league.id = :leagueId order by o.team.name")
				.setParameter("leagueId", leagueId)
				.list();

		List<TeamWinLoseRecord> records = new ArrayList<TeamWinLoseRecord>();
		
		for(Team team : teams){
			Object[] winLose = (Object[]) getSession().createQuery("select"
					+ " sum(case when o.winner = :team then 1 else 0 end),"
					+ " sum(case when o.winner = :team then 0 else 1 end)"
					+ " from " + Match.ENTITY_NAME
					+ " o where o.winner is not null and (o.teamA = :team or o.teamB = :team)")
					.setParameter("team", team)
					.uniqueResult();
			
			long win = winLose[0] != null ? (long) winLose[0] : 0;
			long lose = winLose[1] != null ? (long) winLose[1] : 0;
			
			records.add(new TeamWinLoseRecord(team, Integer.valueOf(String.valueOf(win)), Integer.valueOf(String.valueOf(lose))));
		}
		return records;
	}
	
}

