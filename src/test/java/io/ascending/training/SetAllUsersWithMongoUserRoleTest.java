package io.ascending.training;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.postgres.model.Role;
import io.ascending.training.postgres.model.User;
import io.ascending.training.postgres.repository.interfaces.UserDAO;
import io.ascending.training.postgres.service.RoleService;
import io.ascending.training.postgres.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class SetAllUsersWithMongoUserRoleTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserDAO userDAO;

    @Test
    public void initializeUserRoles() {
        userService.getUserByAccount("Admin");
    }

    @Test
    public void addTest(){
        User user =  userService.getUserById(1);
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getRoleByName("Manager"));
        roles.add(roleService.getRoleByName("User"));
        roles.add(roleService.getRoleByName("MongoUser"));
        user.setRoles(roles);
        userService.updateUserRoles(user);

    }

    @Test
    public void updateTest() {
        List<User> users = userDAO.getUsers();
        for (User i : users) {
            Set<Role> roles;
            if (i.getRoles() != null) {
                roles = i.getRoles();
            } else {
                roles = new HashSet<>();
            }

            roles.add(roleService.getRoleByName("MongoUser"));
            i.setRoles(roles);
            userDAO.update(i);
        }
    }
}
