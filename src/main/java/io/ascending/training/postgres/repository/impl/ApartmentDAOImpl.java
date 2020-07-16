package io.ascending.training.postgres.repository.impl;

import io.ascending.training.postgres.model.Apartment;

import io.ascending.training.postgres.repository.interfaces.ApartmentDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ApartmentDAOImpl implements ApartmentDAO {
    @Autowired
    private SessionFactory sessionFactory;

//    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Logger logger;
    @Override
    public boolean save(Apartment apartment) {
        Transaction transaction = null;
        boolean isSuccess = true;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.save(apartment);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("The apartment %s is saved", apartment.toString()));
        return isSuccess;
    }

    @Override
    public boolean update(Apartment apartment) {
        Transaction transaction = null;
        boolean isSuccess = true;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(apartment);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("The apartment %s is updated", apartment.toString()));
        return isSuccess;
    }

//    @Override
//    public boolean delete(Apartment apartment) {
//        String apartName = apartment.getName();
//        String hql = "DELETE Apartment where name = :apartNamePara";
//        int delectedCount = 0;
//        Transaction transaction = null;
//
//        try {
//            Session session = sessionFactory.getCurrentSession();
//            transaction = session.beginTransaction();
//            Query<Apartment> query = session.createQuery(hql);
//            query.setParameter("apartNamePara", apartName);
//            delectedCount = query.executeUpdate();
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            logger.error(e.getMessage());
//        }
//
//        logger.debug(String.format("The apartment %s is deleted", apartName));
//        return delectedCount >= 1 ? true : false;
//    }

    @Override
    public boolean deleteApartmentByName(String apartName) {
        String hql = "DELETE Apartment where name = :apartNamePara";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            Query<Apartment> query = session.createQuery(hql);
            query.setParameter("apartNamePara", apartName);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The apartment %s is deleted", apartName));
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public boolean deleteApartmentById(long id) {
        String hql ="DELETE Apartment where id = :idPara";
        int delectedCount = 0;
        Transaction transaction = null;

        try{
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            Query<Apartment> query = session.createQuery(hql);
            query.setParameter("idPara",id);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }
        logger.debug(String.format("The apartment %s is deleted", id));
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public List<Apartment> getApartments() {
        String hql = "FROM Apartment";
//        String hql = "FROM Apartment as apt left join fetch apt.users";

        try (Session session = sessionFactory.openSession()) {
            Query<Apartment> query = session.createQuery(hql);
            return query.list().stream().distinct().collect(Collectors.toList());//to make the table distinct
        }
    }


    @Override
    public Apartment getApartmentByName(String apartName) {
        //if(apartName.equals(null)) return null;
//        String hql = "FROM Apartment as apt left join fetch apt.users where name = :name";
        String hql = "FROM Apartment as apt left join fetch apt.users where apt.name = :name";
        try (Session session = sessionFactory.openSession()){
            Query<Apartment> query = session.createQuery(hql);
            query.setParameter("name",apartName);
            return query.uniqueResult();
        }
    }

    @Override
    public Apartment getApartmentById(long id) {

        String hql = "FROM Apartment as apt left join fetch apt.users where apt.id = :id";
        try (Session session = sessionFactory.openSession()){
            Query<Apartment> query = session.createQuery(hql);
            query.setParameter("id",id);
            return query.uniqueResult();
        }
    }

}
