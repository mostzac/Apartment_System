package io.ascending.training;

import com.mongodb.client.MongoClients;
import com.mongodb.internal.async.client.AsyncMongoClients;
import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.service.UserService;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class MongoTest {
    @Autowired
    UserService userService;
    @Autowired
    Logger logger;

    @Test
    public void usertoString() {
        System.out.println(userService.getUserById(1).toString());
    }

    @Test
    public void mongoOperation() {
        MongoOperations mongoOperations = new MongoTemplate(MongoClients.create(), "database");
    }

}
