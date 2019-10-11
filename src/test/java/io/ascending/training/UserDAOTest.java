package io.ascending.training;

import io.ascending.training.jdbc.UserDAO;
import io.ascending.training.model.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class UserDAOTest {
    private UserDAO userDAO;
    private User userTest;

    @Before
    public void init(){
        userDAO = new UserDAO();
        userTest = new User("test","pass",1);
        userDAO.save(userTest);
    }

    @After
    public void tearDown(){
        userDAO.delete(userTest.getName());
    }

    @Test
    public void getUsersTest(){
        List<User> users = userDAO.getUsers();
        int expectetNum = 3;

        Assert.assertEquals(expectetNum,users.size());
    }
}
