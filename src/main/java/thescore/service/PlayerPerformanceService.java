package thescore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.interfaces.IPerformanceRecord;
import thescore.interfaces.IRecord;
import thescore.model.PlayerPerformance;
import thescore.model.computation.PerformanceComputation;
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
	
	public List<PerformanceComputation> findLeaguePlayerPerformanceComputations(Integer leagueId){
		return repository.findLeaguePlayerPerformanceComputations(leagueId);
	}
	
	public List<PerformanceComputation> findOverallPerformanceComputations(Integer playerId){
		return repository.findOverallPerformanceComputations(playerId);
	}
	
	public List<PerformanceComputation> findPerLeaguePerformanceComputations(Integer playerId){
		return repository.findPerLeaguePerformanceComputations(playerId);
	}
	
	public List<PerformanceComputation> findPerMatchPerformanceComputations(Integer playerId){
		return repository.findPerMatchPerformanceComputations(playerId);
	}
	
	public List<PerformanceComputation> findTeamOverallPerformanceComputations(Integer teamId){
		return repository.findTeamOverallPerformanceComputations(teamId);
	}
	
	public List<PerformanceComputation> findTeamPerLeaguePerformanceComputations(Integer teamId){
		return repository.findTeamPerLeaguePerformanceComputations(teamId);
	}
	
	public List<PerformanceComputation> findTeamPerMatchPerformanceComputations(Integer teamId){
		return repository.findTeamPerMatchPerformanceComputations(teamId);
	}
	
	@SuppressWarnings("rawtypes")
	public Boolean checkAndRemoveLatestPerformance(Class clazz, Integer matchId, Integer playerId){
		return repository.checkAndRemoveLatestPerformance(clazz, matchId, playerId);
	}

}
