package io.ascending.training.repository.serviceTest.postgres;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.postgresModel.Role;
import io.ascending.training.model.postgresModel.User;
import io.ascending.training.service.postgres.ApartmentService;
import io.ascending.training.service.postgres.RoleService;
import io.ascending.training.service.postgres.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private ApartmentService apartmentService;
    @Autowired
    private RoleService roleService;
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
        Assert.assertTrue(userService.deleteUserById(user.getId()));
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

    @Test
    public void saveUserWithRoles(){
        Role role = new Role("testRole", "/test", true, true, true, true);
        roleService.save(role);
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.getRoleByName("Manager"));
        roles.add(roleService.getRoleByName("User"));
        user.setRoles(roles);
        userService.save(user);
        User user1 = userService.getUserByAccount(user.getAccount());
        roles = user1.getRoles();
        roles.add(roleService.getRoleByName("testRole"));
        user1.setRoles(roles);
        userService.update(user1);
        userService.deleteUserById(user1.getId());
        roleService.deleteRoleById(roleService.getRoleByName(role.getName()).getId());
    }

    @Test
    public void addTest(){
        User user =  userService.getUserById(1);
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.getRoleByName("Manager"));
        roles.add(roleService.getRoleByName("User"));
        user.setRoles(roles);
        userService.update(user);

    }

    @Test
    public void getUserCredentialTest(){
        User user = userService.getUserByCredential("DaveAccount","1234");
        Assert.assertNotNull(user);
    }


}
