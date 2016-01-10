package thescore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import thescore.interfaces.IRecord;

@Entity(name = Topic.ENTITY_NAME)
public class Topic implements IRecord{
	
	public static final String ENTITY_NAME = "tbl_basta_topic";
	private static final long serialVersionUID = -8557603452430549281L;

	private Integer id;
	private String title;
	private String description;

	private Date date = new Date();
	private String entryBy;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	@Transient
	@Override
	public String getDisplayString() {
		return title;
	}

	@NotNull(message = "Title is required.")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
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

	public void setId(Integer id) {
		this.id = id;
	}

}
