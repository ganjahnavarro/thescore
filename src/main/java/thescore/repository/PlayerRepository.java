package thescore.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import thescore.model.Player;

@Repository
public class PlayerRepository extends AbstractRepository<Integer, Player> {

	public Player findById(int id) {
		return getByKey(id);
	}

	public void savePlayer(Player player) {
		persist(player);
	}

	public void deleteRecordById(Integer id) {
		deleteRecordById(Player.ENTITY_NAME, id);
	}

	@SuppressWarnings("unchecked")
	public List<Player> findAllPlayers() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("lastName"));
		return (List<Player>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Player> findPlayersByTeamId(Integer teamId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("team.id", teamId));
		criteria.addOrder(Order.asc("lastName"));
		return (List<Player>) criteria.list();
	}

}
