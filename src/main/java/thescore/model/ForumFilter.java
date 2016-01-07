package thescore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import thescore.interfaces.IRecord;

@Entity(name = ForumFilter.ENTITY_NAME)
public class ForumFilter implements IRecord {

	public static final String ENTITY_NAME = "tbl_basta_forum_filter";
	private static final long serialVersionUID = 5510313417247229896L;

	private Integer id;
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

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
