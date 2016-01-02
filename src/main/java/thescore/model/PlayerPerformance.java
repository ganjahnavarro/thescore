package thescore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import thescore.interfaces.IRecord;

@Entity(name = PlayerPerformance.ENTITY_NAME)
public class PlayerPerformance implements IRecord {

	public static final String ENTITY_NAME = "tbl_basta_player_performance";
	private static final long serialVersionUID = 8289817067161153547L;

	private Integer id;
	private Player player;
	private Match match;
	private Integer finalScore;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = Player.class)
	@JoinColumn(name = "playerId")
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	@ManyToOne(targetEntity = Match.class)
	@JoinColumn(name = "matchId")
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	@Transient
	@Override
	public String getDisplayString() {
		return "Match: " + getMatch().getDisplayString()
			+ ". Player: " + getPlayer().getDisplayString();
	}

	public Integer getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(Integer finalScore) {
		this.finalScore = finalScore;
	}

}
