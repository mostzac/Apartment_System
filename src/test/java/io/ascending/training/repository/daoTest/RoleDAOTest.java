package io.ascending.training.repository.daoTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.postgres.model.Role;
import io.ascending.training.postgres.repository.interfaces.RoleDAO;
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
public class RoleDAOTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private RoleDAO roleDAO;
    private Role role;

    @Before
    public void init() {
        role = new Role("testRole", "/test", true, true, true, true);
    }

    @Test
    public void test(){
        List<Role> roles = roleDAO.getRoles();
        roles.forEach(r -> r.toString());
    }

   @Test
   public void saveTest(){
       Assert.assertTrue(roleDAO.save(role));
       role.setName("updateRole");
       roleDAO.update(role);
       Role role1  = roleDAO.getRoleByName(role.getName());
       logger.info(role1.toString());
       roleDAO.deleteRoleByName(role1.getName());
       roleDAO.save(role);
       roleDAO.deleteRoleById(roleDAO.getRoleByName(role.getName()).getId());
   }

}
