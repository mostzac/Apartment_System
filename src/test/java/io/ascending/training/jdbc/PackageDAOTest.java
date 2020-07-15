package io.ascending.training.jdbc;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.postgresModel.Package;
import io.ascending.training.model.postgresModel.User;
import io.ascending.training.repository.interfaces.postgres.UserDAO;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class PackageDAOTest {
    @Autowired
    private UserDAO userDAO;
    private PackageDAO packageDAO;
    private Package packageTest;

    @Before
    public void init(){
        packageDAO = new PackageDAO();
        LocalDateTime deliveredTime = LocalDateTime.now();
        LocalDateTime arrangeTime = LocalDateTime.now();
        packageTest = new Package("111","shipTest",deliveredTime,"test",1,arrangeTime,"testNote");
        User user = userDAO.getUserByAccount("DaveAccount");
        packageTest.setUser(user);
        packageDAO.save(packageTest);
    }

    @After
    public void tearDown(){
        packageDAO.delete(packageTest);
    }

    @Test
    public void getresidentsTest(){
        List<Package> aPackages = packageDAO.getPackage();
        int expectetNum = 3;

        Assert.assertEquals(expectetNum, aPackages.size());
    }
}
