package thescore.repository;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import thescore.enums.UserType;
import thescore.model.User;

@Repository
public class UserRepository extends AbstractRepository<Integer, User> {

	public User findById(int id) {
		return getByKey(id);
	}
	
	public User findByUserName(String userName) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("userName", userName));
		return (User) criteria.uniqueResult();
	}

	public void saveUser(User user) {
		persist(user);
	}

	public void deleteRecordById(Integer id) {
		deleteRecordById(User.ENTITY_NAME, id);
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("lastName"));
		return (List<User>) criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> findAllUsers(UserType... types) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.in("type", types));
		criteria.addOrder(Order.asc("lastName"));
		return (List<User>) criteria.list();
	}
	
}
