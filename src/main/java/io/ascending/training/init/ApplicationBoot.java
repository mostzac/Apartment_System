package io.ascending.training.init;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"io.ascending.training"})
public class ApplicationBoot {
    public static void main(String[] args){
        SpringApplication.run(ApplicationBoot.class, args);
    }
}
