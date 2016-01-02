package thescore.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import thescore.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "thescore")
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	@Qualifier(value = CustomUserDetailsService.BEAN_NAME)
	private UserDetailsService service;
	
	@Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(service);
    }
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "/home").permitAll()
			.antMatchers("/user/**").access("hasRole('COMMITTEE')")
			.antMatchers("/newsfeed/**").access("hasRole('COMMITTEE')")
			.and().formLogin()
			.loginPage("/login")
			.usernameParameter("userName").passwordParameter("password")
			.and().exceptionHandling().accessDeniedPage("/accessdenied")
			.and().csrf();
	}
	
}
