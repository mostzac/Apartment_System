package io.ascending.training.init;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.ascending.training.util.HibernateUtil;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

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

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Logger logger(InjectionPoint injectionPoint){
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }

    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module();
    }


}
