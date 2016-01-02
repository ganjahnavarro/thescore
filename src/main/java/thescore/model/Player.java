package thescore.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import thescore.enums.Position;
import thescore.interfaces.IRecord;

@Entity(name = Player.ENTITY_NAME)
public class Player implements IRecord {

	public static final String ENTITY_NAME = "tbl_basta_player";
	private static final long serialVersionUID = -7541452814819899205L;

	private Integer id;
	private String lastName;
	private String firstName;
	private String middleName;
	
	private Date birthDate;
	private String contactNo;
	private String email;
	
	private Double height;
	private Double weight;
	
	private Team team;
	private Position position;
	
	private byte[] image;
	private String imageFileName;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	@NotBlank(message = "Last name is required.")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@NotBlank(message = "First name is required.")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@ManyToOne(targetEntity = Team.class)
	@JoinColumn(name = "teamId")
	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	@Enumerated(EnumType.STRING)
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Transient
	@Override
	public String getDisplayString() {
		return (lastName != null ? lastName + ", " : "")
				+ (firstName != null ? firstName + " " : "")
				+ (middleName != null ? middleName : "");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	public boolean equals(Object obj) {
		if(obj instanceof Player){
			Player player = (Player) obj;

			if(player.getId() != null && getId() != null){
				return player.getId().equals(getId());
			}
		}
		
		return super.equals(obj);
	}

}
