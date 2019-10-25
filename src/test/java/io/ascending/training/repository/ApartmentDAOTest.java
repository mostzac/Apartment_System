package io.ascending.training.repository;

import io.ascending.training.jdbc.UsersDAO;
import io.ascending.training.model.Apartment;
import io.ascending.training.model.User;
import io.ascending.training.repository.impl.ApartmentDAOImpl;
import io.ascending.training.repository.impl.UserDAOImpl;
import io.ascending.training.repository.interfaces.ApartmentDAO;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ApartmentDAOTest {
    private static ApartmentDAO apartmentDAO;
    private static Apartment apartment;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @BeforeClass
    public static void init() {
        apartmentDAO = new ApartmentDAOImpl();
        apartment = new Apartment("test","test");
    }

    @Test
    public void getApartments() {
        List<Apartment> apartments = apartmentDAO.getApartments();
        int expectedNum = 2;
        apartments.forEach(apt -> logger.info(apt.toString()));
        Assert.assertEquals(expectedNum, apartments.size());
    }

    @Test
    public void saveTest() {
        Assert.assertTrue(apartmentDAO.save(apartment));

    }

    @Test
    public void deleteTest() {
        Assert.assertTrue(apartmentDAO.delete(apartment));
    }

    @Test
    public void deleteApartmentByName(){
        Apartment apt = new Apartment("testdelte","byname");
        apartmentDAO.save(apt);
        Assert.assertTrue(apartmentDAO.deleteApartmentByName(apt.getName()));
    }

    @Test
    public void updateTest() {
        apartment = apartmentDAO.getApartmentByName("test");

        if (apartment != null) {
            logger.info(String.valueOf(apartment.getId()));
        }
        else {
            logger.info("apartment is null");
        }
        apartment.setAddress("test1");
        Assert.assertTrue(apartmentDAO.update(apartment));git a
    }


}
