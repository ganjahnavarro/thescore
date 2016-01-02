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

@Entity(name = LeagueTeam.ENTITY_NAME)
public class LeagueTeam implements IRecord {

	public static final String ENTITY_NAME = "tbl_basta_league_team";
	private static final long serialVersionUID = -1121887567926120275L;

	private Integer id;
	private League league;
	private Team team;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
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
	@ManyToOne(targetEntity = Team.class)
	@JoinColumn(name = "teamId")
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Transient
	@Override
	public String getDisplayString() {
		return null;
	}

}
