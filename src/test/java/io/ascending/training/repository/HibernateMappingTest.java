package io.ascending.training.repository;

import io.ascending.training.model.Apartment;
import io.ascending.training.model.Resident;
import io.ascending.training.model.User;
import io.ascending.training.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HibernateMappingTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void mappingTest() {
        String hqlApartment = "FROM Apartment"; //from class
        String hqlUser = "FROM User";
        String hqlResident = "FROM Resident";
        List<Apartment> apartments = null;
        List<User> users = null;
        List<Resident> residents = null;

        try  { //the session will close after the try block, getCurrentSession() - if exist get it which will auto close when thread is over, if not create one
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            Query<Apartment> queryAp = session.createQuery(hqlApartment);
            Query<User> queryUser = session.createQuery(hqlUser);
            Query<Resident> queryRes = session.createQuery(hqlResident);
            apartments = queryAp.list();
            users = queryUser.list();
            residents = queryRes.list();
            transaction.commit();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        apartments.forEach(apt -> logger.info(apt.toString()));
        residents.forEach(resident -> logger.info(resident.toString()));
        users.forEach(user -> logger.info(user.toString()));

        Assert.assertNotNull(apartments);
        Assert.assertNotNull(residents);
        Assert.assertNotNull(users);
    }

}
