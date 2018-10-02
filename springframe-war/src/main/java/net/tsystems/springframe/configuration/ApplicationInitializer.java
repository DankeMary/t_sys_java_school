package net.tsystems.springframe.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author storgovt
 */
public class ApplicationInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext annotationConfigWebApplicationContext = new AnnotationConfigWebApplicationContext();

        annotationConfigWebApplicationContext.setServletContext(servletContext);
        annotationConfigWebApplicationContext.scan("net.tsystems.springframe.configuration");

        servletContext.addListener(new ContextLoaderListener(annotationConfigWebApplicationContext));
        servletContext.addListener(new RequestContextListener());

        ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcher", new DispatcherServlet(annotationConfigWebApplicationContext));
        dynamic.addMapping("/");
        dynamic.setLoadOnStartup(NumberUtils.INTEGER_ONE);
    }
}
