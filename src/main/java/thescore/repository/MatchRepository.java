package thescore.repository;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import thescore.model.Match;
import thescore.model.MatchActivePlayer;
import thescore.model.MatchCommittee;
import thescore.model.MatchStartingPlayer;
import thescore.model.Player;
import thescore.model.PlayerPerformance;
import thescore.model.computation.PerformanceComputation;

@Repository
public class MatchRepository extends AbstractRepository<Integer, Match> {

	public Match findById(int id) {
		return getByKey(id);
	}

	public void saveMatch(Match match) {
		persist(match);
	}

	public void deleteRecordById(Integer id) {
		getSession().createQuery("delete from " + MatchStartingPlayer.ENTITY_NAME
				+ " o where o.match.id = :matchId")
				.setParameter("matchId", id)
				.executeUpdate();
		
		getSession().createQuery("delete from " + MatchCommittee.ENTITY_NAME
				+ " o where o.match.id = :matchId")
				.setParameter("matchId", id)
				.executeUpdate();
		
		getSession().createQuery("delete from " + MatchActivePlayer.ENTITY_NAME
				+ " o where o.match.id = :matchId")
				.setParameter("matchId", id)
				.executeUpdate();
		
		getSession().createQuery("delete from " + PerformanceComputation.ENTITY_NAME
				+ " o where o.match.id = :matchId")
				.setParameter("matchId", id)
				.executeUpdate();
		
		deleteRecordById(Match.ENTITY_NAME, id);
	}

	@SuppressWarnings("unchecked")
	public List<Match> findAllMatches(Criterion... criterions) {
		Criteria criteria = createEntityCriteria();
		
		for(Criterion criterion : criterions){
			criteria.add(criterion);
		}
		
		criteria.addOrder(Order.asc("time"));
		return (List<Match>) criteria.list();
	}
	
	public Match updateWinner(Match match){
		Long teamAScore = (Long) getSession().createQuery("select sum(o.finalScore) from " + PlayerPerformance.ENTITY_NAME
				+ " o where o.player.team.id = :teamId and o.match.id = :matchId")
				.setParameter("teamId", match.getTeamA().getId())
				.setParameter("matchId", match.getId())
				.uniqueResult();
		
		Long teamBScore = (Long) getSession().createQuery("select sum(o.finalScore) from " + PlayerPerformance.ENTITY_NAME
				+ " o where o.player.team.id = :teamId and o.match.id = :matchId")
				.setParameter("teamId", match.getTeamB().getId())
				.setParameter("matchId", match.getId())
				.uniqueResult();
		
		Boolean aWins = teamAScore > teamBScore;
		
		getSession().createQuery("update " + Match.ENTITY_NAME + " o"
				+ " set winner = " + (aWins ? "o.teamA" : "o.teamB")
				+ " where o.id = :matchId")
				.setParameter("matchId", match.getId())
				.executeUpdate();
		
		match.setWinner(aWins ? match.getTeamA() : match.getTeamB());
		return match;
	}
	
	@SuppressWarnings("unchecked")
	public List<MatchCommittee> findAllMatchCommittees(Integer matchId) {
		Criteria criteria = getSession().createCriteria(MatchCommittee.class);
		
		if(matchId != null){
			criteria.add(Restrictions.eq("match.id", matchId));
		}
		return (List<MatchCommittee>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<MatchStartingPlayer> findAllMatchPlayers(Integer matchId) {
		Criteria criteria = getSession().createCriteria(MatchStartingPlayer.class);
		criteria.add(Restrictions.eq("match.id", matchId));
		return (List<MatchStartingPlayer>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> findPlayableMatches() {
		return getSession().createSQLQuery("select o.id from " + Match.ENTITY_NAME
				+ " o where o.id in (select mc.matchId from " + MatchCommittee.ENTITY_NAME + " mc)"
				+ " and o.id in (select msp.matchId from " + MatchStartingPlayer.ENTITY_NAME + " msp"
				+ " group by msp.matchId having count(*) = 10)")
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Player> findStartingPlayers(Integer matchId) {
		return getSession().createQuery("select o.player from " + MatchStartingPlayer.ENTITY_NAME
				+ " o where o.match.id = :matchId")
				.setParameter("matchId", matchId)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Match> findMatchesByTeamId(Integer teamId){
		return getSession().createQuery("select o from " + Match.ENTITY_NAME
				+ " o where o.teamA.id = :teamId or o.teamB.id = :teamId"
				+ " order by o.time desc")
				.setParameter("teamId", teamId)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Player> findActivePlayers(Integer matchId) {
		return getSession().createQuery("select o.player from " + MatchActivePlayer.ENTITY_NAME
				+ " o where o.match.id = :matchId")
				.setParameter("matchId", matchId)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Match> findCommitteeIncomingMatches(Integer committeeId){
		Format dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Calendar today = Calendar.getInstance();
		Calendar tommorow = Calendar.getInstance();
		tommorow.add(Calendar.DATE, 1);
		
		String formattedToday = dateFormat.format(today.getTime());
		String formattedTommorow = dateFormat.format(tommorow.getTime());
		
		List<Match> matches = getSession().createQuery("select o from " + Match.ENTITY_NAME
				+ " o where o.id in (select mc.match.id from " + MatchCommittee.ENTITY_NAME
				+ " mc where mc.committee.id = :committeeId)")
				.setParameter("committeeId", committeeId)
				.list();
		
		List<Match> incomingMatches = new ArrayList<Match>();
		
		if(matches != null){
			for(Match match : matches){
				if(match.getTime() != null){
					String formattedMatchTime = dateFormat.format(match.getTime());
					
					if(formattedMatchTime.equals(formattedToday) || formattedMatchTime.equals(formattedTommorow)){
						incomingMatches.add(match);
					}
				}
			}
		}
		return incomingMatches;
	}
	
	public void deleteMatchActivePlayer(Integer matchId, Integer playerId){
		getSession().createQuery("delete from " + MatchActivePlayer.ENTITY_NAME
				+ " o where o.match.id = :matchId and o.player.id = :playerId")
				.setParameter("matchId", matchId)
				.setParameter("playerId", playerId)
				.executeUpdate();
	}
	
}
