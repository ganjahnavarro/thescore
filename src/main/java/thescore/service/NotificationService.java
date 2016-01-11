package thescore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.model.Notification;
import thescore.model.User;
import thescore.repository.NotificationRepository;

@Service
@Transactional
public class NotificationService {
	
	private @Autowired NotificationRepository repository;
	
	public Notification findById(int id) {
		return repository.getByKey(id);
	}

	public void saveNotification(Notification notification) {
		repository.saveNotification(notification);
	}

	public void deleteRecordById(Integer id) {
		repository.deleteRecordById(id);
	}

	public List<Notification> findAllNotifications() {
		return repository.findAllNotifications();
	}
	
	public List<Notification> findAllUnseenNotifications(User user) {
		return repository.findAllUnseenNotifications(user);
	}
	
	public void updateUnseenNotifications(User user) {
		repository.updateUnseenNotifications(user);
	}
	
}
