package io.ascending.training.springdataTest.Mongo;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.mongo.model.MongoMessage;
import io.ascending.training.mongo.model.MongoUser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.AggregationUpdate;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.springframework.data.mongodb.core.aggregation.ComparisonOperators.*;
import static org.springframework.data.mongodb.core.aggregation.ConditionalOperators.Switch.CaseOperator.when;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class MongoCRUDTest {
    @Autowired
    private MongoOperations ops;
    @Autowired
    private Logger logger;

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
        lists = ops.findAll(MongoUser.class); //second para is the collection name
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
        MongoUser user = ops.findOne(query(where("name").is("UserTest")), MongoUser.class);
        logger.info("UserTest now has a message: " + user.getMessage().getContent());
        MongoMessage retrieve = ops.findOne(query(where("name").is("UserTest")), MongoUser.class).getMessage();
        logger.info("Retrieving message: " + retrieve.getContent());

        //update message
        //upsert() If document is matched, update it, else create a new document by combining the query and update object, it’s works like findAndModifyElseCreate() 🙂
//        ops.update(MongoUser.class).inCollection("mongoUser").matching(query(where("name").is("UserTest"))).apply(update("message","updated message")).upsert();
        ops.update(MongoUser.class).inCollection("mongoUser").matching(query(where("name").is("UserTest"))).apply(update("message", "updated message")).first();
        logger.info("Updating message: " + ops.findOne(query(where("name").is("UserTest")), MongoUser.class).getMessage().getContent());
    }

    @Test
    public void saveAndInsertTest() {
        ops.insert(userTest);
//        ops.insert(user);//Insertion: if Id exists, duplicate key error
        logger.info("Insert: " + userTest);
        MongoUser usersave = userTest;
        usersave.setAge(20);
        ops.save(usersave); //Save: if Id exists, upsert the record: replace
        logger.info("Save: " + usersave);
    }

    @Test
    public void UpdateObjTest() {
        MongoUser user = new MongoUser("Ryan", 20);
        MongoMessage message = new MongoMessage("original message");
        user.setMessage(message);
        ops.insert(user);
        logger.info("Original Message: " + user.getMessage().getContent());

        ops.updateFirst(query(where("name").is("Ryan")), update("message.content", "first update"), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("1. " + user.getMessage().getContent());

        // addToSet()
        ops.updateFirst(query(where("name").is("Ryan")), new Update().addToSet("message.tags").each("tag1", "tag1", "tag2", "tag3"), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("2. " + Arrays.toString(user.getMessage().getTags()));

        // inc()
        ops.updateFirst(query(where("name").is("Ryan")), new Update().inc("age", 5), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("3. " + user);

        // max()
        ops.updateFirst(query(where("name").is("Ryan")), new Update().max("age", 26), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("4. " + user);

        // min()
        ops.updateFirst(query(where("name").is("Ryan")), new Update().min("age", 20), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("5. " + user);

        // multiply()
        ops.updateFirst(query(where("name").is("Ryan")), new Update().multiply("age", 1.5), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("6. " + user);

        // The $pop operator removes the first or last element of an array. Pass $pop a value of -1 to remove the first element of an array and 1 to remove the last element in an array.
        ops.updateFirst(query(where("name").is("Ryan")), new Update().pop("message.tags", Update.Position.FIRST), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("7. " + Arrays.toString(user.getMessage().getTags()));

        // The $pull operator removes from an existing array all instances of a value or values that match a specified condition.
        ops.updateFirst((query(where("name").is("Ryan"))), new Update().pullAll("message.tags", new String[]{"tag3", "tag2"}), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("8. " + Arrays.toString(user.getMessage().getTags()));

        // The $push operator appends a specified value to an array.
        ops.updateFirst((query(where("name").is("Ryan"))), new Update().push("message.tags").each("new tag1", "new tag2"), MongoUser.class);
        ops.updateFirst(query(where("name").is("Ryan")), new Update().push("message.tags").atPosition(0).value("Push to the first"), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("9. " + Arrays.toString(user.getMessage().getTags()));
        //The $slice modifier limits the number of array elements during a $push operation.
        ops.updateFirst(query(where("name").is("Ryan")), new Update().push("message.tags").slice(-3).each("slice 0", "slice 1"), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("9.1.slice: " + Arrays.toString(user.getMessage().getTags()));


        // rename() rename the field,  in this test because changing the field the error occurs when doing the mapping.
//        ops.updateFirst(query(where("name").is("Ryan")), new Update().rename("age", "current age"), MongoUser.class,collection);
//        user = ops.findById(user.getId(), MongoUser.class,collection);
//        logger.info("10. " + user);

        // set()
        ops.updateFirst(query(where("name").is("Ryan")), new Update().set("message.content", "new set()"), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("10. " + user.getMessage().getContent());

        // setting a new field , but retrieving and mapping will not show
        ops.updateFirst(query(where("name").is("Ryan")), new Update().set("new field", 22), MongoUser.class);
        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("11. " + user);

        ops.updateFirst(query(where("name").is("Ryan")), new Update().setOnInsert("new field 2", 25), MongoUser.class);// one document is found, so the setOnInsert is ignored
        ops.update(MongoUser.class).matching(query(where("name").is("Ryan"))).apply(new Update().setOnInsert("new field 2", 25)).first();// one document is found, so the setOnInsert is ignored
        ops.update(MongoUser.class).matching(query(where("name").is("Ryan"))).apply(new Update().setOnInsert("new field 2", 25)).upsert();// not working because one document found
        ops.update(MongoUser.class).matching(query(where("name").is("Ryan"))).apply(new Update().set("new field 2", 25)).first();// aggregation have to set .first() .upsert() or .all() to


        // If an update operation with upsert: true results in an insert of a document, then $setOnInsert assigns the specified values to the fields in the document. If the update operation does not result in an insert, $setOnInsert does nothing.
        // setOnInsert()  is a document is found will set, if not found, insert a new one
//        ops.update(MongoUser.class).matching(query(where("name").is("new Ryan"))).apply(new Update().setOnInsert("new field 2",22)); // insert not work
        ops.updateFirst(query(where("name").is("Ryan")), new Update().setOnInsert("new field 2", 22), MongoUser.class); // will not setting a new field
        ops.updateFirst(query(where("name").is("new Ryan")), new Update().setOnInsert("new field 2", 22), MongoUser.class); // will not setting a new field
        ops.updateFirst(query(where("name").is("new Ryan")), new Update().set("new field 2", 22), MongoUser.class); // will not setting a new field
        ops.update(MongoUser.class).matching(query(where("name").is("new Ryan"))).apply(new Update().setOnInsert("new field 2", 33).setOnInsert("new field 3", 35)).upsert();// insert work
        // The $unset operator deletes a particular field. Consider the following syntax:
        // unset()
        ops.updateFirst(query(where("name").is("new Ryan")), new Update().unset("new field 2").unset("new field 3"), MongoUser.class); // will delete new Ryan fields
        ops.remove(query(where("name").is("new Ryan")), "mongoUser");


        user = ops.findById(user.getId(), MongoUser.class);
        logger.info("12. " + user);

        ops.remove(user);
    }

    @Test
    public void findAndModifyTest() {
        ops.insert(new MongoUser("Tom", 20));
        ops.insert(new MongoUser("Ryan", 24));
        ops.insert(new MongoUser("Ryo", 30));

        Query query = new Query(where("name").is("Ryan"));
        Update update = new Update().inc("age", 1);

        MongoUser oldUser = ops.update(MongoUser.class).matching(query).apply(update).findAndModifyValue();// return the old object, but document has been modify
        Assert.assertEquals(24, oldUser.getAge());  // old - new : 24 - 25  return 24

        Optional<MongoUser> oldUser2 = ops.update(MongoUser.class).matching(query).apply(update).findAndModify();// return the old object, but document has been modify
        Assert.assertEquals(25, oldUser2.get().getAge());// old - new : 25 - 26 return 26

        MongoUser newUser = ops.query(MongoUser.class).matching(query).oneValue();// retrieving the new document
        Assert.assertEquals(26, newUser.getAge()); // now 26

        MongoUser newUser1 = ops.update(MongoUser.class).matching(query).apply(update).withOptions(FindAndModifyOptions.options().returnNew(true)).findAndModifyValue();// return the new object
        Assert.assertEquals(27, newUser1.getAge());

        MongoUser upsertUser = ops.update(MongoUser.class).matching(query(where("name").is("UpsertUser"))).apply(update).withOptions(FindAndModifyOptions.options().upsert(true).returnNew(true)).findAndModifyValue();
        logger.info("UpsertUser: " + upsertUser);


        ops.remove(new Query(), MongoUser.class);
    }

    @Test
    public void aggregationTest() {
        ops.insert(new MongoUser("Tom", 20));
        ops.insert(new MongoUser("Ryan", 24));
        ops.insert(new MongoUser("Ryo", 30));
        //bug
//        AggregationUpdate update = AggregationUpdate.newUpdate(
//                set("avg").toValue(ArithmeticOperators.valueOf("age").avg())
//                        .set("stage").toValue(ConditionalOperators.switchCases(
//                        when(valueOf("avg").greaterThanValue(25)).then("Greater than 25"),
//                        when(valueOf("avg").equalToValue(25)).then("equal to 25"),
//                        when(valueOf("avg").lessThanEqualToValue(25)).then("less than 25")
//                        ).defaultTo("default")
//                ));

        AggregationUpdate update = AggregationUpdate.update()
                .set("avg").toValue(ArithmeticOperators.valueOf("age").avg())
                .set("stage").toValue(ConditionalOperators.switchCases(
                        when(valueOf("avg").greaterThanValue(25)).then("Greater than 25"),
                        when(valueOf("avg").equalToValue(25)).then("equal to 25"),
                        when(valueOf("avg").lessThanEqualToValue(25)).then("less than 25")
                        ).defaultTo("default")
                );

        ops.update(MongoUser.class).apply(update).all();
        logger.info("done ");
    }

    @Test
    public void documentTest() {
        ops.insert(new MongoUser("Ryan", 20));
        ops.insert(new MongoUser("Cat", 21));
        ops.insert(new MongoUser("Bob", 21));
        ops.insert(new MongoUser("Tom", 25));
        ops.insert(new MongoUser("Ryo", 30));
        Optional<MongoUser> result = ops.update(MongoUser.class).matching(query(where("name").is("Ryan")))
                .replaceWith(new MongoUser("Replace user", 30))
                .withOptions(FindAndReplaceOptions.options().upsert().returnNew())
                .as(MongoUser.class)
                .findAndReplace();
        logger.info("Replace: " + result.get());

        //querying documents:
        //using plain JSON string
        BasicQuery query = new BasicQuery("{age:{$lte:25}}");
        List<MongoUser> lists = ops.find(query, MongoUser.class);
        lists.sort(Comparator.comparingInt(MongoUser::getAge).reversed());
        logger.info("JSON query Lists: " + new ArrayList<>(lists));

        //using Mongotemplate
        Query query1 = new Query(where("age").lte(25)).skip(0).with(Sort.by(Sort.Direction.DESC, "age"));
        query1.fields().exclude("_id");
        lists = ops.query(MongoUser.class).matching(query1).all();
        logger.info("MongoTemplate query Lists: " + lists);

        //distinct
//        List<Object> list = ops.query(MongoUser.class).distinct("age").all();
        List<Integer> list = ops.query(MongoUser.class).distinct("age").as(Integer.class).all();//Retrieving strongly typed distinct values
        logger.info("Distinct: " + list);

    }


    @After
    public void tearDown() {
        ops.remove(new Query(), MongoUser.class);
    }

}
