package thescore.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.classes.TeamWinLoseRecord;
import thescore.model.Team;
import thescore.repository.TeamRepository;

@Service
@Transactional
public class TeamService {

	private @Autowired TeamRepository repository;

	public Team findById(int id) {
		return repository.findById(id);
	}

	public void saveTeam(Team team) {
		repository.saveTeam(team);
	}

	public void updateTeam(Team source) {
		Team destination = repository.findById(source.getId());

		if (destination != null) {
			try {
				PropertyUtils.copyProperties(destination, source);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}

	public void deleteTeamById(Integer id) {
		repository.deleteRecordById(id);
	}

	public List<Team> findAllTeams() {
		return repository.findAllTeams();
	}
	
	public List<Team> findAllValidTeams() {
		return repository.findAllValidTeams();
	}
	
	public List<TeamWinLoseRecord> findTeamLoseRecords(Integer teamId){
		return repository.findTeamLoseRecords(teamId);
	}
	
	public List<TeamWinLoseRecord> findTeamLoseRecordsByLeague(Integer leagueId){
		return repository.findTeamLoseRecordsByLeague(leagueId);
	}

}
