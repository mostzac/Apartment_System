package io.ascending.training;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.postgres.model.Role;
import io.ascending.training.postgres.model.User;
import io.ascending.training.postgres.service.RoleService;
import io.ascending.training.postgres.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class SetAllUsersWithMongoUserRoleTest {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Test
    public void updateTest() {
        List<User> users = userService.getUsers();
        for (User i : users) {
            List<Role> roles;
            if (i.getRoles() != null) {
                roles = i.getRoles();
            } else {
                roles = new ArrayList<>();
            }

            roles.add(roleService.getRoleByName("MongoUser"));
            i.setRoles(roles);
            userService.update(i);
        }
    }
}
