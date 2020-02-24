package io.ascending.training.UtilTest;

import io.ascending.training.model.Apartment;
import io.ascending.training.model.Package;
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
        String hqlPackage = "FROM Package";
        List<Apartment> apartments = null;
        List<User> users = null;
        List<Package> aPackages = null;

        try  { //the session will close after the try block, getCurrentSession() - if exist get it which will auto close when thread is over, if not create one
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            Transaction transaction = session.beginTransaction();
            Query<Apartment> queryAp = session.createQuery(hqlApartment);
            Query<User> queryUser = session.createQuery(hqlUser);
            Query<Package> queryPac = session.createQuery(hqlPackage);
            apartments = queryAp.list();
            users = queryUser.list();
            aPackages = queryPac.list();
            transaction.commit();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        apartments.forEach(apt -> logger.info(apt.toString()));
        aPackages.forEach(pack -> logger.info(pack.toString()));
        users.forEach(user -> logger.info(user.toString()));

        Assert.assertNotNull(apartments);
        Assert.assertNotNull(aPackages);
        Assert.assertNotNull(users);
    }

}
