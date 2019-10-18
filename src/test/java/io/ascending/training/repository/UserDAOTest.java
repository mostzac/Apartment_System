package io.ascending.training.repository;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.ascending.training.model.User;
import io.ascending.training.repository.impl.UserDAOImpl;
import io.ascending.training.repository.interfaces.UserDAO;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDAOTest {
    private static UserDAO userDAO;
    private static User user;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void init(){
        userDAO = new UserDAOImpl();
        user = new User("test","passworrTest",1);
    }

    @Test
    public void getUsersTest(){
        List<User> users = userDAO.getUsers();
        int expectedNum = 2;
        users.forEach(user -> logger.info(user.toString()));
        Assert.assertEquals(expectedNum, users.size());
    }

    @Test
    public void saveUserTest(){
        Assert.assertTrue(userDAO.save(user));
    }

    @Test
    public void updateUserTest(){
        user = userDAO.getUserByName(user.getName());
        if(user != null){
            logger.info(String.valueOf(user.getId()));
        }
        else {
            logger.info("User is not null");
        }
        user.setPassword("12312");
        Assert.assertTrue(userDAO.update(user));
    }

    @Test
    public void deletTest(){
        Assert.assertTrue(userDAO.delete(user));
    }

    @Test
    public void delelteByNameTest(){
        User user = new User("testName","123123",1);
        userDAO.save(user);
        Assert.assertTrue(userDAO.deleteUserByName(user.getName()));
    }

}


