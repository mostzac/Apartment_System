package io.ascending.training.postgres.repository.impl;

import io.ascending.training.postgres.model.User;
import io.ascending.training.postgres.repository.interfaces.UserDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    private SessionFactory sessionFactory;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean save(User user) {
        Transaction transaction = null;
        boolean isSuccess = true;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("The user %s is saved", user.toString()));
        return isSuccess;
    }

    @Override
    public boolean update(User user) {
        Transaction transaction = null;
        boolean isSuccess =  true;
        try{
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(user);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }
        if (isSuccess) logger.debug(String.format("The user %s is updated",user.toString()));
        return isSuccess;
    }
//
//    @Override
//    public boolean delete(User user) {
//        String userAccount = user.getAccount();
//        String hql = "DELETE User where account = :userAccountPara";
//        int delectedCount = 0;
//        Transaction transaction = null;
//
//        try {
//            Session session = sessionFactory.getCurrentSession();
//            transaction = session.beginTransaction();
//            Query<User> query = session.createQuery(hql);
//            query.setParameter("userAccountPara", userAccount);
//            delectedCount = query.executeUpdate();
//            transaction.commit();
//        } catch (Exception e) {
//            if (transaction != null) transaction.rollback();
//            logger.error(e.getMessage());
//        }
//
//        logger.debug(String.format("The user %s is deleted", userAccount));
//        return delectedCount >= 1 ? true : false;
//    }

    @Override
    public boolean deleteUserByAccount(String userAccount) {
        String hql = "DELETE User where account = :userAccountPara";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery(hql);
            query.setParameter("userAccountPara", userAccount);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The user %s is deleted", userAccount));
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public boolean deleteUserById(long id) {
        String hql = "DELETE User where id = :uid";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            Query<User> query = session.createQuery(hql);
            query.setParameter("uid", id);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The user %s is deleted", id));
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public List<User> getUsers() {
        String hql = "FROM User";
//        String hql = "FROM User as u left join fetch u.packages";
        try (Session session = sessionFactory.openSession()) { //openSession() in try block dont need to close,
            Query<User> query = session.createQuery(hql);
            return query.list().stream().distinct().collect(Collectors.toList());
        }
    }

    @Override
    public User getUserByAccount(String userAccount) {
        //if(apartName.equals(null)) return null;
//        String hql = "FROM User where account = :account";
        String hql = "FROM User as u left join fetch u.packages where u.account = :account";
        try (Session session = sessionFactory.openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("account",userAccount);
            return query.uniqueResult();
        }
    }

    @Override
    public User getUserById(long id) {
        String hql = "FROM User as u left join fetch u.packages where u.id = :id";
        try (Session session = sessionFactory.openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("id",id);
            return query.uniqueResult();
        }
    }

    @Override
    public User getUserByCredential(String userAccount, String password) {
        String hql = "FROM User as u left join fetch u.packages where u.account = :account and u.password = :password";
        try (Session session = sessionFactory.openSession()){
            Query<User> query = session.createQuery(hql);
            query.setParameter("account",userAccount);
            query.setParameter("password",password);
            return query.uniqueResult();
        }
    }
}
