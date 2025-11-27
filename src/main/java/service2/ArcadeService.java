package service2;

import config.HibernateUtil;
import dao2.ArcadeDao;
import dao2.hibernateimpl2.ArcadeHibernateDao;
import domain2.Arcade;
import dto2.ArcadeEstimatedIncome;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ArcadeService {


    private final SessionFactory sessionFactory;
    private final ArcadeDao arcadeDao;

    public ArcadeService(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.arcadeDao = new ArcadeHibernateDao();
    }

    public List<ArcadeEstimatedIncome> estimatedIncomesByArcade(){
        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            List<ArcadeEstimatedIncome> arcades = this.arcadeDao.estimatedIncomeByArcade(session);

            tx.commit();
            return arcades;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Arcade findByName(String name){
        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Arcade arcade = this.arcadeDao.findByName(session, name);
            tx.commit();

            return arcade;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }

    }

    public Long createArcade(String name, String address){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Arcade arcade = new Arcade();
            arcade.setName(name);
            arcade.setAddress(address);

            Long id = this.arcadeDao.create(session, arcade);

            tx.commit();

            return id;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }

    }

}
