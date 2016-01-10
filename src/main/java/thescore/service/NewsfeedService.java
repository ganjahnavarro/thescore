package thescore.service;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.model.Match;
import thescore.model.Newsfeed;
import thescore.model.Notification;
import thescore.repository.MatchRepository;
import thescore.repository.NewsfeedRepository;
import thescore.repository.NotificationRepository;

@Service
@Transactional
public class NewsfeedService {
	
	private @Autowired NewsfeedRepository newsfeedRepository;
	private @Autowired MatchRepository matchRepository;
	private @Autowired NotificationRepository notificationRepository;
	
	public Newsfeed findById(int id) {
        return newsfeedRepository.findById(id);
    }
 
    public void updateNewsfeed(Newsfeed source, Boolean updateImage) {
    	Newsfeed destination = newsfeedRepository.findById(source.getId());
    	
    	if(!updateImage){
        	source.setImage(destination.getImage());
        	source.setImageFileName(destination.getImageFileName());
        }
        
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
	
	public void onMatchEnd(Match match, Integer defWinTeamId){
		Match destination = matchRepository.findById(match.getId());

		if (destination != null) {
			try {
				PropertyUtils.copyProperties(destination, match);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		
		newsfeedRepository.updatePlayerPerformanceFinalScores(destination);
		destination.setActualEnd(new Date());
		
		if(defWinTeamId == null){
			destination = matchRepository.updateWinner(destination);
		} else {
			destination.setWinner(destination.getTeamA().getId().equals(defWinTeamId) ? destination.getTeamA() : destination.getTeamB());
			destination.setDefaultWin(true);
		}
		
		createNotification(destination);
	}
	
	private void createNotification(Match match) {
		Notification notification = new Notification();
		notification.setDate(new Date());
		notification.setUrl("/match/view-" + match.getId() + "-match");
		
		String loserTeamName = match.getWinner().equals(match.getTeamA()) ?
				match.getTeamA().getDisplayString() : match.getTeamB().getDisplayString();
		
		notification.setMessage(match.getWinner().getDisplayString() + " wins against "
				+ loserTeamName + (match.getDefaultWin() ? " by default" : "")
				+ " on " + match.getLeague().getDisplayString());
		
		notificationRepository.saveNotification(notification);
	}
	
	public void deleteNewsfeedById(Integer id) {
		newsfeedRepository.deleteRecordById(id);
	}

}
