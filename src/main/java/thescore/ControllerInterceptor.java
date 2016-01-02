package thescore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ControllerInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(modelAndView != null && modelAndView.getModelMap() != null){
			modelAndView.getModelMap().addAttribute("userName", Utility.getSecurityPrincipal());
		}
		super.postHandle(request, response, handler, modelAndView);
	}
	
}
