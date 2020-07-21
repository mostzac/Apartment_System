package io.ascending.training.repository.serviceTest.mongo;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.mongo.model.MongoMessage;
import io.ascending.training.mongo.model.MongoUser;
import io.ascending.training.mongo.repository.MessageRepository;
import io.ascending.training.mongo.repository.UserRepository;
import io.ascending.training.mongo.service.UserService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
@ContextConfiguration
public class UserServiceTest {
    @Autowired
    @Qualifier("mongoService")
    private UserService userService;
    @Autowired
    private Logger logger;
    private MongoUser user;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;

    @Before
    public void init() {
        user = new MongoUser("userTest", 20);
    }

    @Test
    public void crudTest() {
        logger.info("Insert: " + userService.insert(user));
        MongoUser user1 = userService.findById(user.getId());
        user1.setAge(22);
        user1.setMessage(new MongoMessage("hello"));
        //update
        logger.info("Update: " + userService.save(user1));
        userService.save(new MongoUser("userNew", 25));
        MongoUser newUser = userService.findByName("userNew");
        logger.info("FindByName('UserNew'):  " + newUser);
        logger.info("FindAll: "+ userService.findAll());
        Assert.assertTrue(userService.deleteByName("userTest"));
        Assert.assertFalse(userService.existById(user.getId()));
        logger.info("After deleting userTest: " + userService.findAll());
    }

    @Test
    public void insertSaveWithMessage() {
        userService.insert(user);
        user.setMessage(new MongoMessage("hello"));
        Assert.assertEquals(user.getMessage().getContent(),userService.save(user).getMessage().getContent());
        MongoUser newUser = new MongoUser("newUser", 10);
        newUser.setMessage(new MongoMessage("hello"));
        Assert.assertTrue(user.getMessage().getId().equals(userService.insert(newUser).getMessage().getId()));
        newUser.setMessage(new MongoMessage("new Message"));
        userService.save(newUser);
        Assert.assertFalse(user.getMessage().getId().equals(newUser.getMessage().getId()));
        newUser.setAge(25);
        logger.info("updated: "+userService.update(newUser));
        Assert.assertTrue(newUser.getMessage().getId().equals(userService.update(newUser).getMessage().getId()));

    }

    @Test
    public void cascadeDeleteTest() {
        MongoUser user = new MongoUser("userTest", 20);
        user.setMessage(new MongoMessage("original"));
        userService.insert(user);
        logger.info("Insert: " + user);
        user.setAge(22);
        user.setMessage(new MongoMessage("hello"));
        userService.save(user); //update
        logger.info("Save: " + user);
        MongoUser user1 = new MongoUser("userNew", 20);
        user1.setMessage(new MongoMessage("hello"));
        userService.insert(user1);
        logger.info("");
        messageRepository.delete(user1.getMessage());
        logger.info("");
        Assert.assertNull(userService.findByName("userNew").getMessage());

    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
        messageRepository.deleteAll();
    }


}
