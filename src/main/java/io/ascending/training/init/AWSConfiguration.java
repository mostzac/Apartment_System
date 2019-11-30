package io.ascending.training.init;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfiguration {
    @Bean(name = "AmazonS3")
    public AmazonS3 getAmazonS3(){
        return AmazonS3ClientBuilder.standard().withCredentials(new DefaultAWSCredentialsProviderChain()).build();
    }

}
