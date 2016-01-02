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

import thescore.model.Match;
import thescore.model.Newsfeed;
import thescore.repository.MatchRepository;
import thescore.repository.NewsfeedRepository;

@Service
@Transactional
public class NewsfeedService {
	
	private @Autowired MailSender mailSender;
	
	private @Autowired NewsfeedRepository newsfeedRepository;
	private @Autowired MatchRepository matchRepository;
//	private @Autowired PlayerPerformanceRepository playerPerformanceRepository;
	
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
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("ganjaboi.navarro@gmail.com");
		mailMessage.setTo("ganjaboi.navarro@gmail.com");
		mailMessage.setSubject("Match Results");
		
		String loserTeamName = match.getWinner().equals(match.getTeamA()) ?
				match.getTeamA().getDisplayString() : match.getTeamB().getDisplayString();
		
		String messageBody = match.getWinner().getDisplayString() + " wins against "
				+ loserTeamName + " on " + match.getLeague().getDisplayString();
		
		mailMessage.setText(messageBody);
		mailSender.send(mailMessage);
	}
	
	public void createNewsfeeds(Match match){
		createWinnerNewsfeed(match);
//		TODO
//		createHighestScorerNewsfeed(match);
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
	
//	private void createHighestScorerNewsfeed(Match match) {
//		Newsfeed newsfeed = new Newsfeed();
//		newsfeed.setDate(new Date());
//		
//		List<PlayerPerformance> performances = playerPerformanceRepository.findHighestScorePerformances(match.getId());
//		
//		String player = "";
//		Integer score = 0;
//		
//		for(PlayerPerformance performance : performances){
//			player += performance.getPlayer().getDisplayString() + ". ";
//			score = performance.getFinalScore();
//		}
//		
//		if(player.isEmpty() == false && score > 0){
//			newsfeed.setDescription(player + " scores " + score + " points on " + match.getDisplayString() + " match up.");
//			saveNewsfeed(newsfeed);
//		}
//	}
	
	public void deleteNewsfeedById(Integer id) {
		newsfeedRepository.deleteRecordById(id);
	}

}
