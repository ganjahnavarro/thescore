package thescore.interfaces;

import thescore.model.PlayerPerformance;

public interface IPerformanceRecord extends IRecord{

	PlayerPerformance getPerformance();
	
	void setPerformance(PlayerPerformance playerPerformance);
	
	Integer getQuarter();
	
	void setQuarter(Integer quarter);
	
}
