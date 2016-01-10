package thescore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import thescore.interfaces.IRecord;

@Entity(name = Match.ENTITY_NAME)
public class Match implements IRecord {

	public static final String ENTITY_NAME = "tbl_basta_match";
	private static final long serialVersionUID = -6389601980430638175L;

	private Integer id;
	private Date time;
	private Date actualStart;
	private Date actualEnd;
	private String referee;
	
	private Team teamA;
	private Team teamB;
	
	private Integer teamATimeout = 0;
	private Integer teamBTimeout = 0;
	
	private Team winner;
	private League league;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@NotNull(message = "Teams are required.")
	@ManyToOne(targetEntity = Team.class)
	@JoinColumn(name = "teamAId")
	public Team getTeamA() {
		return teamA;
	}

	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}

	@NotNull(message = "Teams are required.")
	@ManyToOne(targetEntity = Team.class)
	@JoinColumn(name = "teamBId")
	public Team getTeamB() {
		return teamB;
	}

	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}

	@ManyToOne(targetEntity = Team.class)
	@JoinColumn(name = "winnerId")
	public Team getWinner() {
		return winner;
	}

	public void setWinner(Team winner) {
		this.winner = winner;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Transient
	@Override
	public String getDisplayString() {
		return "League: " + getLeague().getDisplayString() + " - " + getMatchUp();
	}
	
	@Transient
	public String getMatchUp() {
		return getTeamA().getDisplayString() + " VS. " + getTeamB().getDisplayString();
	}

	@ManyToOne(targetEntity = League.class)
	@JoinColumn(name = "leagueId")
	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	public String getReferee() {
		return referee;
	}

	public void setReferee(String referee) {
		this.referee = referee;
	}

	public Integer getTeamATimeout() {
		return teamATimeout;
	}

	public void setTeamATimeout(Integer teamATimeout) {
		this.teamATimeout = teamATimeout;
	}

	public Integer getTeamBTimeout() {
		return teamBTimeout;
	}

	public void setTeamBTimeout(Integer teamBTimeout) {
		this.teamBTimeout = teamBTimeout;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	public Date getActualStart() {
		return actualStart;
	}

	public void setActualStart(Date actualStart) {
		this.actualStart = actualStart;
	}

	@Override
	public int hashCode() {
		final int prime = 47;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((league == null || league.getId() == null) ? 0 : league.getId().hashCode());
		result = prime * result + ((teamA == null || teamA.getId() == null) ? 0 : teamA.getId().hashCode());
		result = prime * result + ((teamB == null || teamB.getId() == null) ? 0 : teamB.getId().hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Match){
			Match match = (Match) obj;

			if(match.getId() != null && getId() != null){
				return match.getId().equals(getId());
			}
		}
		return super.equals(obj);
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	public Date getActualEnd() {
		return actualEnd;
	}

	public void setActualEnd(Date actualEnd) {
		this.actualEnd = actualEnd;
	}

}
