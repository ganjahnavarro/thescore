package thescore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import thescore.enums.UserType;
import thescore.model.performance.Assist;
import thescore.model.performance.Block;
import thescore.model.performance.DefensiveRebound;
import thescore.model.performance.FieldGoal;
import thescore.model.performance.Foul;
import thescore.model.performance.FreeThrow;
import thescore.model.performance.OffensiveRebound;
import thescore.model.performance.Steal;
import thescore.model.performance.ThreePointFieldGoal;
import thescore.model.performance.Turnover;

@Component
@SuppressWarnings("rawtypes")
public final class Utility implements ApplicationContextAware{
	
	private static ApplicationContext context;
	private static Map<String, Class> performanceClasses;
	
	static {
		performanceClasses = new ConcurrentHashMap<String, Class>();
		performanceClasses.put("FG", FieldGoal.class);
		performanceClasses.put("FGA", FieldGoal.class);
		
		performanceClasses.put("3FG", ThreePointFieldGoal.class);
		performanceClasses.put("3FGA", ThreePointFieldGoal.class);
		
		performanceClasses.put("FT", FreeThrow.class);
		performanceClasses.put("FTA", FreeThrow.class);
		
		performanceClasses.put("AST", Assist.class);
		performanceClasses.put("BLK", Block.class);
		performanceClasses.put("STL", Steal.class);
		
		performanceClasses.put("DEF", DefensiveRebound.class);
		performanceClasses.put("OFF", OffensiveRebound.class);
		
		performanceClasses.put("TO", Turnover.class);
		performanceClasses.put("FOUL", Foul.class);
	}
	
	public static Map<String, Class> getPerformanceClasses(){
		return performanceClasses;
	}
	
	public static void parseErrors(BindingResult result, ModelMap model){
		String errorMessage = "";
		
    	for(FieldError error : result.getFieldErrors()){
    		errorMessage += error.getDefaultMessage() + " ";
    	}
    	
    	model.addAttribute("errorMessage", errorMessage);
	}

	public static ApplicationContext getApplicationContext() {
        return context;
    }
	
	public static String getSecurityPrincipal() {
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (principal instanceof UserDetails) {
				return ((UserDetails) principal).getUsername();
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static int getCurrentUserAccess() {
		if(SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null){
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (principal instanceof UserDetails) {
				Iterator<GrantedAuthority> iterator = (Iterator<GrantedAuthority>) ((UserDetails) principal).getAuthorities().iterator();
				
				List<String> roles = new ArrayList<String>();
				while(iterator.hasNext()){
					roles.add(iterator.next().toString());
				}

				List<UserType> types = Arrays.asList(UserType.values());
				Collections.reverse(types);
				
				for(UserType type : types){
					for(String role : roles){
						if(role.equalsIgnoreCase("ROLE_" + type.toString())){
							return type.ordinal();
						}
					}
				}
			}
		}
		return 0;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		Utility.context = context;
	}
	
}
