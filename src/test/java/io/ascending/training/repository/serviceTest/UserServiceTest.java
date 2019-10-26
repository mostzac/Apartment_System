package io.ascending.training.repository.serviceTest;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.Apartment;
import io.ascending.training.model.User;
import io.ascending.training.service.ApartmentService;
import io.ascending.training.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private ApartmentService apartmentService;
    private User user;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void init(){

        user = new User("accountTest","passworrTest","nameTest","777");
        user.setApartment(apartmentService.getApartmentByName("Buchanan"));
    }

    @Test
    public void saveAndDeleteTest(){
        Assert.assertTrue(userService.save(user));
        Assert.assertTrue(userService.delete(user));
    }


    @Test
    public void getUsersTest(){
        List<User> users = userService.getUsers();
        int expectedNum = 3;
        Assert.assertEquals(expectedNum,users.size());
    }

    @Test
    public void Test(){
        userService.save(user);
        logger.info(user.getApartment().toString());
        user.setApartment(apartmentService.getApartmentByName("Crystal Plaza"));
        userService.update(user);
        user = userService.getUserByAccount(user.getAccount());
        logger.info(user.getApartment().toString());
        Assert.assertTrue(userService.deleteUserByAccount(user.getAccount()));

    }

}
