package net.tsystems.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan({"net.tsystems.springframe", "net.tsystems"})
public class WebMvcConfigure {
    @Bean
    public ViewResolver getViewResolver() {
        InternalResourceViewResolver resolver
                = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        resolver.setViewClass(JstlView.class);
        resolver.setSuffix(".xhtml");
        return resolver;
    }
}
