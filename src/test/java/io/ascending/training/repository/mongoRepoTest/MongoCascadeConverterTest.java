package io.ascending.training.repository.mongoRepoTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.mongo.model.MongoMessage;
import io.ascending.training.mongo.model.MongoUser;
import io.ascending.training.mongo.repository.MessageRepository;
import io.ascending.training.mongo.repository.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class MongoCascadeConverterTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    Logger logger;
    @Autowired
    private MongoOperations ops;

    private MongoUser user;

    @Before
    public void init() {
        user = new MongoUser("userTest", 20);
    }

    @Test
    public void userCascadeSaveTest() {
        user.setMessage(new MongoMessage("new Message"));
        userRepository.insert(user);
        MongoUser newUser = new MongoUser("new User", 25);
        newUser.setMessage(new MongoMessage("new Message"));
        userRepository.insert(newUser);
        logger.info("User: "+userRepository.findAllByMessageContainsContent("new Message").stream().collect(Collectors.toList()));
        logger.info("Message: "+messageRepository.findByContent("new Message").get().getContent());
    }

    @Test
    public void converterTest() {
        user.setMessage(new MongoMessage("new Message"));
//        userRepository.insert(user);
        ops.insert(user);
        MongoUser newUser = new MongoUser("new User", 25);
        ops.insert(newUser);
        MongoUser newUser1 = new MongoUser("newUser2", 26);
        newUser1.setMessage(messageRepository.findByContent("new Message").get());
        ops.insert(newUser1);
        List<MongoUser> list = userRepository.findAll();
        newUser = userRepository.findByName("userTest").get();
        logger.info("Read: "+newUser);
        logger.info("Find: "+list);
        logger.info("Find message:"+userRepository.findAllByMessageContainsContent("new Message"));
    }




    @After
    public void tearDown() {
        userRepository.deleteAll();
        messageRepository.deleteAll();
    }
}
