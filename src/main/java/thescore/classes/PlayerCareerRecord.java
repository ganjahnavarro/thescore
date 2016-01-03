package thescore.classes;

import java.text.NumberFormat;

import thescore.model.Player;

public class PlayerCareerRecord {

	private Player player;
	private String action;

	private Number matches;
	private Number maxOnSingleMatch;
	private Number total;
	
	public PlayerCareerRecord(Player player, String action, Integer matches, Integer maxOnSingleMatch, Integer total) {
		this.player = player;
		this.action = action;
		this.matches = matches;
		this.maxOnSingleMatch = maxOnSingleMatch;
		this.total = total;
	}
	
	public PlayerCareerRecord(Player player, String action, Double matches, Double maxOnSingleMatch, Double total) {
		this.player = player;
		this.action = action;
		this.matches = matches;
		this.maxOnSingleMatch = maxOnSingleMatch;
		this.total = total;
	}
	
	public PlayerCareerRecord(Player player, String action, Long matches, Long maxOnSingleMatch, Long total) {
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

	public Number getMatches() {
		return matches;
	}

	public void setMatches(Number matches) {
		this.matches = matches;
	}

	public Number getMaxOnSingleMatch() {
		return maxOnSingleMatch;
	}

	public void setMaxOnSingleMatch(Number maxOnSingleMatch) {
		this.maxOnSingleMatch = maxOnSingleMatch;
	}

	public Number getTotal() {
		return total;
	}

	public void setTotal(Number total) {
		this.total = total;
	}
	
	public String getAverageDisplay(){
		double average = (double) total / (double) matches;
		average = Double.isNaN(average) ? 0 : average;
		return NumberFormat.getNumberInstance().format(average);
	}

}
