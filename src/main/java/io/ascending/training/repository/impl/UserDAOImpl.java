package io.ascending.training.repository.impl;

import io.ascending.training.model.User;
import io.ascending.training.repository.interfaces.UserDAO;
import io.ascending.training.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDAOImpl implements UserDAO {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean save(User user) {
        Transaction transaction = null;
        boolean isSuccess = true;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("The user &s is saved"), user.toString());
        return isSuccess;
    }

    @Override
    public boolean update(User user) {
        Transaction transaction = null;
        boolean isSuccess =  true;
        try{
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }
        if (isSuccess) logger.debug(String.format("The user &s is updated",user.toString()));
        return isSuccess;
    }

    @Override
    public boolean delete(User user) {
        String userName = user.getName();
        String hql = "DELETE User where name = :userNamePara";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery(hql);
            query.setParameter("userNamePara", userName);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The user &s is deleted"), userName);
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public boolean deleteUserByName(String userName) {
        String hql = "DELETE User where name = :userNamePara";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery(hql);
            query.setParameter("userNamePara", userName);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The user &s is deleted"), userName);
        return delectedCount >= 1 ? true : false;
    }


    @Override
    public List<User> getUsers() {
        String hql = "FROM User";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) { //openSession() in try block dont need to close, 
            Query<User> query = session.createQuery(hql);
            return query.list();
        }
    }

    @Override
    public User getUserByName(String userName) {
        //if(apartName.equals(null)) return null;
        String hql = "FROM User where name = :name";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("name",userName);
            return query.uniqueResult();
        }
    }
}
