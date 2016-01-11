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

@Entity(name = LeagueMythicalPlayer.ENTITY_NAME)
public class LeagueMythicalPlayer implements IRecord {

	public static final String ENTITY_NAME = "tbl_basta_league_mythical_player";
	private static final long serialVersionUID = 7765477919936910703L;
	
	private Integer id;
	private League league;
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
		return player.getDisplayString();
	}

	@NotNull
	@ManyToOne(targetEntity = League.class)
	@JoinColumn(name = "leagueId")
	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
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
