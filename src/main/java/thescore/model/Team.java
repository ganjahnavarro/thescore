package thescore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import thescore.interfaces.IRecord;

@Entity(name = Team.ENTITY_NAME)
public class Team implements IRecord{

	public static final String ENTITY_NAME = "tbl_basta_team";
	private static final long serialVersionUID = 5590872676675155887L;

	private Integer id;
	private String code;
	private String name;
	private String coach;
	
	private byte[] image;
	private String imageFileName;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	@Column(unique = true)
	@NotBlank(message = "Name is required.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoach() {
		return coach;
	}

	public void setCoach(String coach) {
		this.coach = coach;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Transient
	@Override
	public String getDisplayString() {
		return getName() + " (" + getCode() + ")";
	}

	@NotBlank(message = "Code is required.")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Lob
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	@Override
	public int hashCode() {
		final int prime = 41;
		int result = 1;
		result = prime * result + ((coach == null) ? 0 : coach.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Team){
			Team team = (Team) obj;

			if(team.getId() != null && getId() != null){
				return team.getId().equals(getId());
			}
		}
		return super.equals(obj);
	}
	
}
