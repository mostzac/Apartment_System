
package io.ascending.training.util;

import com.github.fluent.hibernate.cfg.scanner.EntityScanner;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class HibernateUtil {
    private static SessionFactory sessionFactory;
    private static Logger logger = LoggerFactory.getLogger(HibernateUtil.class);


    /* Define JVM options
    -Ddatabase.driver=org.postgresql.Driver
    -Ddatabase.dialect=org.hibernate.dialect.PostgreSQL9Dialect
    -Ddatabase.url=jdbc:postgresql://localhost:5432/training_db
    -Ddatabase.user=admin
    -Ddatabase.password=Training123!
    */
    public static SessionFactory getSessionFactory() {//factory design pattern
        if (sessionFactory == null) {// singleton design pattern
            try {
                String[] modelPackages = {"io.ascending.training.postgres.model"};
                String dbDriver = System.getProperty("database.driver");
                String dbDialect = System.getProperty("database.dialect");
                String dbUrl = System.getProperty("database.url");
                String dbUser = System.getProperty("database.user");
                String dbPassword = System.getProperty("database.password");

                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, dbDriver);
                settings.put(Environment.DIALECT, dbDialect);
                settings.put(Environment.URL, dbUrl);
                settings.put(Environment.USER, dbUser);
                settings.put(Environment.PASS, dbPassword);
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO,"validate");//
                configuration.setProperties(settings);

                //Mapping Class
                EntityScanner.scanPackages(modelPackages).addTo(configuration);// third-party config tool, EntityScanner
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
                ServiceRegistry serviceRegistry = registryBuilder.applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            } catch (Exception e) {
                logger.error("build factory exception",e);
//                logger.debug(e.getMessage());
            }
        }
        return sessionFactory;
    }
}
