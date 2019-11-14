package io.ascending.training.init;


import io.ascending.training.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication(scanBasePackages = {"io.ascending.training"})
@ServletComponentScan(basePackages = {"io.ascending.training.filter"})
public class ApplicationBoot {
    public static void main(String[] args){
        SpringApplication.run(ApplicationBoot.class, args);
    }

    @Bean
    public SessionFactory getFactory() throws Exception{
        SessionFactory sf = HibernateUtil.getSessionFactory();
        if(sf==null) throw new Exception("building session factory exception");
        return sf;
    }
}
