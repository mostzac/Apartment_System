package io.ascending.training.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.mock;

@Configuration
public class AWSConfigMock {
    @Bean
    @Primary //test bean will scan both main and test
    public AmazonS3 getAmazonS3Mock(){
        return mock(AmazonS3.class);
    }

}
