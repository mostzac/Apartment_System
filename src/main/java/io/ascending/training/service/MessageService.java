package io.ascending.training.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.GetQueueUrlResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private AmazonSQS sqsClient;

    private String queueName;
    private String queueUrl;

    // variable initialization is after constructor, so if we add beans for the variables outside the constructor, the bean won't be initialized.
    // So we put them in the parameter field.
    public MessageService(@Autowired AmazonSQS amazonSQS, @Value("${aws.sqs.name}") String queueName) {
        this.sqsClient = amazonSQS;
        this.queueName = queueName;
        queueUrl = queueUrl(queueName);

    }

    public void sendEvent(String message) {
        SendMessageRequest sendMsgRequest = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(message)
                .withDelaySeconds(5);
        sqsClient.sendMessage(sendMsgRequest);
    }

    public String queueUrl(String queueName) {
        GetQueueUrlResult result = sqsClient.getQueueUrl(queueName);
        return result.getQueueUrl();
    }
}
