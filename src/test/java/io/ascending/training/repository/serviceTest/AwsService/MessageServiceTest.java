package io.ascending.training.repository.serviceTest.AwsService;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.TestCase.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class MessageServiceTest {
    @Autowired
    private MessageService messageService;

    @Autowired
    private AmazonSQS amazonSQS;

    @Test
    public void sendEventTest() {

        SendMessageRequest sendMessageRequest = mock(SendMessageRequest.class);
        messageService.sendEvent("test");
        verify(amazonSQS,times(1)).sendMessage(any(SendMessageRequest.class));
    }

    @Test
    public void getqueueUrl() throws MalformedURLException {
        System.out.printf(messageService.queueUrl("test"));
    }



}
