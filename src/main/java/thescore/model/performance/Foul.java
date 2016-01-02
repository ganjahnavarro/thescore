package thescore.model.performance;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import thescore.interfaces.IPerformanceRecord;
import thescore.model.PlayerPerformance;

@Entity(name = Foul.ENTITY_NAME)
public class Foul  implements IPerformanceRecord {

	public static final String ENTITY_NAME = "tbl_basta_player_performance_foul";
	private static final long serialVersionUID = -295518530939951754L;
	
	private Integer id;
	private PlayerPerformance performance;
	private Integer quarter;
	
	@Id
	@Override
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
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

	@Transient
	@Override
	public String getDisplayString() {
		return "Offensive Foul by " + getPerformance().getPlayer().getDisplayString();
	}

}
