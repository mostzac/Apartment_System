package io.ascending.training.springdataTest.Mongo;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.mongoModel.MongoMessage;
import io.ascending.training.model.mongoModel.MongoUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class MongoCRUDTest {
    @Autowired
    private MongoOperations ops;
    @Autowired
    Logger logger;

    private MongoUser userTest;


    @Before
    public void init() {
        userTest = new MongoUser("UserTest", 10);
    }

    @Test
    public void saveAndFind() {
        //Insert
        ops.insert(userTest);
        logger.info("Insert: " + userTest);
        //Find
        MongoUser target = ops.findById(userTest.getId(), MongoUser.class);
        logger.info("Found: " + target);

        //Update
        ops.updateFirst(query(where("name").is("UserTest")), update("age", 25), MongoUser.class);
        userTest = ops.findOne(query(where("name").is("UserTest")), MongoUser.class);
        logger.info("Update: " + userTest);

        //Insert 2 documents as list
        List<MongoUser> lists = new ArrayList<>();
        lists.add(new MongoUser("Test 2", 11));
        lists.add(new MongoUser("Test 3", 22));
        ops.insertAll(lists);
        logger.info("Insert a list: " + lists);
        //Delete Test 2
        MongoUser remove = ops.findOne((query(where("name").is("Test 2"))), MongoUser.class);
        logger.info(String.format("Deleting Test 2: %d record is deleted", ops.remove(remove).getDeletedCount()));
        //Size after deleting
        lists = ops.findAll(MongoUser.class, "mongoUser"); //second para is the collection name
        Assert.assertEquals(2, lists.size());
        logger.info("Records after deleting: " + lists);
        lists = ops.findAllAndRemove(query(where("_class").is(MongoUser.class.getName())), MongoUser.class);
        logger.info("Delete lists success: " + lists);
    }

    @Test
    public void testWithMessage() {
        MongoMessage message = new MongoMessage("hello from test");
        userTest.setMessage(message);
        ops.insert(userTest);
        MongoUser user = ops.findOne(query(where("name").is("UserTest")),MongoUser.class);
        logger.info("UserTest now has a message: "+user.getMessage().getContent());
        MongoMessage retrieve = ops.findOne(query(where("name").is("UserTest")),MongoUser.class).getMessage();
        logger.info("Retrieving message: " + retrieve.getContent());

        //update message
        //upsert() If document is matched, update it, else create a new document by combining the query and update object, it’s works like findAndModifyElseCreate() 🙂
//        ops.update(MongoUser.class).inCollection("mongoUser").matching(query(where("name").is("UserTest"))).apply(update("message","updated message")).upsert();
        ops.update(MongoUser.class).inCollection("mongoUser").matching(query(where("name").is("UserTest"))).apply(update("message","updated message")).first();
        logger.info("Updating message: "+ ops.findOne(query(where("name").is("UserTest")),MongoUser.class).getMessage().getContent());
    }

    @Test
    public void saveAndInsertTest() {
        ops.insert(userTest);
//        ops.insert(user);//Insertion: if Id exists, duplicate key error
        logger.info("Insert: "+ userTest);
        MongoUser usersave = userTest;
        usersave.setAge(20);
        ops.save(usersave); //Save: if Id exists, upsert the record: replace
        logger.info("Save: "+ usersave);
    }

    @Test
    public void UpdateObjTest() {
        MongoUser user = new MongoUser("Ryan",20);
        MongoMessage message= new MongoMessage("original message");
        user.setMessage(message);
        ops.insert(user);
        logger.info("Original Message: "+ user.getMessage().getContent());

        ops.updateFirst(query(where("name").is("Ryan")), update("message.content","first update"), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("1. "+ user.getMessage().getContent());

        // addToSet()
        ops.updateFirst(query(where("name").is("Ryan")), new Update().addToSet("message.tags").each("tag1", "tag2"), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("2. "+ user.getMessage().getContent());


        ops.remove(user);


    }


    @After
    public void tearDown() {
        try{
            ops.remove(userTest);
        }catch (Exception e){
            logger.info("UserTest not inserted into database");
        }



    }

}
