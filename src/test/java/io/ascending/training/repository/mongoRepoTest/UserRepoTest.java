package io.ascending.training.repository.mongoRepoTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.mongo.model.MongoMessage;
import io.ascending.training.mongo.model.MongoUser;
import io.ascending.training.mongo.repository.MessageRepository;
import io.ascending.training.mongo.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class UserRepoTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;

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
        user.setMessage(messageRepository.insert(new MongoMessage("hello")));
        userRepository.save(user); //update
        logger.info("Update: " + user);
        userRepository.save(new MongoUser("userNew", 25));
        MongoUser newUser = userRepository.findByName("userNew").get();
        logger.info("FindByName('UserNew'):  " + newUser);
        logger.info("FindAll: " + userRepository.findAll());
        Assert.assertTrue(userRepository.deleteByName("userTest") == 1 ? true : false);
        logger.info("After deleting userTest: " + userRepository.findAll());
    }

    @Test
    public void exampleStreamableAndSortTest() {
        // sort expression
        Sort sort = Sort.by("age").ascending().and(Sort.by("name").descending());
        // query by example
        Example userExample = Example.of(new MongoUser("A", 25));

        List<MongoUser> list = new ArrayList<>();
        list.add(new MongoUser("A", 30));
        list.add(new MongoUser("A", 25));
        list.add(new MongoUser("B", 30));
        list.add(new MongoUser("C", 20));
        list.add(new MongoUser("D", 20));
        MongoUser a = new MongoUser("AAA", 10);
        a.setMessage(messageRepository.insert(new MongoMessage("i have message")));
        userRepository.insert(a);
        MongoUser a1 = new MongoUser("AAA", 15);
        a1.setMessage(messageRepository.findByContent("i have message").get());
        userRepository.insert(a1);
        MongoUser b = new MongoUser("BBB", 20);
        b.setMessage(messageRepository.insert(new MongoMessage("message 2")));
        userRepository.insert(b);
        logger.info("Unsorted record: " + userRepository.insert(list));
        logger.info("Sorted :" + userRepository.findAll(sort));
        logger.info("Example query: " + userRepository.findAll(userExample));
        logger.info("FindByMessageContainsContent: " + userRepository.findAllByMessageContainsContent("i have message").stream().collect(Collectors.toList()));
        logger.info("Streamable user: " + userRepository.findAllByMessageContainsContent("i have message")
                .and(userRepository.findAllByMessageContainsContent("message 2")).stream().collect(Collectors.toList()));
        logger.info("Streamable map to content: " + userRepository.findAllByMessageContainsContent("i have message")
                .and(userRepository.findAllByMessageContainsContent("message 2")).stream().map(MongoUser::getMessage).map(MongoMessage::getContent).collect(Collectors.joining(", ")));
        logger.info("Page: " + userRepository.findAll(PageRequest.of(0, 3, Sort.by("age").ascending())).getContent());
        logger.info("Contains: " + userRepository.findByMessageContains(messageRepository.findByContent("i have message").get()));
        logger.info("Containing: " + userRepository.findByMessageContaining(messageRepository.findByContent("i have message").get()));

    }

    @Test
    public void userAndMessageTest() {
        userRepository.insert(user);
        logger.info("Insert: " + user);
        user.setAge(22);
        user.setMessage(messageRepository.insert(new MongoMessage("hello")));
        userRepository.save(user); //update
        logger.info("Save: " + user);
        MongoMessage msg= messageRepository.findByContent("hello").get();
        logger.info("findByMessageContains: " + userRepository.findByMessageContains(msg));
        logger.info("findByMessageContaining: " + userRepository.findByMessageContaining(msg));
        logger.info("findAllByMessageContainsContent" + userRepository.findAllByMessageContainsContent(msg.getId()));
    }


    @After
    public void tearDown() {
        userRepository.deleteAll();
        messageRepository.deleteAll();
    }


}
