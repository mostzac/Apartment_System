package io.ascending.training.repository.serviceTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.Role;
import io.ascending.training.repository.interfaces.RoleDAO;
import io.ascending.training.service.RoleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class RoleServiceTest {
    @Autowired
    private RoleService roleService;
    private Role role;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before
    public void init(){
        role = new Role("testRole", "/test", true, true, true, true);
    }

    @Test
    public void roleServiceTest(){
        roleService.save(role);
        Role role1 = roleService.getRoleByName(role.getName());
        role1.setName("updated");
        roleService.update(role1);
        roleService.deleteById(role1.getId());
    }

}
