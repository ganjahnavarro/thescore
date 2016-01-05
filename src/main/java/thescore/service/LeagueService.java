package thescore.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.model.League;
import thescore.model.LeagueTeam;
import thescore.repository.LeagueRepository;
import thescore.repository.TeamRepository;

@Service
@Transactional
public class LeagueService {

    private @Autowired LeagueRepository repository;
    private @Autowired TeamRepository teamRepository;
     
    public League findById(int id) {
        return repository.findById(id);
    }
 
	public void saveLeague(League league) {
		repository.saveLeague(league);
	}
	
	public void saveLeague(League league, String[] teamPKs) {
		repository.saveLeague(league);
		updateLeagueTeams(league, teamPKs);
	}
    
    public void updateLeague(League source) {
        League destination = repository.findById(source.getId());
        
		if (destination != null) {
			try {
				PropertyUtils.copyProperties(destination, source);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
        }
    }
    
    public void updateLeague(League league, String[] teamPKs) {
    	updateLeague(league);
    	updateLeagueTeams(league, teamPKs);
	}

	private void updateLeagueTeams(League league, String[] teamPKs) {
		List<LeagueTeam> leagueTeams = findAllLeagueTeams(league.getId());
    	
		List<Integer> newTeamPKs = new ArrayList<Integer>();
		List<Integer> existingTeamPKs = new ArrayList<Integer>();
		
		if(teamPKs != null){
			for(String pk : teamPKs){
				newTeamPKs.add(Integer.valueOf(pk));
			}
		}
		
		if(leagueTeams != null){
			for(LeagueTeam leagueTeam : leagueTeams){
				if(newTeamPKs.contains(leagueTeam.getTeam().getId()) == false){
					repository.deleteRecordById(LeagueTeam.ENTITY_NAME, leagueTeam.getId());
				}
				
				existingTeamPKs.add(leagueTeam.getTeam().getId());
			}
		}
		
		for(Integer newTeamPK : newTeamPKs){
			if(existingTeamPKs.contains(newTeamPK) == false){
				LeagueTeam leagueTeam = new LeagueTeam();
				leagueTeam.setLeague(league);
				leagueTeam.setTeam(teamRepository.findById(newTeamPK));
				repository.persist(leagueTeam);
			}
		}
	}
 
    public void deleteLeagueById(Integer id) {
        repository.deleteRecordById(id);
    }
     
	public List<League> findAllLeagues() {
		return repository.findAllLeagues();
	}
	
	public List<League> findChampionships(Integer teamId) {
		return repository.findChampionships(teamId);
	}
	
	public List<LeagueTeam> findAllLeagueTeams(Integer leagueId) {
		return repository.findAllLeagueTeams(leagueId);
	}
 
}
