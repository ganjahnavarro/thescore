package thescore.enums;

public enum Position {

	POINT_GUARD(1, "PG", "Point Guard"),
	SHOOTING_GUARD(2, "SG", "Shooting Guard"),
	SMALL_FORWARD(3, "SF", "Small Forward"),
	POWER_FORWARD(4, "PF", "Power Forward"),
	CENTER(5, "C", "Center");
	
	private Integer id;
	private String code;
	private String displayString;
	
	private Position(Integer id, String code, String displayString){
		this.id = id;
		this.code = code;
		this.displayString = displayString;
	}
	
	public Integer getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getDisplayString() {
		return displayString;
	}

}
