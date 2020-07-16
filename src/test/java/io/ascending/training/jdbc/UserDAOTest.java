package io.ascending.training.jdbc;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.postgresModel.User;
import io.ascending.training.repository.postgres.interfaces.ApartmentDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class UserDAOTest {
    @Autowired
    private ApartmentDAO apartmentDAO;
    private UsersDAO userDAO;
    private User userTest;

    @Before
    public void init(){
        userDAO = new UsersDAO();
        userTest = new User("testJDBC","passworrTest","nameTest","777");
        userTest.setApartment(apartmentDAO.getApartmentByName("Buchanan"));
        userDAO.save(userTest);
    }

    @After
    public void tearDown(){
        Assert.assertTrue(userDAO.delete(userTest.getAccount()));
    }

    @Test
    public void getUsersTest(){
        List<User> users = userDAO.getUsers();
        int expectetNum = 4;

        Assert.assertEquals(expectetNum,users.size());
    }
}
