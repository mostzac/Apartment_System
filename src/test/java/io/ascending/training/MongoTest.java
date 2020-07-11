package io.ascending.training;

import com.mongodb.client.MongoClient;
import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.mongoModel.MongoUser;
import io.ascending.training.service.ApartmentService;
import io.ascending.training.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.data.mongodb.core.query.Criteria.where;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class MongoTest {
    @Autowired
    UserService userService;
    @Autowired
    Logger logger;
    @Autowired
    MongoClient mongoClient;
    @Autowired
    ApartmentService apartmentService;

    @Test
    public void usertoString() {
        System.out.println(userService.getUserById(1).toString());
    }

    @Test
    public void mongoOperation() {
        MongoOperations mongoOps = new MongoTemplate(mongoClient, "mongoTest");
        mongoOps.insert(new MongoUser("Ryan",10));

        logger.info(mongoOps.findOne(new Query(where("name").is("Ryan")),MongoUser.class).toString());
        mongoOps.dropCollection("mongoUser");
    }

}
