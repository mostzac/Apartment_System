package io.ascending.training.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import jdk.jfr.Name;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
public class AWSConfigMock {


    @Bean
    @Primary //test bean will scan both main and test
    public AmazonS3 getAmazonS3Mock() throws MalformedURLException {
        AmazonS3 amazonS3 = mock(AmazonS3.class);
//        URL result = new URL("http://testS3URL.com");
//        URL result2 = new URL("http://testS3PresignedURL.com");
//        GeneratePresignedUrlRequest g = new GeneratePresignedUrlRequest(anyString(),anyString());
//        when(amazonS3.getUrl(anyString(),anyString())).thenReturn(result);
//        when(amazonS3.generatePresignedUrl(g)).thenReturn(result2);
        return amazonS3;
    }

//    @Bean(name="AmazonS3Test")
//    public AmazonS3 getAmazonS3(){
//        return mock(AmazonS3.class);
//    }

    @Bean
    @Primary //test bean will scan both main and test
    public AmazonSQS getAmazonSQSMock(){
        //
        AmazonSQS amazonSQS = mock(AmazonSQS.class);
        GetQueueUrlResult result = new GetQueueUrlResult();
        result.setQueueUrl("http://testSQSURL.com");
        when(amazonSQS.getQueueUrl(anyString())).thenReturn(result);
        return amazonSQS;
    }


}
