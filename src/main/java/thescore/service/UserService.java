package thescore.service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.enums.UserType;
import thescore.model.User;
import thescore.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	private @Autowired UserRepository repository;

	public User findById(int id) {
		return repository.findById(id);
	}
	
	public User findByUserName(String userName) {
		return repository.findByUserName(userName);
	}

	public void saveUser(User user) {
		repository.saveUser(user);
	}

	public void updateUser(User source, Boolean updateImage) {
		User destination = repository.findById(source.getId());

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

	public void deleteUserById(Integer id) {
		repository.deleteRecordById(id);
	}

	public List<User> findAllUsers() {
		return repository.findAllUsers();
	}
	
	public List<User> findAllUsers(UserType... types) {
		return repository.findAllUsers(types);
	}
	
}
