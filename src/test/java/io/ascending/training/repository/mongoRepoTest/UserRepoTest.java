package io.ascending.training.repository.mongoRepoTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.mongoModel.MongoMessage;
import io.ascending.training.model.mongoModel.MongoUser;
import io.ascending.training.repository.mongo.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class UserRepoTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Logger logger;
    private MongoUser user;

    @Before
    public void init() {
        user = new MongoUser("userTest", 20);
    }

    @Test
    public void crudTest() {
        userRepository.insert(user);
        logger.info("Insert: " + user);
        user.setAge(22);
        user.setMessage(new MongoMessage("hello"));
        userRepository.save(user); //update
        logger.info("Update: " + user);
        userRepository.save(new MongoUser("userNew", 25));
        MongoUser newUser = userRepository.findByName("userNew").get();
        logger.info("FindByName('UserNew'):  " + newUser);
        logger.info("FindAll: "+userRepository.findAll());
        Assert.assertTrue(userRepository.deleteByName("userTest")==1?true:false);
        logger.info("After deleting userTest: " + userRepository.findAll());

    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }




}
