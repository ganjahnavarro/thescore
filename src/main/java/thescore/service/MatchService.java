package thescore.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.model.MatchCommittee;
import thescore.model.Match;
import thescore.model.MatchPlayer;
import thescore.model.Player;
import thescore.repository.MatchRepository;
import thescore.repository.PlayerRepository;
import thescore.repository.UserRepository;

@Service
@Transactional
public class MatchService {

	private @Autowired MatchRepository matchRepository;
	private @Autowired UserRepository userRepository;
	private @Autowired PlayerRepository playerRepository;
     
    public Match findById(int id) {
        return matchRepository.findById(id);
    }
 
    public void saveMatch(Match match) {
        matchRepository.saveMatch(match);
    }
    
	public void saveMatch(Match match, String[] committeePKs, String[] teamAPlayerPKs, String[] teamBPlayerPKs) {
		saveMatch(match);
		updateMatchCommittees(match, committeePKs);
		updateMatchPlayers(match, teamAPlayerPKs, teamBPlayerPKs);
	}
	
	public List<Match> findMatchesByTeamId(Integer teamId){
		return matchRepository.findMatchesByTeamId(teamId);
	}
 
    public void updateMatch(Match source) {
        Match destination = matchRepository.findById(source.getId());
        
		if (destination != null) {
			try {
				PropertyUtils.copyProperties(destination, source);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
        }
    }
    
	public void updateMatch(Match match, String[] committeePKs, String[] teamAPlayerPKs, String[] teamBPlayerPKs) {
		updateMatch(match);
		updateMatchCommittees(match, committeePKs);
		updateMatchPlayers(match, teamAPlayerPKs, teamBPlayerPKs);
	}
    
    private void updateMatchCommittees(Match match, String[] committeePKs) {
		List<MatchCommittee> matchCommittees = findAllMatchCommittees(match.getId());
    	
		List<Integer> newCommitteePKs = new ArrayList<Integer>();
		List<Integer> existingCommitteePKs = new ArrayList<Integer>();
		
		for(String pk : committeePKs){
			newCommitteePKs.add(Integer.valueOf(pk));
		}
		
		if(matchCommittees != null){
			for(MatchCommittee matchCommittee : matchCommittees){
				if(newCommitteePKs.contains(matchCommittee.getCommittee().getId()) == false){
					matchRepository.deleteRecordById(MatchCommittee.ENTITY_NAME, matchCommittee.getId());
				}
				
				existingCommitteePKs.add(matchCommittee.getCommittee().getId());
			}
		}
		
		for(Integer newCommitteePK : newCommitteePKs){
			if(existingCommitteePKs.contains(newCommitteePK) == false){
				MatchCommittee matchCommittee = new MatchCommittee();
				matchCommittee.setMatch(match);
				matchCommittee.setCommittee(userRepository.findById(newCommitteePK));
				matchRepository.persist(matchCommittee);
			}
		}
	}
    
    private void updateMatchPlayers(Match match, String[] teamAPlayerPKs, String[] teamBPlayerPKs) {
		List<MatchPlayer> matchPlayers = findAllMatchPlayers(match.getId());
    	
		List<Integer> newPlayerPKs = new ArrayList<Integer>();
		List<Integer> existingPlayerPKs = new ArrayList<Integer>();
		
		for(String teamAPlayerPK : teamAPlayerPKs){
			newPlayerPKs.add(Integer.valueOf(teamAPlayerPK));
		}
		
		for(String teamBPlayerPK : teamBPlayerPKs){
			newPlayerPKs.add(Integer.valueOf(teamBPlayerPK));
		}
		
		if(matchPlayers != null){
			for(MatchPlayer matchPlayer : matchPlayers){
				if(newPlayerPKs.contains(matchPlayer.getPlayer().getId()) == false){
					matchRepository.deleteRecordById(MatchPlayer.ENTITY_NAME, matchPlayer.getId());
				}
				
				existingPlayerPKs.add(matchPlayer.getPlayer().getId());
			}
		}
		
		for(Integer newPlayerPK : newPlayerPKs){
			if(existingPlayerPKs.contains(newPlayerPK) == false){
				MatchPlayer matchPlayer = new MatchPlayer();
				matchPlayer.setMatch(match);
				matchPlayer.setPlayer(playerRepository.findById(newPlayerPK));
				matchRepository.persist(matchPlayer);
			}
		}
	}
    
    public List<MatchCommittee> findAllMatchCommittees(Integer matchId) {
		return matchRepository.findAllMatchCommittees(matchId);
	}
    
    public List<MatchPlayer> findAllMatchPlayers(Integer matchId) {
		return matchRepository.findAllMatchPlayers(matchId);
	}
    
    public List<Player> findStartingPlayers(Integer matchId) {
		return matchRepository.findStartingPlayers(matchId);
	}
 
    public void deleteMatchById(Integer id) {
        matchRepository.deleteRecordById(id);
    }
     
    public List<Match> findAllMatches() {
        return matchRepository.findAllMatches();
    }
 
}
