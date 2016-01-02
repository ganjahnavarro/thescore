package thescore.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import thescore.enums.Gender;
import thescore.enums.UserType;
import thescore.interfaces.IRecord;

@Entity(name = User.ENTITY_NAME)
public class User implements IRecord{

	public static final String ENTITY_NAME = "tbl_basta_user";
	private static final long serialVersionUID = 8438443391159451383L;

	private Integer id;
	private UserType type = UserType.DEFAULT;
	private Gender gender = Gender.MALE;
	
	private String lastName;
	private String firstName;
	private String middleName;
	
	private Date birthDate;
	private String contactNo;
	private String email;
	
	private String userName;
	private String password;
	private Boolean active = true;
	
	private byte[] image;
	private String imageFileName;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	@NotNull(message = "Type is required.")
	@Enumerated(EnumType.STRING)
	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
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

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Enumerated(EnumType.STRING)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Transient
	@Override
	public String getDisplayString() {
		return (lastName != null ? lastName + ", " : "")
				+ (firstName != null ? firstName + " " : "")
				+ (middleName != null ? middleName : "");
	}

	@Column(unique = true)
	@NotBlank(message = "Username is required.")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@NotBlank(message = "Password is required.")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
