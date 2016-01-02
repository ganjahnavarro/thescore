package thescore.controller;

import java.io.IOException;
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
import thescore.model.Team;
import thescore.service.TeamService;

@Controller
@RequestMapping("/team")
public class TeamController {
	
	private @Autowired TeamService service;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<Team> teams = service.findAllTeams();
		model.addAttribute("teams", teams);
		return "team/list";
	}
	
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String create(ModelMap model) {
		Team team = new Team();
		model.addAttribute("team", team);
		model.addAttribute("edit", false);
		model.addAttribute("actionParam", "/team/new?");
		return "team/dataentry";
	}
	
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String save(@Valid Team team, BindingResult result, ModelMap model,
			@RequestParam CommonsMultipartFile fileUpload) {
		if (fileUpload != null && fileUpload.getOriginalFilename() != null
    			&& !fileUpload.getOriginalFilename().isEmpty()) {
			team.setImageFileName(fileUpload.getOriginalFilename());
			team.setImage(fileUpload.getBytes());
		}

		if (result.hasErrors()) {
			Utility.parseErrors(result, model);
			model.addAttribute("actionParam", "/team/new?");
			return "team/dataentry";
		}

		service.saveTeam(team);
		model.addAttribute("infoMessage", "Team " + team.getDisplayString() + " registered successfully");
		return "redirect:/team/list";
	}
	
	@RequestMapping(value = { "/edit-{id}-team" }, method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model) {
		Team team = service.findById(id);
		model.addAttribute("team", team);
		model.addAttribute("edit", true);
		model.addAttribute("actionParam", "/team/edit-" + id + "-team?");
        return "team/dataentry";
    }
     
    @RequestMapping(value = { "/edit-{id}-team" }, method = RequestMethod.POST)
    public String update(@Valid Team team, BindingResult result, ModelMap model, @PathVariable Integer id,
            @RequestParam CommonsMultipartFile fileUpload) {
    	if (fileUpload != null && fileUpload.getOriginalFilename() != null
    			&& !fileUpload.getOriginalFilename().isEmpty()) {
			team.setImageFileName(fileUpload.getOriginalFilename());
			team.setImage(fileUpload.getBytes());
		}
    	
        if (result.hasErrors()) {
        	Utility.parseErrors(result, model);
        	model.addAttribute("actionParam", "/team/edit-" + id + "-team?");
            return "team/dataentry";
        }
 
        service.updateTeam(team);
        model.addAttribute("success", "Team " + team.getDisplayString()  + " updated successfully");
        return "redirect:/team/list";	
    }
 
    @RequestMapping(value = { "/delete-{id}-team" }, method = RequestMethod.GET)
    public String delete(@PathVariable Integer id) {
        service.deleteTeamById(id);
        return "redirect:/team/list";
    }
    
    @RequestMapping(value = { "/view-{id}-team" }, method = RequestMethod.GET)
    public String view(@PathVariable Integer id, ModelMap model) {
        Team team = service.findById(id);
        model.addAttribute("team", team);
        return "team/info";
    }
    
	@RequestMapping(value = "/image", method = RequestMethod.GET)
	public void showImage(@RequestParam("id") Integer id, HttpServletResponse response, HttpServletRequest request)
			throws ServletException, IOException {
		Team team = service.findById(id);
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
		response.getOutputStream().write(team.getImage());
		response.getOutputStream().close();
	}
    
    @InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	    binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
	}

}
