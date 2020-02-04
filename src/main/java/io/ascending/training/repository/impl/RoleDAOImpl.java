package io.ascending.training.repository.impl;

import io.ascending.training.model.Role;
import io.ascending.training.repository.interfaces.RoleDAO;
import io.ascending.training.util.HibernateUtil;
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
public class RoleDAOImpl implements RoleDAO {
    @Autowired
    private SessionFactory sessionFactory;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean save(Role role) {
        Transaction transaction = null;
        boolean isSuccess = true;

        try{
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("The role %s is saved", role.toString()));
        return isSuccess;
    }

    @Override
    public boolean update(Role role) {
        Transaction transaction = null;
        boolean isSuccess = true;

        try{
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(role);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("The role %s is updated", role.toString()));
        return isSuccess;
    }

    @Override
    public boolean deleteRoleByName(String roleName) {
        String hql = "DELETE Role where name = :roleName";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            Query<Role> query = session.createQuery(hql);
            query.setParameter("roleName", roleName);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The role %s is deleted", roleName));
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public boolean deleteRoleById(long id) {
        String hql = "DELETE Role where id = :id";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            Query<Role> query = session.createQuery(hql);
            query.setParameter("id", id);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The role %s is deleted", id));
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public Role getRoleByName(String roleName) {
        String hql = "FROM Role as r left join fetch r.users where r.name= :roleName";
//        String hql = "FROM Role where name= :roleName";
        try (Session session = sessionFactory.openSession()){
            Query<Role> query = session.createQuery(hql);
            query.setParameter("roleName",roleName);
            return query.uniqueResult();
        }
    }

    @Override
    public Role getRoleById(long id) {
        String hql = "FROM Role as r left join fetch r.users where r.id= :roleid";
//        String hql = "FROM Role where name= :roleName";
        try (Session session = sessionFactory.openSession()){
            Query<Role> query = session.createQuery(hql);
            query.setParameter("roleid",id);
            return query.uniqueResult();
        }
    }

    @Override
    public List<Role> getRoles() {
//        String hql = "FROM Role";
        String hql = "FROM Role as r left join fetch r.users";
        try (Session session = sessionFactory.openSession()) { //openSession() in try block dont need to close,
            Query<Role> query = session.createQuery(hql);
            return query.list().stream().distinct().collect(Collectors.toList());
        }
    }
}
