package thescore;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import thescore.enums.UserType;
import thescore.model.Match;
import thescore.model.Notification;
import thescore.model.User;
import thescore.service.MatchService;
import thescore.service.NotificationService;
import thescore.service.UserService;

@Component
public class ControllerInterceptor extends HandlerInterceptorAdapter {

	private @Autowired NotificationService notificationService;
	private @Autowired UserService userService;
	private @Autowired MatchService matchService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		initializeServices();
		
		if(modelAndView != null && modelAndView.getModelMap() != null){
			User user = userService.findByUserName(Utility.getSecurityPrincipal());
			
			modelAndView.getModelMap().addAttribute("userName", Utility.getSecurityPrincipal());
			
			List<Notification> notifications = notificationService.findAllUnseenNotifications(user);
			
			List<Notification> computedNotifications = getComputedNotification(user);
			
			if(notifications == null){
				notifications = new ArrayList<Notification>();
			}
			
			if(computedNotifications != null && !computedNotifications.isEmpty()){
				notifications.addAll(computedNotifications);
			}
			
			modelAndView.getModelMap().addAttribute("notifications", notifications);
		}
		super.postHandle(request, response, handler, modelAndView);
	}

	private void initializeServices() {
		if(notificationService == null){
			notificationService = Utility.getApplicationContext().getBean(NotificationService.class);
		}
		
		if(userService == null){
			userService = Utility.getApplicationContext().getBean(UserService.class);
		}
		
		if(matchService == null){
			matchService = Utility.getApplicationContext().getBean(MatchService.class);
		}
	}
	
	private List<Notification> getComputedNotification(User user){
		Format dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		List<Notification> computedNotifications = new ArrayList<Notification>();
		
		if(Utility.getCurrentUserAccess() == UserType.COMMITTEE.ordinal()){
			List<Match> incomingMatches = matchService.findCommitteeIncomingMatches(user.getId());
			
			for(Match match : incomingMatches){
				Notification notification = new Notification();
				notification.setDate(match.getTime());
				notification.setMessage("You have a match to umpire (" + dateFormat.format(match.getTime()) + ") " + match.getDisplayString());
				notification.setUrl("/match/view-" + match.getId() + "-match");
				computedNotifications.add(notification);
			}
		}
		return computedNotifications;
	}
	
}
