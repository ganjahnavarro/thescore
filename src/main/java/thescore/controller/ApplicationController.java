package thescore.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import thescore.Utility;
import thescore.enums.Gender;
import thescore.model.User;
import thescore.service.ForumService;
import thescore.service.NewsfeedService;
import thescore.service.UserService;

@Controller
@RequestMapping("/")
public class ApplicationController {

	private @Autowired UserService userService;
	private @Autowired ForumService forumService;
	private @Autowired NewsfeedService newsfeedService;
	
	@RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
	public String home(ModelMap model) {
		model.addAttribute("newsfeeds", newsfeedService.findNewsfeeds());
		model.addAttribute("topics", forumService.findAllTopics());
		model.addAttribute("viewOnly", true);
		return "app/home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "app/login";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			model.addAttribute("infoMessage", "You have been logged out.");
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/home";
	}
	
	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public String accessDenied(ModelMap model) {
		return "app/accessdenied";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(ModelMap model) {
		System.out.println("User Registration @ Application Controller.");
		
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("isCommitteesView", false);
		model.addAttribute("actionParam", "/register?");
		model.addAttribute("edit", false);
		model.addAttribute("title", "Register");
		model.addAttribute("genders", Gender.values());
		return "user/dataentry";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String save(@Valid User user, BindingResult result, ModelMap model,
			@RequestParam CommonsMultipartFile fileUpload, @RequestParam String passwordConfirmation) {
		if(user.getPassword().equals(passwordConfirmation) == false){
			model.addAttribute("errorMessage", "Password doesn't match");
			model.addAttribute("genders", Gender.values());
			model.addAttribute("actionParam", "/register?");
			return "user/dataentry";
		}
		
		if (fileUpload != null && fileUpload.getOriginalFilename() != null
    			&& !fileUpload.getOriginalFilename().isEmpty()) {
			user.setImageFileName(fileUpload.getOriginalFilename());
			user.setImage(fileUpload.getBytes());
		}

		if (result.hasErrors()) {
			Utility.parseErrors(result, model);
			model.addAttribute("genders", Gender.values());
			model.addAttribute("actionParam", "/register?");
			return "user/dataentry";
		}

		userService.saveUser(user);
		model.addAttribute("infoMessage", "User " + user.getDisplayString() + " registered successfully");
		return "app/login";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
}
