package io.ascending.training.repository.serviceTest.postgres;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.postgres.model.Role;
import io.ascending.training.postgres.model.User;
import io.ascending.training.postgres.service.ApartmentService;
import io.ascending.training.postgres.service.RoleService;
import io.ascending.training.postgres.service.UserService;
import org.junit.After;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        userService.save(user);
    }


    @Test
    public void getUsersTest(){
        List<User> users = userService.getUsers();
        int expectedNum = 4;
        Assert.assertEquals(expectedNum,users.size());
    }

    @Test
    public void Test(){
        logger.info(user.getApartment().toString());
        user.setApartment(apartmentService.getApartmentByName("Crystal Plaza"));
        userService.updateUserOptionalApartment(user);
        user = userService.getUserByAccount(user.getAccount());
        logger.info(user.getName());
    }

    @Test
    public void saveUserWithRoles(){
        Role role = new Role("testRole", "/test", true, true, true, true);
        roleService.save(role);
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("Manager"));
        roles.add(roleService.getRoleByName("User"));
        user.setRoles(roles);
        userService.save(user);
        User user1 = userService.getUserByAccount(user.getAccount());
        roles = user1.getRoles();
        roles.add(roleService.getRoleByName("testRole"));
        user1.setRoles(roles);
        userService.updateUserRoles(user1);
        roleService.deleteRoleById(roleService.getRoleByName(role.getName()).getId());
    }

    @Test
    public void getUserCredentialTest(){
        User user = userService.getUserByCredential("DaveAccount","1234");
        Assert.assertNotNull(user);
    }

    @After
    public void tearDown() {
        Assert.assertTrue(userService.deleteUserByAccount(user.getAccount()));
    }


}
