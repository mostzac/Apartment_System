package io.ascending.training.repository.daoTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.postgresModel.Package;
import io.ascending.training.repository.interfaces.PackageDAO;
import io.ascending.training.repository.interfaces.UserDAO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class PackageDAOTest {
    @Autowired
    private PackageDAO packageDAO;
    private Package pack;
    @Autowired
    private UserDAO userDAO;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void init() {
        LocalDateTime deliveredTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime arrangeTime = deliveredTime.plusMonths(1);
        pack = new Package("111","shipTest",deliveredTime,"test",1,arrangeTime,"testNote");
        pack.setUser(userDAO.getUserByAccount("DaveAccount"));
        Assert.assertTrue(packageDAO.save(pack));
    }

    @Test
    public void getPackages() {
        List<Package> packages = packageDAO.getPackages();
        int expectedNum = 3;
        packages.forEach(pack -> logger.info(pack.toString()));
        Assert.assertEquals(expectedNum, packages.size());
    }

//    @Test
//    public void saveTest() {
//        Assert.assertTrue(packageDAO.save(pack));
//
//    }

    @Test
    public void updateTest() {
        pack = packageDAO.getPackageByShipNumber("111");

        if (pack != null) {
            logger.info(String.valueOf(pack.getId()));
        }
        else {
            logger.info("Resident is null");
        }
        pack.setDescription("this is the test");
        pack.setShipper("newTest");
        Assert.assertTrue(packageDAO.update(pack));
    }

    @After
    public void deleteTest() {
        Assert.assertTrue(packageDAO.deletePackageByShipNumber(pack.getShipNumber()));
    }




}
