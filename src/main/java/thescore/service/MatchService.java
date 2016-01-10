package thescore.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.model.Match;
import thescore.model.MatchActivePlayer;
import thescore.model.MatchCommittee;
import thescore.model.MatchStartingPlayer;
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
    
    public void saveMatchActivePlayer(MatchActivePlayer matchActivePlayer) {
        matchRepository.persist(matchActivePlayer);
    }
    
    public void deleteMatchActivePlayer(Integer matchId, Integer playerId) {
        matchRepository.deleteMatchActivePlayer(matchId, playerId);
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
		
		if(committeePKs != null){
			for(String pk : committeePKs){
				newCommitteePKs.add(Integer.valueOf(pk));
			}
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
		List<MatchStartingPlayer> matchPlayers = findAllMatchPlayers(match.getId());
    	
		List<Integer> newPlayerPKs = new ArrayList<Integer>();
		List<Integer> existingPlayerPKs = new ArrayList<Integer>();
		
		if(teamAPlayerPKs != null){
			for(String teamAPlayerPK : teamAPlayerPKs){
				newPlayerPKs.add(Integer.valueOf(teamAPlayerPK));
			}
		}
		
		if(teamBPlayerPKs != null){
			for(String teamBPlayerPK : teamBPlayerPKs){
				newPlayerPKs.add(Integer.valueOf(teamBPlayerPK));
			}
		}
		
		if(matchPlayers != null){
			for(MatchStartingPlayer matchPlayer : matchPlayers){
				if(newPlayerPKs.contains(matchPlayer.getPlayer().getId()) == false){
					matchRepository.deleteRecordById(MatchStartingPlayer.ENTITY_NAME, matchPlayer.getId());
				}
				
				existingPlayerPKs.add(matchPlayer.getPlayer().getId());
			}
		}
		
		for(Integer newPlayerPK : newPlayerPKs){
			if(existingPlayerPKs.contains(newPlayerPK) == false){
				MatchStartingPlayer matchPlayer = new MatchStartingPlayer();
				matchPlayer.setMatch(match);
				matchPlayer.setPlayer(playerRepository.findById(newPlayerPK));
				matchRepository.persist(matchPlayer);
			}
		}
	}
    
    public List<MatchCommittee> findAllMatchCommittees(Integer matchId) {
		return matchRepository.findAllMatchCommittees(matchId);
	}
    
    public List<MatchStartingPlayer> findAllMatchPlayers(Integer matchId) {
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
    
    public List<Match> findAllMatches(Criterion... criterions) {
        return matchRepository.findAllMatches(criterions);
    }
    
    public List<Player> findActivePlayers(Integer matchId) {
		return matchRepository.findActivePlayers(matchId);
	}
    
    public List<Match> findCommitteeIncomingMatches(Integer committeeId){
    	return matchRepository.findCommitteeIncomingMatches(committeeId);
    }
    
    public List<Integer> findPlayableMatches() {
    	return matchRepository.findPlayableMatches();
    }
 
}
