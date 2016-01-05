package thescore.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.enums.UserType;
import thescore.model.Match;
import thescore.model.Newsfeed;
import thescore.model.User;
import thescore.repository.MatchRepository;
import thescore.repository.NewsfeedRepository;
import thescore.repository.UserRepository;

@Service
@Transactional
public class NewsfeedService {
	
	private @Autowired MailSender mailSender;
	
	private @Autowired UserRepository userRepository;
	private @Autowired NewsfeedRepository newsfeedRepository;
	private @Autowired MatchRepository matchRepository;
	
	public Newsfeed findById(int id) {
        return newsfeedRepository.findById(id);
    }
 
    public void updateNewsfeed(Newsfeed source) {
    	Newsfeed destination = newsfeedRepository.findById(source.getId());
        
		if (destination != null) {
			try {
				PropertyUtils.copyProperties(destination, source);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
        }
    }
	
	public void saveNewsfeed(Newsfeed newsfeed) {
		newsfeedRepository.saveNewsfeed(newsfeed);
	}

	public List<Newsfeed> findNewsfeeds() {
		return newsfeedRepository.findNewsfeeds();
	}
	
	public void onMatchEnd(Match match){
		Match destination = matchRepository.findById(match.getId());

		if (destination != null) {
			try {
				PropertyUtils.copyProperties(destination, match);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		
		newsfeedRepository.updatePlayerPerformanceFinalScores(destination);
		destination = matchRepository.updateWinner(destination);
		createNewsfeeds(destination);
		sendEmailToSubscribeUsers(destination);
	}
	
	private void sendEmailToSubscribeUsers(Match match){
		for(User user : userRepository.findAllUsers(UserType.DEFAULT)){
			if(user.getEmail() != null && !user.getEmail().isEmpty()){
				try {
					SimpleMailMessage mailMessage = new SimpleMailMessage();
					mailMessage.setFrom("1applicationbot@gmail.com");
					mailMessage.setTo(user.getEmail());
					mailMessage.setSubject("Match Results");
					
					String loserTeamName = match.getWinner().equals(match.getTeamA()) ?
							match.getTeamA().getDisplayString() : match.getTeamB().getDisplayString();
					
					String messageBody = match.getWinner().getDisplayString() + " wins against "
							+ loserTeamName + " on " + match.getLeague().getDisplayString();
					
					mailMessage.setText(messageBody);
					mailSender.send(mailMessage);
				} catch (Exception e){
					System.out.println("Error sending email to: " + user.getEmail());
				}
			}
		}
	}
	
	public void createNewsfeeds(Match match){
		createWinnerNewsfeed(match);
	}

	private void createWinnerNewsfeed(Match match) {
		Newsfeed newsfeed = new Newsfeed();
		newsfeed.setDate(new Date());
		
		String loserTeamName = match.getWinner().equals(match.getTeamA()) ?
				match.getTeamA().getDisplayString() : match.getTeamB().getDisplayString();
		
		newsfeed.setDescription(match.getWinner().getDisplayString() + " wins against "
				+ loserTeamName + " on " + match.getLeague().getDisplayString());
		
		saveNewsfeed(newsfeed);
	}
	
	public void deleteNewsfeedById(Integer id) {
		newsfeedRepository.deleteRecordById(id);
	}

}
