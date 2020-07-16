package io.ascending.training.repository.serviceTest.mongo;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.mongo.model.MongoMessage;
import io.ascending.training.mongo.model.MongoUser;
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

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }


}
