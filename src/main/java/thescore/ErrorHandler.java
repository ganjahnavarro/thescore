package thescore;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(Exception.class)
	public String handleError(Exception exception, ModelMap model){
		model.addAttribute("exceptionMessage", exception.toString());
		exception.printStackTrace();
		return "app/404";
	}
	
}
