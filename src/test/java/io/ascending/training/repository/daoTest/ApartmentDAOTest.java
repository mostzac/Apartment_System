package io.ascending.training.repository.daoTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.Apartment;
import io.ascending.training.repository.impl.ApartmentDAOImpl;
import io.ascending.training.repository.interfaces.ApartmentDAO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class ApartmentDAOTest {
    @Autowired
    private ApartmentDAO apartmentDAO;
    private static Apartment apartment;
    @Autowired
    private Logger logger;
//    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void init() {
//        apartmentDAO = new ApartmentDAOImpl();
        apartment = new Apartment("test","test");
        Assert.assertTrue(apartmentDAO.save(apartment));
    }

    @Test
    public void getApartments() {
        List<Apartment> apartments = apartmentDAO.getApartments();
        int expectedNum = 1;
        apartments.forEach(apt -> logger.info(apt.toString()));
        Assert.assertEquals(expectedNum, apartments.size());
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
        Assert.assertTrue(apartmentDAO.update(apartment));
    }

    @After
    public void teardown() {
        Assert.assertTrue(apartmentDAO.deleteApartmentByName("test"));
    }

    @Test
    public void deleteApartmentByName(){
        Apartment apt = new Apartment("testdelte","byname");
        apartmentDAO.save(apt);
        Assert.assertTrue(apartmentDAO.deleteApartmentById(apt.getId()));
    }



}
