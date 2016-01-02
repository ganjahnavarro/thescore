package thescore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.classes.PlayerCareerRecord;
import thescore.interfaces.IPerformanceRecord;
import thescore.interfaces.IRecord;
import thescore.model.PlayerPerformance;
import thescore.repository.PlayerPerformanceRepository;

@Service
@Transactional
public class PlayerPerformanceService {
	
	private @Autowired PlayerPerformanceRepository repository;
	
	public void savePerformance(IRecord performance) {
        repository.savePerformance(performance);
    }
 
    public void deletePerformance(IRecord performance) {
        repository.deletePerformance(performance);
    }
    
	public List<Integer> findPlayerPerformancePlayerPKs(Integer matchId) {
		return repository.findPlayerPerformancePlayerPKs(matchId);
	}
    
    public PlayerPerformance findPlayerPerformance(Integer matchId, Integer playerId){
    	return repository.findPlayerPerformance(matchId, playerId);
    }
    
	public List<IPerformanceRecord> findPerformanceRecords(Integer matchId, String entityName){
    	return repository.findPerformanceRecords(matchId, entityName);
    }
	
	public List<PlayerCareerRecord> findPlayerCareerRecords(Integer playerId){
		return repository.findPlayerCareerRecords(playerId);
	}
	
	@SuppressWarnings("rawtypes")
	public Boolean checkAndRemoveLatestPerformance(Class clazz, Integer matchId, Integer playerId){
		return repository.checkAndRemoveLatestPerformance(clazz, matchId, playerId);
	}

}
