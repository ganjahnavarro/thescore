package thescore.configuration;

import java.util.List;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;

import thescore.ControllerInterceptor;
import thescore.controller.core.CoreArgumentResolver;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "thescore")
public class AppConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(coreArgumentResolver());
		argumentResolvers.add(authenticationPrincipalArgumentResolver());
	}
	
	@Bean
	public ViewResolver resourceBundleViewResolver() {
		ResourceBundleViewResolver viewResolver = new ResourceBundleViewResolver();
		viewResolver.setBasename("views");
		viewResolver.setOrder(1);
		return viewResolver;
	}

	@Bean
	public ViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setOrder(2);
		return viewResolver;
	}
	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    
	    long twentyMB = 20971520;
	    int oneMB = 1048576;
	    
	    multipartResolver.setMaxUploadSize(twentyMB);
	    multipartResolver.setMaxInMemorySize(oneMB);
	    return multipartResolver;
	}
	
	@Bean
	public JavaMailSenderImpl mailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setUsername("1applicationbot@gmail.com");
		mailSender.setPassword("@pplicationbot");
		
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.setProperty("mail.smtp.auth", "true");
		properties.setProperty("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.debug", "true");
		mailSender.setJavaMailProperties(properties);
		return mailSender;
	}
	
	@Bean
	public CoreArgumentResolver coreArgumentResolver(){
		return new CoreArgumentResolver();
	}
	
	@Bean
	public AuthenticationPrincipalArgumentResolver authenticationPrincipalArgumentResolver(){
		return new AuthenticationPrincipalArgumentResolver();
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new ControllerInterceptor());
		super.addInterceptors(registry);
	}
	
}
