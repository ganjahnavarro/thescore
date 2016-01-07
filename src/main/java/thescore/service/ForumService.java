package thescore.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.Utility;
import thescore.model.Comment;
import thescore.model.ForumFilter;
import thescore.model.Topic;
import thescore.repository.ForumRepository;

@Service
@Transactional
public class ForumService {

	private @Autowired ForumRepository repository;

	public Topic findTopicById(int id) {
		return repository.findTopicById(id);
	}
	
	public Comment findCommentById(int id) {
		return repository.findCommentById(id);
	}

	public void saveTopic(Topic topic) {
		if(topic.getDate() == null){
			topic.setDate(new Date());
		}
		
		if(topic.getEntryBy() == null){
			topic.setEntryBy(Utility.getSecurityPrincipal());
		}
		
		repository.saveTopic(topic);
	}
	
	public void saveComment(Comment comment) {
		if(comment.getDate() == null){
			comment.setDate(new Date());
		}
		
		if(comment.getEntryBy() == null){
			comment.setEntryBy(Utility.getSecurityPrincipal());
		}
		
		repository.saveComment(comment);
	}

	public void updateTopic(Topic source) {
		Topic destination = repository.findTopicById(source.getId());

		if (destination != null) {
			try {
				PropertyUtils.copyProperties(destination, source);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteTopicById(Integer id) {
		repository.deleteRecordById(id);
	}
	
	public void deleteCommentById(Integer id) {
		repository.deleteRecordById(Comment.ENTITY_NAME, id);
	}

	public List<Topic> findAllTopics() {
		return repository.findAllTopics();
	}
	
	public List<Comment> findAllComments(Integer topicId) {
		return repository.findAllComments(topicId);
	}
	
	public void updateForumFilter(ForumFilter forumFilter) {
		repository.merge(forumFilter);
	}
	
	public ForumFilter findDefaultForumFilter(){
		return repository.findDefaultForumFilter();
	}
	
	public Boolean isCommentValueValid(String value){
		return repository.isCommentValueValid(value);
	}
	
}
