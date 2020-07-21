package io.ascending.training.repository.mongoRepoTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.mongo.model.MongoMessage;
import io.ascending.training.mongo.model.MongoUser;
import io.ascending.training.mongo.repository.MessageRepository;
import io.ascending.training.mongo.repository.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class MessageRepoTest {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Logger logger;

    private MongoMessage message;

    @Before
    public void init() {
        message = new MongoMessage("this is message");
    }

    @Test
    public void crudTest() {
        messageRepository.insert(message);
        messageRepository.save(messageRepository.findByContent(message.getContent()).get());
        message.setTags(new String[]{"tag1","tag2"});
        messageRepository.save(message);
        String[] res = messageRepository.findByContent(message.getContent()).get().getTags();
        Assert.assertTrue(Arrays.stream(message.getTags()).collect(Collectors.joining()).equals(Arrays.stream(res).collect(Collectors.joining())) );
    }

    @Test
    public void findByContentRegexTest() {
        List<MongoMessage> list = new ArrayList<>();
        list.add(new MongoMessage("test"));
        list.add(new MongoMessage("aaaaa   test"));
        list.add(new MongoMessage("aaa"));
        messageRepository.insert(list);
        list = messageRepository.findAllByContentRegex("test");
        logger.info("");
    }

    @After
    public void tearDown() {
        messageRepository.deleteAll();
    }

}
