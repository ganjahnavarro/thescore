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

@Entity(name = MatchCommittee.ENTITY_NAME)
public class MatchCommittee implements IRecord {

	public static final String ENTITY_NAME = "tbl_basta_match_committee";
	private static final long serialVersionUID = -7063819444524334765L;

	private Integer id;
	private Match match;
	private User committee;
	
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
	@ManyToOne(targetEntity = Match.class)
	@JoinColumn(name = "matchId")
	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	@NotNull
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "committeeId")
	public User getCommittee() {
		return committee;
	}

	public void setCommittee(User committee) {
		this.committee = committee;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
