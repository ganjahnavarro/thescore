package thescore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import thescore.interfaces.IRecord;

@Entity(name = Comment.ENTITY_NAME)
public class Comment implements IRecord{

	public static final String ENTITY_NAME = "tbl_basta_comment";
	private static final long serialVersionUID = -1143037023294144341L;

	private Integer id;
	private Date date;
	private String entryBy;

	private Topic topic;
	private String value;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	@Transient
	@Override
	public String getDisplayString() {
		return value;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEntryBy() {
		return entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	@ManyToOne(targetEntity = Topic.class)
	@JoinColumn(name = "topicId")
	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	@NotBlank(message = "Message is required.")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
