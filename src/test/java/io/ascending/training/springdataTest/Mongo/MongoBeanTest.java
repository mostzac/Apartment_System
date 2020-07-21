package io.ascending.training.springdataTest.Mongo;

import com.mongodb.client.MongoClient;
import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.mongo.model.MongoUser;
import io.ascending.training.postgres.service.ApartmentService;
import io.ascending.training.postgres.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class MongoBeanTest {
    @Autowired
    private UserService userService;
    @Autowired
    private Logger logger;
    @Autowired
    private MongoClient mongoClient;
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private MongoClientFactoryBean mongoClientFactoryBean;
    @Autowired
    private MongoDatabaseFactory mongoDatabaseFactory;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoOperations mongoOperations;

    @Test
    public void usertoString() {
        System.out.println(userService.getUserById(1).toString());
    }

    @Test
    public void mongoClientTest() {
        //Test MongoClient
        MongoOperations mongoOps = new MongoTemplate(mongoClient, "mongoTest");
        mongoOps.insert(new MongoUser("mongoClient", 10));

        logger.info(mongoOps.findOne(new Query(where("name").is("mongoClient")), MongoUser.class).toString());
        mongoOps.findAllAndRemove(query(where("_class").is(MongoUser.class.getName())), MongoUser.class);
//        mongoOps.dropCollection("mongoUser");
    }

    @Test
    public void mongoClientFactoryBeanTest() throws Exception {
        //Test MongoClientFactoryBean
        MongoOperations mongoOps = new MongoTemplate(mongoClientFactoryBean.getObject(), "mongoTest");
        mongoOps.insert(new MongoUser("mongoClientFactoryBean", 10));
        logger.info(mongoOps.findOne(new Query(where("name").is("mongoClientFactoryBean")), MongoUser.class).toString());
        mongoOps.findAllAndRemove(query(where("_class").is(MongoUser.class.getName())), MongoUser.class);
    }

    @Test
    public void mongoDatabaseFactoryTest() {
        MongoOperations ops = new MongoTemplate(mongoDatabaseFactory);
        ops.insert(new MongoUser("mongoDatabaseFactoryTest", 10));
        logger.info(ops.findOne(new Query(where("name").is("mongoDatabaseFactoryTest")), MongoUser.class).toString());
        ops.remove(new Query(), MongoUser.class);
    }

    @Test
    public void mongoTemplateTest() {
        MongoOperations ops = mongoTemplate;
        ops.insert(new MongoUser("mongoTemplate", 10));
        logger.info(ops.findOne(new Query(where("name").is("mongoTemplate")), MongoUser.class).toString());
        ops.findAllAndRemove(query(where("_class").is(MongoUser.class.getName())), MongoUser.class);
    }

    @Test
    public void mongoOperationTest() {
        mongoOperations.insert(new MongoUser("mongoOperations", 10));
        logger.info(mongoOperations.findOne(new Query(where("name").is("mongoOperations")), MongoUser.class).toString());
        mongoOperations.findAllAndRemove(query(where("_class").is(MongoUser.class.getName())), MongoUser.class);
    }




}
