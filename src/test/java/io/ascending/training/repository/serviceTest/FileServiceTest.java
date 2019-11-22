package io.ascending.training.repository.serviceTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class FileServiceTest {
    @Autowired
    private FileService fileService;

    @Test
    public void putObjectTest(){
        File test = new File("/home/runlai/Downloads/algorithm.pdf");
        fileService.putObject(test);
    }
}
