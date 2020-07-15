package io.ascending.training.UtilTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.postgresModel.Role;
import io.ascending.training.model.postgresModel.User;
import io.ascending.training.service.postgres.UserService;
import io.ascending.training.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class JwtUtilTest {
    @Autowired
    UserService userService;
    String token;
    @Test
    public void generateTokenTest(){
        User user = userService.getUserById(1);
        Role role = new Role("testRole", "/roles", true, true, false, true);
        List<Role> roles = user.getRoles();
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
