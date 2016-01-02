package thescore.enums;

import java.util.ArrayList;
import java.util.List;

public enum UserType {

	DEFAULT("User"), COMMITTEE("Committee"), HEAD_COMMITTEE("Head Committee"), ADMIN("Admin");
	
	private String displayString;
	
	private UserType(String displayString){
		this.displayString = displayString;
	}
	
	public Boolean isDefaultUser(){
		return this.equals(UserType.DEFAULT);
	}

	public String getDisplayString() {
		return displayString;
	}
	
	public List<String> getRoles(){
		List<String> roles = new ArrayList<String>();

		for(UserType type : UserType.values()){
			if(type.ordinal() <= this.ordinal()){
				roles.add(type.toString());
			}
		}
		return roles;
	}
	
}
