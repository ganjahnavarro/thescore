package thescore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import thescore.interfaces.IRecord;

@Entity(name = NotificationView.ENTITY_NAME)
public class NotificationView implements IRecord {

	public static final String ENTITY_NAME = "tbl_basta_notification_view";
	private static final long serialVersionUID = -852879244477515948L;

	private Integer id;
	private Notification notification;
	private User user;

	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	@Transient
	@Override
	public String getDisplayString() {
		return null;
	}

	@NotNull
	@ManyToOne(targetEntity = Notification.class)
	@JoinColumn(name = "notificationId")
	public Notification getNotification() {
		return notification;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	@NotNull
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
