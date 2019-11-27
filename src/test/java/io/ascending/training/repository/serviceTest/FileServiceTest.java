package io.ascending.training.repository.serviceTest;

import com.amazonaws.services.s3.AmazonS3;
import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.mockito.ArgumentMatchers.shortThat;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class FileServiceTest {
    @Autowired
    private FileService fileService;
    @Autowired
    private AmazonS3 amazonS3;

    @Test
    public void putObjectTest(){
        File test = new File("xx.");
        fileService.putObject(test);
        verify(amazonS3,times(1)).putObject("ascending-apartment-system",test.getName(),test);
    }
}
