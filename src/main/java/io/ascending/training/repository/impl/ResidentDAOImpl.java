package io.ascending.training.repository.impl;

import io.ascending.training.model.Apartment;
import io.ascending.training.model.Resident;
import io.ascending.training.repository.interfaces.ResidentDAO;
import io.ascending.training.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ResidentDAOImpl implements ResidentDAO {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean save(Resident resident) {
        Transaction transaction = null;
        boolean isSuccess = true;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(resident);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("The resident &s is saved"), resident.toString());
        return isSuccess;
    }

    @Override
    public boolean update(Resident resident) {
        Transaction transaction = null;
        boolean isSuccess =  true;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(resident);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }
        if (isSuccess) logger.debug(String.format("The resident &s is updated",resident.toString()));
        return isSuccess;
    }

    @Override
    public boolean delete(Resident resident) {
        String residentName = resident.getName();
        String hql = "DELETE Resident where name = :residentNamePara";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Query<Apartment> query = session.createQuery(hql);
            query.setParameter("residentNamePara", residentName);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The resident &s is deleted"), residentName);
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public boolean deleteResidentByName(String residentName) {
        String hql = "DELETE Resident where name = :residentNamePara";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Query<Apartment> query = session.createQuery(hql);
            query.setParameter("residentNamePara", residentName);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The resident &s is deleted"), residentName);
        return delectedCount >= 1 ? true : false;
    }


    @Override
    public List<Resident> getResidents() {
        String hql = "FROM Resident";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Resident> query = session.createQuery(hql);
            return query.list();
        }
    }

    @Override
    public Resident getResidentByName(String residentName) {
        //if(apartName.equals(null)) return null;
        String hql = "FROM Resident where name = :name";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<Resident> query = session.createQuery(hql);
            query.setParameter("name",residentName);
            return query.uniqueResult();
        }
    }
}
