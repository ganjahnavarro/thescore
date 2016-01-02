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
import thescore.model.Comment;
import thescore.model.Topic;
import thescore.service.ForumService;

@Controller
@RequestMapping(value = "/topic")
public class ForumController {
	
	private @Autowired ForumService forumService;
	
	@RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
	public String list(ModelMap model) {
		List<Topic> topics = forumService.findAllTopics();
		model.addAttribute("topics", topics);
		return "topic/list";
	}
	
	@RequestMapping(value = { "/new" }, method = RequestMethod.GET)
	public String create(ModelMap model) {
		Topic topic = new Topic();
		model.addAttribute("topic", topic);
		model.addAttribute("edit", false);
		return "topic/dataentry";
	}
	
	@RequestMapping(value = { "/new" }, method = RequestMethod.POST)
	public String save(@Valid Topic topic, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			Utility.parseErrors(result, model);
			return "topic/dataentry";
		}
		forumService.saveTopic(topic);
		model.addAttribute("infoMessage", "Topic " + topic.getDisplayString() + " registered successfully");
		return "redirect:/topic/list";
	}
	
	@RequestMapping(value = { "/edit-{id}-topic" }, method = RequestMethod.GET)
    public String edit(@PathVariable Integer id, ModelMap model) {
        Topic topic = forumService.findTopicById(id);
        model.addAttribute("topic", topic);
        model.addAttribute("edit", true);
        return "topic/dataentry";
    }
     
	@RequestMapping(value = { "/edit-{id}-topic" }, method = RequestMethod.POST)
	public String update(@Valid Topic topic, BindingResult result, ModelMap model, @PathVariable Integer id) {
		if (result.hasErrors()) {
			Utility.parseErrors(result, model);
			return "topic/dataentry";
		}
		forumService.updateTopic(topic);
		model.addAttribute("success", "Topic " + topic.getDisplayString() + " updated successfully");
		return "redirect:/topic/list";
	}
 
	@RequestMapping(value = { "/delete-{id}-topic" }, method = RequestMethod.GET)
	public String delete(@PathVariable Integer id) {
		forumService.deleteTopicById(id);
		return "redirect:/topic/list";
	}
    
	@RequestMapping(value = { "/view-{id}-topic" }, method = RequestMethod.GET)
	public String view(@PathVariable Integer id, ModelMap model) {
		Topic topic = forumService.findTopicById(id);
		model.addAttribute("comment", new Comment());
		model.addAttribute("topic", topic);
		model.addAttribute("comments", forumService.findAllComments(topic.getId()));
		return "topic/thread";
	}
	
	@RequestMapping(value = { "/view-{id}-topic" }, method = RequestMethod.POST)
	public String addComment(@PathVariable Integer id, @Valid Comment comment, BindingResult result, ModelMap model) {
		if (result.hasErrors()) {
			Utility.parseErrors(result, model);
			return "topic/thread";
		}
		Topic topic = forumService.findTopicById(id);
		comment.setTopic(topic);
		forumService.saveComment(comment);
		return "redirect:/topic/view-" + comment.getTopic().getId() + "-topic";
	}
	
	@RequestMapping(value = { "/comment/delete-{id}-comment" }, method = RequestMethod.GET)
	public String deleteComment(@PathVariable Integer id) {
		Comment comment = forumService.findCommentById(id);
		forumService.deleteCommentById(comment.getId());
		return "redirect:/topic/view-" + comment.getTopic().getId() + "-topic";
	}
    
    @InitBinder
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

}
