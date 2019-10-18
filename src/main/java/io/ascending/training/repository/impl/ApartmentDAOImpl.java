package io.ascending.training.repository.impl;

import io.ascending.training.model.Apartment;
import io.ascending.training.repository.interfaces.ApartmentDAO;
import io.ascending.training.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ApartmentDAOImpl implements ApartmentDAO {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean save(Apartment apartment) {
        Transaction transaction = null;
        boolean isSuccess = true;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(apartment);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("The apartment &s is saved"), apartment.toString());
        return isSuccess;
    }

    @Override
    public boolean update(Apartment apartment) {
        Transaction transaction = null;
        boolean isSuccess = true;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(apartment);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("The apartment &s is updated"), apartment.toString());
        return isSuccess;
    }

    @Override
    public boolean delete(Apartment apartment) {
        String apartName = apartment.getName();
        String hql = "DELETE Apartment where name = :apartNamePara";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Query<Apartment> query = session.createQuery(hql);
            query.setParameter("apartNamePara", apartName);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The apartment &s is deleted"), apartName);
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public boolean deleteApartmentByName(String apartName) {
        String hql = "DELETE Apartment where name = :apartNamePara";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Query<Apartment> query = session.createQuery(hql);
            query.setParameter("apartNamePara", apartName);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The apartment &s is deleted"), apartName);
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public List<Apartment> getApartments() {
        String hql = "FROM Apartment";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Apartment> query = session.createQuery(hql);
            return query.list();
        }
    }

    @Override
    public Apartment getApartmentByName(String apartName) {
        //if(apartName.equals(null)) return null;
        String hql = "FROM Apartment where name = :name";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Apartment> query = session.createQuery(hql);
            query.setParameter("name",apartName);
            return query.uniqueResult();
        }
    }
}
