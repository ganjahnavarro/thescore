package thescore.configuration;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.atmosphere.cpr.MeteorServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		super.onStartup(servletContext);
		registerMeteorServlet(servletContext);
	}

	private void registerMeteorServlet(ServletContext servletContext) {
		Dynamic meteorServletRegistration = servletContext.addServlet("MeteorServlet", new MeteorServlet());
		meteorServletRegistration.setLoadOnStartup(1);
		meteorServletRegistration.addMapping("/core/atmosphere/*");
		meteorServletRegistration.setAsyncSupported(true);
		
		Map<String,String> params = new HashMap<String,String>();
        params.put("org.atmosphere.cpr.packages", "thescore");
        params.put("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
        params.put("contextConfigLocation", "thescore.configuration.AppConfig");
		meteorServletRegistration.setInitParameters(params);
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] { AppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}
	
}
