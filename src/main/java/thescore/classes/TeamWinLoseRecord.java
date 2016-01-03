package thescore.classes;

import thescore.model.Team;

public class TeamWinLoseRecord {

	private Team team;
	private Integer win;
	private Integer lose;
	
	public TeamWinLoseRecord(Team team, Integer win, Integer lose){
		this.team = team;
		this.win = win;
		this.lose = lose;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Integer getWin() {
		return win;
	}

	public void setWin(Integer win) {
		this.win = win;
	}

	public Integer getLose() {
		return lose;
	}

	public void setLose(Integer lose) {
		this.lose = lose;
	}

}
