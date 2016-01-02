package thescore.classes;

import java.text.NumberFormat;

import thescore.model.Player;

public class PlayerCareerRecord {

	private Player player;
	private String action;

	private Integer matches;
	private Integer maxOnSingleMatch;
	private Integer total;
	
	public PlayerCareerRecord(Player player, String action, Integer matches, Integer maxOnSingleMatch, Integer total) {
		this.player = player;
		this.action = action;
		this.matches = matches;
		this.maxOnSingleMatch = maxOnSingleMatch;
		this.total = total;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
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
	
	public String getAverageDisplay(){
		double average = (double) total / (double) matches;
		return NumberFormat.getNumberInstance().format(average);
	}

}
