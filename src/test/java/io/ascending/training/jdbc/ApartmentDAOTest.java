package io.ascending.training.jdbc;

import io.ascending.training.model.postgresModel.Apartment;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ApartmentDAOTest {
    private ApartmentDAO apartmentDAO;
    private Apartment apartmentTest;

    @Before
    public void init(){
        apartmentDAO = new ApartmentDAO();
        apartmentTest = new Apartment("test","address");
        apartmentDAO.save(apartmentTest);
    }

    @After
    public void tearDown(){
        apartmentDAO.delete(apartmentTest.getName());
    }

    @Test
    public void getapartmentsTest(){
        List<Apartment> apartments = apartmentDAO.gerApartment();
        int expectetNum = 3;

        Assert.assertEquals(expectetNum,apartments.size());
    }


}
