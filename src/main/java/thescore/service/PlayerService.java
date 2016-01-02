package thescore.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.model.Player;
import thescore.repository.PlayerRepository;

@Service
@Transactional
public class PlayerService {

    private @Autowired PlayerRepository repository;
     
    public Player findById(int id) {
        return repository.findById(id);
    }
 
    public void savePlayer(Player player) {
        repository.savePlayer(player);
    }
 
    public void updatePlayer(Player source) {
        Player destination = repository.findById(source.getId());
        
		if (destination != null) {
			try {
				PropertyUtils.copyProperties(destination, source);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
        }
    }
 
    public void deletePlayerById(Integer id) {
        repository.deleteRecordById(id);
    }
     
    public List<Player> findAllPlayers() {
        return repository.findAllPlayers();
    }
    
    public List<Player> findPlayersByTeamId(Integer teamId) {
        return repository.findPlayersByTeamId(teamId);
    }
 
}
