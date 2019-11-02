package io.ascending.training.repository.daoTest;

import io.ascending.training.model.Role;
import io.ascending.training.repository.interfaces.RoleDAO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleDAOTest {
    @Autowired
    private RoleDAO roleDAO;
    private Role role;

    @Before
    public void init() {
        role = new Role("testRole", "/test", true, true, true, true);
    }

   @Test
   public void saveTest(){
       Assert.assertTrue(roleDAO.save(role));
       role.setName("updateRole");
       roleDAO.update(role);
       Role role1  = roleDAO.getRoleByName(role.getName());
   }

}
