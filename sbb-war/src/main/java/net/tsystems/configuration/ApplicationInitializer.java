package net.tsystems.configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer {

    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext annotationConfigWebApplicationContext = new AnnotationConfigWebApplicationContext();

        annotationConfigWebApplicationContext.setServletContext(servletContext);
        annotationConfigWebApplicationContext.scan("net.tsystems.configuration");

        servletContext.addListener(new ContextLoaderListener(annotationConfigWebApplicationContext));
        servletContext.addListener(new RequestContextListener());

        ServletRegistration.Dynamic dynamic = servletContext.addServlet("dispatcher", new DispatcherServlet(annotationConfigWebApplicationContext));
        dynamic.addMapping("/");
        dynamic.setLoadOnStartup(NumberUtils.INTEGER_ONE);
    }
}
