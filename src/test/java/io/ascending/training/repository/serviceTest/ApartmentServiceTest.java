package io.ascending.training.repository.serviceTest;

import io.ascending.training.init.ApplicationBoot;
import io.ascending.training.model.postgresModel.Apartment;
import io.ascending.training.service.postgres.ApartmentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationBoot.class)
public class ApartmentServiceTest {
    @Autowired
    private ApartmentService apartmentService;
    private Apartment apartment;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Before
    public void init(){
        apartment = new Apartment("test","test");
    }

    @Test
    public void saveAndDeleteTest(){
        Assert.assertTrue(apartmentService.save(apartment));
        Assert.assertTrue(apartmentService.deleteApartmentByName(apartment.getName()));
    }

    @Test
    public void Test(){
        apartmentService.save(apartment);
        List<Apartment> apartments = apartmentService.getApartments();
        apartments.forEach(apt -> logger.info(apt.toString()));
        apartment.setName("updateTest");
        apartmentService.update(apartment);
        Apartment apt = apartmentService.getApartmentByName("updateTest");
        logger.info(apt.toString());
        Assert.assertTrue(apartmentService.deleteApartmentByName("updateTest"));
    }

    @Test
    public void TestId(){
        apartmentService.save(apartment);
        apartment = apartmentService.getApartmentById(1);
        apartment.toString();
        apartment = apartmentService.getApartmentByName("test");
        Apartment apt = apartmentService.getApartmentById(apartment.getId());
        if(apt.getId()==apartment.getId())
            apartmentService.deleteApartmentById(apt.getId());

    }

}
