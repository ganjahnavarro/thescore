package thescore.classes;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionData {
	
	private Integer playerId;
	private Integer matchId;
	private Integer quarter;
	
	private String action;
	private Boolean missed;
	private Boolean subtract;
	
	private Integer teamId;
	
	public Integer getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Integer playerId) {
		this.playerId = playerId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Boolean getMissed() {
		return missed;
	}

	public void setMissed(Boolean missed) {
		this.missed = missed;
	}

	public Integer getMatchId() {
		return matchId;
	}

	public void setMatchId(Integer matchId) {
		this.matchId = matchId;
	}

	public Integer getQuarter() {
		return quarter;
	}

	public void setQuarter(Integer quarter) {
		this.quarter = quarter;
	}

	public Boolean getSubtract() {
		return subtract;
	}

	public void setSubtract(Boolean subtract) {
		this.subtract = subtract;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}
	
}
