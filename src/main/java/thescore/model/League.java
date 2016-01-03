package thescore.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import thescore.interfaces.IRecord;


@Entity(name = League.ENTITY_NAME)
public class League implements IRecord {

	public static final String ENTITY_NAME = "tbl_basta_league";
	private static final long serialVersionUID = 3280044419228748778L;
	
	private Integer id;
	private String name;
	private String description;
	
	private Date startDate;
	private Date endDate;
	
	private String address;
	private BigDecimal prize;
	private Integer maxTeamCount;
	
	private Date lockedDate;
	private Team champion;
	private Boolean generated = false;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(unique = true)
	@NotBlank(message = "Name is required.")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Transient
	@Override
	public String getDisplayString() {
		return getName();
	}
	
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@DateTimeFormat(pattern = "MM/dd/yyyy")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BigDecimal getPrize() {
		return prize;
	}

	public void setPrize(BigDecimal prize) {
		this.prize = prize;
	}

	public Integer getMaxTeamCount() {
		return maxTeamCount;
	}

	public void setMaxTeamCount(Integer maxTeamCount) {
		this.maxTeamCount = maxTeamCount;
	}

	@DateTimeFormat(pattern = "MM/dd/yyyy HH:mm")
	public Date getLockedDate() {
		return lockedDate;
	}

	public void setLockedDate(Date lockedDate) {
		this.lockedDate = lockedDate;
	}

	@ManyToOne(targetEntity = Team.class)
	@JoinColumn(name = "winnerTeamId")
	public Team getChampion() {
		return champion;
	}

	public void setChampion(Team champion) {
		this.champion = champion;
	}

	public Boolean getGenerated() {
		return generated;
	}

	public void setGenerated(Boolean generated) {
		this.generated = generated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((prize == null) ? 0 : prize.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof League){
			League league = (League) obj;

			if(league.getId() != null && getId() != null){
				return league.getId().equals(getId());
			}
		}
		return super.equals(obj);
	}

}
