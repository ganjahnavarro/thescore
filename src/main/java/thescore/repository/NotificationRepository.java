package thescore.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import thescore.model.Notification;
import thescore.model.NotificationView;
import thescore.model.User;

@Repository
public class NotificationRepository extends AbstractRepository<Integer, Notification> {
	
	public Notification findById(int id) {
		return getByKey(id);
	}

	public void saveNotification(Notification notification) {
		persist(notification);
	}

	public void deleteRecordById(Integer id) {
		deleteRecordById(Notification.ENTITY_NAME, id);
	}

	@SuppressWarnings("unchecked")
	public List<Notification> findAllNotifications() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.desc("date"));
		return (List<Notification>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Notification> findAllUnseenNotifications(User user) {
		return getSession().createQuery("select o from " + Notification.ENTITY_NAME
				+ " o where o.id not in (select v.notification.id from " + NotificationView.ENTITY_NAME
				+ " v where v.user = :user)")
				.setParameter("user", user)
				.list();
	}
	
	public void updateUnseenNotifications(User user) {
		getSession().createQuery("insert into " + NotificationView.ENTITY_NAME
				+ " (notification, user)"
				+ " select o, :user from " + Notification.ENTITY_NAME
				+ " o where o.id not in (select v.notification.id from " + NotificationView.ENTITY_NAME
				+ " v where v.user = :user)")
				.setParameter("user", user)
				.executeUpdate();
	}

}
