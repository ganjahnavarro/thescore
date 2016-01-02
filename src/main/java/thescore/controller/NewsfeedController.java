package thescore.controller;

import java.util.List;

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

import thescore.Utility;
import thescore.model.Newsfeed;
import thescore.service.NewsfeedService;

@Controller
@RequestMapping(value = "/newsfeed")
public class NewsfeedController {
	
	private @Autowired NewsfeedService newsfeedService;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<Newsfeed> newsfeeds = newsfeedService.findNewsfeeds();
		model.addAttribute("newsfeeds", newsfeeds);
		return "newsfeed/list";
	}
	
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String create(ModelMap model) {
		Newsfeed newsfeed = new Newsfeed();
		model.addAttribute("newsfeed", newsfeed);
		model.addAttribute("edit", false);
		return "newsfeed/dataentry";
	}
	
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String save(@Valid Newsfeed newsfeed, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			Utility.parseErrors(result, model);
			return "newsfeed/dataentry";
		}

		newsfeedService.saveNewsfeed(newsfeed);
		model.addAttribute("infoMessage", "Newsfeed " + newsfeed.getDisplayString() + " registered successfully");
		return "redirect:/newsfeed/list";
	}
	
	@RequestMapping(value = { "/edit-{id}-newsfeed" }, method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model) {
		Newsfeed newsfeed = newsfeedService.findById(id);
        model.addAttribute("newsfeed", newsfeed);
        model.addAttribute("edit", true);
        return "newsfeed/dataentry";
    }
     
    @RequestMapping(value = { "/edit-{id}-newsfeed" }, method = RequestMethod.POST)
    public String update(@Valid Newsfeed newsfeed, BindingResult result,
            ModelMap model, @PathVariable Integer id) {
    	
        if (result.hasErrors()) {
        	Utility.parseErrors(result, model);
            return "newsfeed/dataentry";
        }
 
        newsfeedService.updateNewsfeed(newsfeed);
        model.addAttribute("success", "Newsfeed " + newsfeed.getDisplayString()  + " updated successfully");
        return "redirect:/newsfeed/list";	
    }
 
    @RequestMapping(value = { "/delete-{id}-newsfeed" }, method = RequestMethod.GET)
    public String delete(@PathVariable Integer id) {
        newsfeedService.deleteNewsfeedById(id);
        return "redirect:/newsfeed/list";
    }
    
    @RequestMapping(value = { "/view-{id}-newsfeed" }, method = RequestMethod.GET)
    public String view(@PathVariable Integer id, ModelMap model) {
        Newsfeed newsfeed = newsfeedService.findById(id);
        model.addAttribute("newsfeed", newsfeed);
        return "newsfeed/info";
    }
    
    @InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

}
