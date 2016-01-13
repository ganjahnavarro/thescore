package thescore.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import thescore.Utility;
import thescore.enums.Gender;
import thescore.enums.UserType;
import thescore.model.User;
import thescore.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	private @Autowired UserService service;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<User> users = service.findAllUsers(UserType.DEFAULT);
		model.addAttribute("title", UserType.DEFAULT.getDisplayString());
		model.addAttribute("isCommitteesView", false);
		model.addAttribute("users", users);
		return "user/list";
	}
	
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String create(ModelMap model, @RequestParam("isCommitteesView") Boolean isCommitteesView) {
		User user = new User();
		
		if(isCommitteesView){
			user.setType(UserType.COMMITTEE);
		}
		
		model.addAttribute("user", user);
		model.addAttribute("isCommitteesView", isCommitteesView);
		model.addAttribute("actionParam", "/user/new?");
		model.addAttribute("edit", false);
		model.addAttribute("title", user.getType().getDisplayString());
		model.addAttribute("genders", Gender.values());
		model.addAttribute("types", Arrays.asList(UserType.COMMITTEE, UserType.HEAD_COMMITTEE));
		return "user/dataentry";
	}
	
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String save(@Valid User user, BindingResult result, ModelMap model,
			@RequestParam CommonsMultipartFile fileUpload, @RequestParam String passwordConfirmation) {
		if(user.getPassword().equals(passwordConfirmation) == false){
			model.addAttribute("errorMessage", "Password doesn't match");
			model.addAttribute("actionParam", "/user/new?");
			model.addAttribute("edit", false);
			model.addAttribute("title", user.getType().getDisplayString());
			model.addAttribute("genders", Gender.values());
			model.addAttribute("types", Arrays.asList(UserType.COMMITTEE, UserType.HEAD_COMMITTEE));
			
//			TODO
//			model.addAttribute("isCommitteesView", isCommitteesView);
			
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
			model.addAttribute("actionParam", "/user/new?");
			model.addAttribute("edit", false);
			model.addAttribute("title", user.getType().getDisplayString());
			model.addAttribute("types", Arrays.asList(UserType.COMMITTEE, UserType.HEAD_COMMITTEE));
			return "user/dataentry";
		}

		service.saveUser(user);
		model.addAttribute("infoMessage", "User " + user.getDisplayString() + " registered successfully");
		String redirect = "redirect:" + (user.getType().isDefaultUser() ? "/user/list" : "/user/committees");
		return redirect;
	}
	
	@RequestMapping(value = { "/edit-{id}-user" }, method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model, @RequestParam("isCommitteesView") Boolean isCommitteesView) {
		User user = service.findById(id);
		model.addAttribute("user", user);
		model.addAttribute("isCommitteesView", isCommitteesView);
		model.addAttribute("actionParam", "/user/edit-" + id + "-user?");
		model.addAttribute("edit", true);
		model.addAttribute("title", user.getType().getDisplayString());
		model.addAttribute("genders", Gender.values());
		model.addAttribute("passwordConfirmation", user.getPassword());
		model.addAttribute("types", Arrays.asList(UserType.COMMITTEE, UserType.HEAD_COMMITTEE));
		return "user/dataentry";
    }
     
    @RequestMapping(value = { "/edit-{id}-user" }, method = RequestMethod.POST)
    public String update(@Valid User user, BindingResult result, ModelMap model, @PathVariable Integer id,
            @RequestParam CommonsMultipartFile fileUpload, @RequestParam String passwordConfirmation) {
    	Boolean updateImage = false;
    	
		if (user.getPassword().equals(passwordConfirmation) == false) {
			model.addAttribute("errorMessage", "Password doesn't match");
			model.addAttribute("actionParam", "/user/edit-" + id + "-user?");
			model.addAttribute("edit", true);
			model.addAttribute("title", user.getType().getDisplayString());
			model.addAttribute("genders", Gender.values());
			model.addAttribute("types", Arrays.asList(UserType.COMMITTEE, UserType.HEAD_COMMITTEE));
			
//			TODO
//			model.addAttribute("isCommitteesView", isCommitteesView);
			
			return "user/dataentry";
		}

		if (fileUpload != null && fileUpload.getOriginalFilename() != null
				&& !fileUpload.getOriginalFilename().isEmpty()) {
			user.setImageFileName(fileUpload.getOriginalFilename());
			user.setImage(fileUpload.getBytes());
			updateImage = true;
		}

		if (result.hasErrors()) {
			Utility.parseErrors(result, model);
			model.addAttribute("actionParam", "/user/edit-" + id + "-user?");
			model.addAttribute("edit", true);
			model.addAttribute("title", user.getType().getDisplayString());
			model.addAttribute("genders", Gender.values());
			model.addAttribute("types", Arrays.asList(UserType.COMMITTEE, UserType.HEAD_COMMITTEE));
			return "user/dataentry";
		}

		service.updateUser(user, updateImage);
		model.addAttribute("success", "User " + user.getDisplayString() + " updated successfully");
		String redirect = "redirect:" + (user.getType().isDefaultUser() ? "/user/list" : "/user/committees");
		return redirect;
    }
    
    @RequestMapping(value = "/committees", method = RequestMethod.GET)
	public String committees(ModelMap model) {
		List<User> users = service.findAllUsers(UserType.COMMITTEE, UserType.HEAD_COMMITTEE);
		model.addAttribute("title", UserType.COMMITTEE.getDisplayString());
		model.addAttribute("isCommitteesView", true);
		model.addAttribute("users", users);
		return "user/list";
	}
 
    @RequestMapping(value = { "/delete-{id}-user" }, method = RequestMethod.GET)
    public String delete(@PathVariable Integer id) {
        service.deleteUserById(id);
        return "redirect:/user/list";
    }
    
	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public void showImage(@RequestParam("id") Integer id, HttpServletResponse response, HttpServletRequest request)
			throws ServletException, IOException {
		User user = service.findById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(user.getImage());
		response.getOutputStream().close();
	}
    
    @InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
