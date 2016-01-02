package thescore.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import thescore.model.Team;

@Repository
public class TeamRepository extends AbstractRepository<Integer, Team> {

	public Team findById(int id) {
		return getByKey(id);
	}

	public void saveTeam(Team team) {
		persist(team);
	}

	public void deleteRecordById(Integer id) {
		deleteRecordById(Team.ENTITY_NAME, id);
	}

	@SuppressWarnings("unchecked")
	public List<Team> findAllTeams() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("name"));
		return (List<Team>) criteria.list();
	}

}
