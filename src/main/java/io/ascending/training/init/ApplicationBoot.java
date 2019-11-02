package io.ascending.training.init;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication(scanBasePackages = {"io.ascending.training"})
@ServletComponentScan(basePackages = {"io.ascending.training.filter"})
public class ApplicationBoot {
    public static void main(String[] args){
        SpringApplication.run(ApplicationBoot.class, args);
    }
}
