package io.ascending.training.repository.serviceTest.AwsService;

import com.amazonaws.services.sqs.AmazonSQS;
import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class MessageServiceTest {
    @Autowired
    private MessageService messageService;

    @Autowired
    private AmazonSQS amazonSQS;

    @Test
    public void sendEventTest(){
        messageService.sendEvent("test");
        assertTrue(false);
    }
}
