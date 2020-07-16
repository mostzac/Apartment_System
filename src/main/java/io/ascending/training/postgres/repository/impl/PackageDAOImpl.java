package io.ascending.training.postgres.repository.impl;

import io.ascending.training.postgres.model.Package;
import io.ascending.training.postgres.repository.interfaces.PackageDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PackageDAOImpl implements PackageDAO {
    @Autowired
    private SessionFactory sessionFactory;
    
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean save(Package pack) {
        Transaction transaction = null;
        boolean isSuccess = true;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.save(pack);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        if (isSuccess) logger.debug(String.format("The package %s is saved", pack.toString()));
        return isSuccess;
    }

    @Override
    public boolean update(Package pack) {
        Transaction transaction = null;
        boolean isSuccess =  true;
        try{
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(pack);
            transaction.commit();
        } catch (Exception e) {
            isSuccess = false;
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }
        if (isSuccess) logger.debug(String.format("The package %s is updated", pack.toString()));
        return isSuccess;
    }

    @Override
    public boolean deletePackageByShipNumber(String shipNum) {
        String hql = "DELETE Package where shipNumber = :shipNumPara";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            Query<Package> query = session.createQuery(hql);
            query.setParameter("shipNumPara", shipNum);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The package %s is deleted", shipNum));
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public boolean deletePackageById(long id) {
        String hql = "DELETE Package where id = :idPara";
        int delectedCount = 0;
        Transaction transaction = null;

        try {
            Session session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            Query<Package> query = session.createQuery(hql);
            query.setParameter("idPara", id);
            delectedCount = query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error(e.getMessage());
        }

        logger.debug(String.format("The package %s is deleted", id));
        return delectedCount >= 1 ? true : false;
    }

    @Override
    public List<Package> getPackages() {
        String hql = "FROM Package p join fetch p.user";

        try (Session session = sessionFactory.openSession()) {
            Query<Package> query = session.createQuery(hql);
            return query.list();
        }
    }

    @Override
    public Package getPackageByShipNumber(String packShipNum) {
        //if(apartName.equals(null)) return null;
        String hql = "FROM Package p join fetch p.user where p.shipNumber = :packShipNumPara";
        try (Session session = sessionFactory.openSession()){
            Query<Package> query = session.createQuery(hql);
            query.setParameter("packShipNumPara",packShipNum);
            return query.uniqueResult();
        }
    }

    @Override
    public Package getPackageById(long id) {
        //if(apartName.equals(null)) return null;
        String hql = "FROM Package p join fetch p.user where p.id = :id";
        try (Session session = sessionFactory.openSession()){
            Query<Package> query = session.createQuery(hql);
            query.setParameter("id",id);
            return query.uniqueResult();
        }
    }
}
