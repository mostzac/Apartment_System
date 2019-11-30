package io.ascending.training.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.S3Object;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {
    private String bucket = "ascending-apartment-system";
    @Autowired
    private FileService fileService;

    @Autowired
//    @Qualifier("AmazonS3")
    private AmazonS3 s3Client;

    public String putObject(File file) {
        s3Client.putObject(bucket, file.getName(), file);
        return s3Client.getUrl(bucket,file.getName()).toString();
    }

    public String getObjectURL(String key) {
        return s3Client.generatePresignedUrl(new GeneratePresignedUrlRequest(bucket,key)).toString();
//        return s3Client.getUrl(bucket,key).toString();
    }

    public ResponseEntity putObject(MultipartFile file){
        //MultipartFile file is the selected file from Front-end, when the file is being transferred, the file is tear down as multipart,
        // when the transfer is ready, the server will create a File with /homedir/filename.extension in the server side, and the put it onto the s3
        String msg = String.format("The file name = %s,size = %d is uploaded.", file.getOriginalFilename(), file.getSize());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String homeDir = System.getProperty("catalina.base") !=null ? System.getProperty("catalina.base"):"/tmp/";
        String s3Key = FilenameUtils.getBaseName(file.getOriginalFilename()+"_"+ UUID.randomUUID()+"."+extension);


        File f = new File(homeDir+s3Key);
        try {
            file.transferTo(f);
            msg = msg.concat(" URL = "+ fileService.putObject(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(msg);
    }
}
