package io.ascending.training.jdbc;

import io.ascending.training.model.Package;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public class PackageDAOTest {
    private PackageDAO packageDAO;
    private Package packageTest;

    @Before
    public void init(){
        packageDAO = new PackageDAO();
        LocalDateTime deliveredTime = LocalDateTime.now();
        LocalDateTime arrangeTime = LocalDateTime.now();
        packageTest = new Package("111","shipTest",deliveredTime,"test",1,arrangeTime,"testNote");

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
