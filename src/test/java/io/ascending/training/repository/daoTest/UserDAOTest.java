package io.ascending.training.repository.daoTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.Role;
import io.ascending.training.model.User;
import io.ascending.training.repository.impl.ApartmentDAOImpl;
import io.ascending.training.repository.impl.RoleDAOImpl;
import io.ascending.training.repository.impl.UserDAOImpl;
import io.ascending.training.repository.interfaces.ApartmentDAO;
import io.ascending.training.repository.interfaces.RoleDAO;
import io.ascending.training.repository.interfaces.UserDAO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.TransactionScoped;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class UserDAOTest {
    @Autowired
    private UserDAO userDAO;
    private User user;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private ApartmentDAO apartmentDAO;

    @Before
    public void init(){
        user = new User("accountTest","passworrTest","nameTest","777");
        user.setApartment(apartmentDAO.getApartmentByName("Buchanan"));
        Assert.assertTrue(userDAO.save(user));
    }

    @Test
    public void getUsersTest(){
        List<User> users = userDAO.getUsers();
        int expectedNum = 4;
        users.forEach(user -> logger.info(user.toString()));
        Assert.assertEquals(expectedNum, users.size());
    }

    @Test
    public void updateUserTest(){
        user = userDAO.getUserByAccount(user.getAccount());
        if(user != null){
            logger.info(String.valueOf(user.getId()));
        }
        else {
            logger.info("User is null");
        }
        user.setName("update");
        user.setPassword("12312");
        Assert.assertTrue(userDAO.update(user));
    }

    @After
    public void tearDown(){
        Assert.assertTrue(userDAO.deleteUserByAccount(user.getAccount()));
    }

    @Test
    public void deleteByIdTest(){
        User user = new User("accountIdTest","passworrTest","nameTest","777");
        user.setApartment(apartmentDAO.getApartmentByName("Buchanan"));
        Assert.assertTrue(userDAO.save(user));
        Assert.assertTrue(userDAO.deleteUserById(userDAO.getUserByAccount("accountIdTest").getId()));
    }



    @Test
    public void delelteByAccountTest(){
        User user = new User("deleteTest","passworrTest","nameTest","777");
        userDAO.save(user);
        Assert.assertTrue(userDAO.deleteUserByAccount(user.getAccount()));
    }

    @Test
    public void saveUserWithRoleTest(){
        List<Role> roles = new ArrayList<>();
        roles.add(roleDAO.getRoleByName("Manager"));
        roles.add(roleDAO.getRoleByName("User"));
//        user = userDAO.getUserByAccount("accountTest");
        user.setRoles(roles);
        userDAO.save(user);
//        User user1 = userDAO.getUserByAccount(user.getAccount());
//        Assert.assertEquals(user1.getRoles().size(),roles.size());
//        userDAO.deleteUserById(user1.getId());
        Assert.assertEquals(user.getRoles().size(),roles.size());
//        userDAO.deleteUserById(user.getId());
    }

    @Test
    public void setUserWithRole() {
        List<Role> roles = new ArrayList<>();
        roles.add(roleDAO.getRoleByName("User"));
        User user = userDAO.getUserById(1);
        user.setRoles(roles);
        userDAO.update(user);
    }
    @Test
    public void setAdmin(){
        List<Role> roles = new ArrayList<>();
        roles.add(roleDAO.getRoleByName("Admin"));
        User user = userDAO.getUserByAccount("Admin");
        user.setRoles(roles);
        userDAO.update(user);
    }



}


