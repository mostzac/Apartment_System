package io.ascending.training;

import io.ascending.training.jdbc.ResidentDAO;
import io.ascending.training.model.Resident;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ResidentDAOTest {
    private ResidentDAO residentDAO;
    private Resident residentTest;

    @Before
    public void init(){
        residentDAO = new ResidentDAO();
        residentTest = new Resident("test","room",1);
        residentDAO.save(residentTest);
    }

    @After
    public void tearDown(){
        residentDAO.delete(residentTest.getName());
    }

    @Test
    public void getresidentsTest(){
        List<Resident> residents = residentDAO.getResident();
        int expectetNum = 3;

        Assert.assertEquals(expectetNum,residents.size());
    }
}
