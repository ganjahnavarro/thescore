package thescore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import thescore.model.User;

@Service(value = CustomUserDetailsService.BEAN_NAME)
public class CustomUserDetailsService implements UserDetailsService {

	public static final String BEAN_NAME = "customUserDetailsService";
	
	private @Autowired UserService userService;
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userService.findByUserName(userName);
		
		if (user == null) {
			throw new UsernameNotFoundException("Username not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
			user.getActive(), true, true, true, getGrantedAuthorities(user));
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        for(String role : user.getType().getRoles()){
        	authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        
        return authorities;
    }

}
