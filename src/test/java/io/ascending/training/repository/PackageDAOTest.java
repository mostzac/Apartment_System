package io.ascending.training.repository;

import io.ascending.training.model.Package;
import io.ascending.training.repository.impl.PackageDAOImpl;
import io.ascending.training.repository.impl.UserDAOImpl;
import io.ascending.training.repository.interfaces.PackageDAO;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PackageDAOTest {
    private static PackageDAO packageDAO;
    private static Package pack;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void init() {
        packageDAO = new PackageDAOImpl();
        LocalDateTime deliveredTime = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime arrangeTime = deliveredTime.plusMonths(1);
        pack = new Package("111","shipTest",deliveredTime,"test",1,arrangeTime,"testNote");
        pack.setUser(new UserDAOImpl().getUserByAccount("DaveAccount"));
    }

    @Test
    public void getPackages() {
        List<Package> packages = packageDAO.getPackages();
        int expectedNum = 2;
        packages.forEach(pack -> logger.info(pack.toString()));
        Assert.assertEquals(expectedNum, packages.size());
    }

    @Test
    public void saveTest() {
        Assert.assertTrue(packageDAO.save(pack));

    }

    @Test
    public void deleteTest() {
        Assert.assertTrue(packageDAO.delete(pack));
    }


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

}
