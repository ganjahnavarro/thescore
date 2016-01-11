package thescore;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import thescore.model.Notification;
import thescore.model.User;
import thescore.service.NotificationService;
import thescore.service.UserService;

@Component
public class ControllerInterceptor extends HandlerInterceptorAdapter {

	private @Autowired NotificationService notificationService;
	private @Autowired UserService userService;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(notificationService == null){
			notificationService = Utility.getApplicationContext().getBean(NotificationService.class);
		}
		
		if(userService == null){
			userService = Utility.getApplicationContext().getBean(UserService.class);
		}
		
		if(modelAndView != null && modelAndView.getModelMap() != null){
			User user = userService.findByUserName(Utility.getSecurityPrincipal());
			
			modelAndView.getModelMap().addAttribute("userName", Utility.getSecurityPrincipal());
			
			List<Notification> notifications = notificationService.findAllUnseenNotifications(user);
			
			
//			Notification notificationA = new Notification();
//			notificationA.setMessage("Match Result: Golden State Warriors wins over Baranggay Ginebra with score of 99 - 98");
//			
//			Notification notificationB = new Notification();
//			notificationB.setMessage("Incoming Match: Golden State Warriors vs Baranggay Ginebra (01/15/2015 18:30)");
//			
//			Notification notificationC = new Notification();
//			notificationC.setMessage("Incoming Match: Alaska Aces vs FEU Tamaraws (01/15/2015 18:30)");
//
//			notifications = Arrays.asList(notificationA, notificationB, notificationC);
			
			modelAndView.getModelMap().addAttribute("notifications", notifications);
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	
}
