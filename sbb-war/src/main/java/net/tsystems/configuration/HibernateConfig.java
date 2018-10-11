package net.tsystems.configuration;


import net.tsystems.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@org.springframework.context.annotation.Configuration
@EnableTransactionManagement
public class HibernateConfig {

    private static SessionFactory sessionFactory;

    static {
        Configuration configuration = new Configuration();

        configuration.configure("classpath:hibernate.cfg.xml");
        configuration.addAnnotatedClass(PassengerDO.class);
        configuration.addAnnotatedClass(TicketDO.class);
        configuration.addAnnotatedClass(TrainDO.class);
        configuration.addAnnotatedClass(StationDO.class);
        configuration.addAnnotatedClass(TripDO.class);
        configuration.addAnnotatedClass(TripDataDO.class);
        configuration.addAnnotatedClass(RouteDO.class);

        //configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        //configuration.setProperty(" hibernate.connection.pool_size", "10");

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /*@Autowired
    private ApplicationContext context;*/

    /*@Bean
    public LocalSessionFactoryBean getSessionFactory() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
        factoryBean.setConfigLocation(context.getResource("classpath:hibernate.cfg.xml"));
        factoryBean.setAnnotatedClasses(PassengerDO.class,
                                        TicketDO.class,
                                        TrainDO.class,
                                        StationDO.class,
                                        TripDO.class,
                                        TripDataDO.class,
                                        RouteDO.class);
        return factoryBean;
    }*/

    /*@Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactory().getObject());
        return transactionManager;
    }*/
}
