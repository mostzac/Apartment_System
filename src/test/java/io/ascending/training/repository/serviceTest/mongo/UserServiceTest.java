package io.ascending.training.repository.serviceTest.mongo;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.mongoModel.MongoUser;
import io.ascending.training.repository.interfaces.mongo.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class UserServiceTest {
    @Autowired
    UserRepository userRepository;
    MongoUser user;

    @Before
    public void init() {
        user = new MongoUser("userTest", 20);
    }

    @Test
    public void crudTest() {

    }

    @After
    public void tearDown() {
        userRepository.deleteAll();
    }


}
