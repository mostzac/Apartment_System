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
import java.util.stream.Collectors;

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
        String userAccount = user.getAccount();
        String hql = "DELETE User where account = :userAccountPara";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery(hql);
            query.setParameter("userAccountPara", userAccount);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The user &s is deleted"), userAccount);
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public boolean deleteUserByAccount(String userAccount) {
        String hql = "DELETE User where account = :userAccountPara";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery(hql);
            query.setParameter("userAccountPara", userAccount);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The user &s is deleted"), userAccount);
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public List<User> getUsers() {
//        String hql = "FROM User";
        String hql = "FROM User as u left join fetch u.packages";
        try (Session session = HibernateUtil.getSessionFactory().openSession()) { //openSession() in try block dont need to close, 
            Query<User> query = session.createQuery(hql);
            return query.list().stream().distinct().collect(Collectors.toList());
        }
    }

    @Override
    public User getUserByAccount(String userAccount) {
        //if(apartName.equals(null)) return null;
//        String hql = "FROM User where account = :account";
        String hql = "FROM User as u left join fetch u.packages where account = :account";
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("account",userAccount);
            return query.uniqueResult();
        }
    }

}
