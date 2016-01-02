package thescore.model.performance;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import thescore.interfaces.IAttempt;
import thescore.interfaces.IPerformanceRecord;
import thescore.model.PlayerPerformance;

@Entity(name = FreeThrow.ENTITY_NAME)
public class FreeThrow implements IPerformanceRecord, IAttempt {

	public static final String ENTITY_NAME = "tbl_basta_player_performance_ft";
	private static final long serialVersionUID = 2547753272415629009L;
	
	private Integer id;
	private PlayerPerformance performance;
	private Integer quarter;
	private Boolean missed;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(targetEntity = PlayerPerformance.class)
	@JoinColumn(name = "performanceId")
	public PlayerPerformance getPerformance() {
		return performance;
	}

	@Override
	public void setPerformance(PlayerPerformance performance) {
		this.performance = performance;
	}

	@Override
	public Integer getQuarter() {
		return quarter;
	}

	@Override
	public void setQuarter(Integer quarter) {
		this.quarter = quarter;
	}

	@Override
	public Boolean getMissed() {
		return missed;
	}

	@Override
	public void setMissed(Boolean missed) {
		this.missed = missed;
	}

	@Transient
	@Override
	public String getDisplayString() {
		return "Free Throw " + (missed ? " Attempt" : "") + " by " + getPerformance().getPlayer().getDisplayString();
	}

}