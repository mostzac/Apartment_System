package io.ascending.training.repository.serviceTest.AwsService;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.ArgumentMatchers.shortThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class FileServiceTest {
    @Autowired
    private FileService fileService;
    @Autowired
//    @Qualifier("AmazonS3Test")
    private AmazonS3 amazonS3;

//    @Test
//    public void putObjectTest(){ //not using mock, test it with Qualifier() that autowired to the main bean AmazonS3.
//        File test = new File("xx.");
//        fileService.putObject(test);
//        verify(amazonS3,times(1)).putObject("ascending-apartment-system",test.getName(),test);
//    }

    @Test
    public void putObjectTest1() throws MalformedURLException { //mock test
        MultipartFile mf = mock(MultipartFile.class);
        when(mf.getOriginalFilename()).thenReturn("test.txt");
//        when(fileService.putObject(null)).thenReturn()
        URL url = new URL("http://testS3URL.com");
        when(amazonS3.getUrl(anyString(),anyString())).thenReturn(url);
//        when(amazonS3.getUrl(anyString(),anyString()).toString()).thenReturn(url.toString());   // nullPointerException

        System.out.printf(fileService.putObject(mf).toString());
    }

    @Test
    public void getObjectURL() throws MalformedURLException {
        URL result2 = new URL("http://testS3PresignedURL.com");
        when(amazonS3.generatePresignedUrl(any(GeneratePresignedUrlRequest.class))).thenReturn(result2);
        System.out.printf(fileService.getObjectURL("key"));
    }
}
