package thescore;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

	@ExceptionHandler(Exception.class)
	public String handleError(Exception exception){
		exception.printStackTrace();
		return "app/404";
	}
	
}
