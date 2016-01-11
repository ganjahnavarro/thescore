package thescore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import thescore.interfaces.IRecord;

@Entity(name = MatchActivePlayer.ENTITY_NAME)
public class MatchActivePlayer implements IRecord {

	public static final String ENTITY_NAME = "tbl_basta_match_active_player";
	private static final long serialVersionUID = 4831938380621856046L;

	private Integer id;
	private Match match;
	private Player player;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	@Transient
	@Override
	public String getDisplayString() {
		return null;
	}

	@NotNull
	@ManyToOne(targetEntity = Match.class)
	@JoinColumn(name = "matchId")
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	@NotNull
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

}
