package io.ascending.training.repository;

import io.ascending.training.model.Package;
import io.ascending.training.repository.impl.ResidentDAOImpl;
import io.ascending.training.repository.interfaces.ResidentDAO;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PackageDAOTest {
    private static ResidentDAO residentDAO;
    private static Package aPackage;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void init() {
        residentDAO = new ResidentDAOImpl();
        aPackage = new Package("test","777",1);
    }

    @Test
    public void getResidents() {
        List<Package> aPackages = residentDAO.getResidents();
        int expectedNum = 2;
        aPackages.forEach(res -> logger.info(res.toString()));
        Assert.assertEquals(expectedNum, aPackages.size());
    }

    @Test
    public void saveTest() {
        Assert.assertTrue(residentDAO.save(aPackage));

    }

    @Test
    public void deleteTest() {
        Assert.assertTrue(residentDAO.delete(aPackage));
    }

    @Test
    public void deleteByNameTest(){
        Package res = new Package("testbyname","123",2);
        residentDAO.save(res);
        Assert.assertTrue(residentDAO.deleteResidentByName(res.getName()));
    }

    @Test
    public void updateTest() {
        aPackage = residentDAO.getResidentByName("test");

        if (aPackage != null) {
            logger.info(String.valueOf(aPackage.getId()));
        }
        else {
            logger.info("Resident is null");
        }
        aPackage.setRoom("7777");
        Assert.assertTrue(residentDAO.update(aPackage));
    }

}
