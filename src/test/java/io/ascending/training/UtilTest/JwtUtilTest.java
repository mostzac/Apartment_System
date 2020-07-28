package io.ascending.training.UtilTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.postgres.model.Role;
import io.ascending.training.postgres.model.User;
import io.ascending.training.postgres.service.UserService;
import io.ascending.training.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class JwtUtilTest {
    @Autowired
    UserService userService;
    String token;
    @Test
    public void generateTokenTest(){
        User user = userService.getUserByCredential("Admin","123");
        Role role = new Role("testRole", "/roles", true, true, false, true);
        Set<Role> roles = user.getRoles();
        roles.add(role);
        user.setRoles(roles);
        token = JwtUtil.generateToken(user);
        System.out.printf(token);
        Assert.assertNotNull(token);

        Claims actualResult = JwtUtil.decodeJwtToken(token);
        System.out.printf(actualResult.toString());
        Assert.assertEquals(actualResult.getId(),String.valueOf(user.getId()));

    }

}
