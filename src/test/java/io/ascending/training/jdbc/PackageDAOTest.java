package io.ascending.training.jdbc;

import io.ascending.training.model.Package;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PackageDAOTest {
    private ResidentDAO residentDAO;
    private Package packageTest;

    @Before
    public void init(){
        residentDAO = new ResidentDAO();
        packageTest = new Package("test","room",1);
        residentDAO.save(packageTest);
    }

    @After
    public void tearDown(){
        residentDAO.delete(packageTest.getName());
    }

    @Test
    public void getresidentsTest(){
        List<Package> aPackages = residentDAO.getResident();
        int expectetNum = 3;

        Assert.assertEquals(expectetNum, aPackages.size());
    }
}
