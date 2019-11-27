package io.ascending.training.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileService {
    private String bucket = "ascending-apartment-system";

    @Autowired
    private AmazonS3 s3Client;

    public void putObject(File file) {
        s3Client.putObject(bucket, file.getName(), file);

    }

    public String getObjectURL(String key) {
        return s3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(bucket,key)).toString();
//        return s3Client.getUrl(bucket,key).toString();
    }
}
