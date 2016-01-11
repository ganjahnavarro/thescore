package thescore.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.enums.UserType;
import thescore.model.League;
import thescore.model.LeagueMythicalPlayer;
import thescore.model.LeagueTeam;
import thescore.model.Player;
import thescore.model.User;
import thescore.repository.LeagueRepository;
import thescore.repository.TeamRepository;
import thescore.repository.UserRepository;

@Service
@Transactional
public class LeagueService {
	
	private @Autowired MailSender mailSender;

    private @Autowired LeagueRepository leagueRepository;
    private @Autowired TeamRepository teamRepository;
    private @Autowired UserRepository userRepository;
     
    public League findById(int id) {
        return leagueRepository.findById(id);
    }
    
    public void saveMythicalPlayer(LeagueMythicalPlayer mythicalPlayer){
    	leagueRepository.persist(mythicalPlayer);
    }
 
	public void saveLeague(League league, String[] teamPKs) {
		leagueRepository.saveLeague(league);
		updateLeagueTeams(league, teamPKs);
		sendEmailToSubscribeUsers(league);
	}
	
	private void sendEmailToSubscribeUsers(League league){
	for(User user : userRepository.findAllUsers(UserType.DEFAULT)){
		if(user.getEmail() != null && !user.getEmail().isEmpty()){
			try {
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				mailMessage.setFrom("1applicationbot@gmail.com");
				mailMessage.setTo(user.getEmail());
				mailMessage.setSubject("New Basketball League");
				
				String messageBody = "New League has been created (" + league.getName() + ")"
						+ (league.getPrize() != null ? " with a prize of " + league.getPrize() : "")
						+ (league.getAddress() != null ? " at address " + league.getAddress() : "");
				
				mailMessage.setText(messageBody);
				mailSender.send(mailMessage);
			} catch (Exception e){
				System.out.println("Error sending email to: " + user.getEmail());
			}
		}
	}
}
    
    public void updateLeague(League source) {
        League destination = leagueRepository.findById(source.getId());
        
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
					leagueRepository.deleteRecordById(LeagueTeam.ENTITY_NAME, leagueTeam.getId());
				}
				
				existingTeamPKs.add(leagueTeam.getTeam().getId());
			}
		}
		
		for(Integer newTeamPK : newTeamPKs){
			if(existingTeamPKs.contains(newTeamPK) == false){
				LeagueTeam leagueTeam = new LeagueTeam();
				leagueTeam.setLeague(league);
				leagueTeam.setTeam(teamRepository.findById(newTeamPK));
				leagueRepository.persist(leagueTeam);
			}
		}
	}
 
    public void deleteLeagueById(Integer id) {
        leagueRepository.deleteRecordById(id);
    }
     
	public List<League> findAllLeagues() {
		return leagueRepository.findAllLeagues();
	}
	
	public List<League> findChampionships(Integer teamId) {
		return leagueRepository.findChampionships(teamId);
	}
	
	public List<LeagueTeam> findAllLeagueTeams(Integer leagueId) {
		return leagueRepository.findAllLeagueTeams(leagueId);
	}
	
	public List<Player> findMythicalFive(Integer leagueId) {
		return leagueRepository.findMythicalFive(leagueId);
	}
 
}
