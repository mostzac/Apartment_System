package io.ascending.training.repository;

import io.ascending.training.model.Resident;
import io.ascending.training.repository.impl.ResidentDAOImpl;
import io.ascending.training.repository.interfaces.ResidentDAO;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.util.List;

public class ResidentDAOTest {
    private static ResidentDAO residentDAO;
    private static Resident resident;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void init() {
        residentDAO = new ResidentDAOImpl();
        resident = new Resident("test","777",1);
    }

    @Test
    public void getResidents() {
        List<Resident> residents = residentDAO.getResidents();
        int expectedNum = 2;
        residents.forEach(res -> logger.info(res.toString()));
        Assert.assertEquals(expectedNum, residents.size());
    }

    @Test
    public void saveTest() {
        Assert.assertTrue(residentDAO.save(resident));

    }

    @Test
    public void deleteTest() {
        Assert.assertTrue(residentDAO.delete(resident));
    }

    @Test
    public void deleteByNameTest(){
        Resident res = new Resident("testbyname","123",2);
        residentDAO.save(res);
        Assert.assertTrue(residentDAO.deleteResidentByName(res.getName()));
    }

    @Test
    public void updateTest() {
        resident = residentDAO.getResidentByName("test");

        if (resident != null) {
            logger.info(String.valueOf(resident.getId()));
        }
        else {
            logger.info("Resident is null");
        }
        resident.setRoom("7777");
        Assert.assertTrue(residentDAO.update(resident));
    }

}
