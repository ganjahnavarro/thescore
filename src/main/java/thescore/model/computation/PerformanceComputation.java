package thescore.model.computation;

import java.text.NumberFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import thescore.interfaces.IRecord;
import thescore.model.League;
import thescore.model.Match;
import thescore.model.Player;

@Entity(name = PerformanceComputation.ENTITY_NAME)
public class PerformanceComputation implements IRecord{

	public static final String ENTITY_NAME = "tbl_basta_performance_computation";
	private static final long serialVersionUID = 4483546492391029336L;

	private Integer id;
	private String action;
	private String sessionId;
	
	private Integer matches;
	private Integer maxOnSingleMatch;
	private Integer total;
	
	private Player player;
	private League league;
	private Match match;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@ManyToOne(targetEntity = Player.class)
	@JoinColumn(name = "playerId")
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getMatches() {
		return matches;
	}

	public void setMatches(Integer matches) {
		this.matches = matches;
	}

	public Integer getMaxOnSingleMatch() {
		return maxOnSingleMatch;
	}

	public void setMaxOnSingleMatch(Integer maxOnSingleMatch) {
		this.maxOnSingleMatch = maxOnSingleMatch;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	@Transient
	@Override
	public String getDisplayString() {
		double average = (double) total / (double) matches;
		average = Double.isNaN(average) ? 0 : average;
		return NumberFormat.getNumberInstance().format(average);
	}

	@ManyToOne(targetEntity = League.class)
	@JoinColumn(name = "leagueId")
	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	@ManyToOne(targetEntity = Match.class)
	@JoinColumn(name = "matchId")
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
	
}
