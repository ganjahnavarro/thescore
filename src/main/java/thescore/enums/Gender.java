package thescore.enums;

public enum Gender {

	MALE("M", "Male"), FEMALE("F", "Female");
	
	private String code;
	private String displayString;
	
	private Gender(String code, String displayString){
		this.code = code;
		this.displayString = displayString;
	}

	public String getCode() {
		return code;
	}

	public String getDisplayString() {
		return displayString;
	}
	
}
