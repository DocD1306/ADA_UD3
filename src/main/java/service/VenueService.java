package service;

import config.HibernateUtil;
import dao.VenueDao;
import dao.hibernateimpl.VenueHibernateDao;
import domain.Venue;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class VenueService {

    private final SessionFactory sf;
    private final VenueDao venueDao;


    public VenueService(){
        this.sf = HibernateUtil.getSessionFactory();
        this.venueDao = new VenueHibernateDao();
    }

    public List<Venue> findVenuesByCity(String city){

        Transaction tx = null;
        try{
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();

            List<Venue> venues = this.venueDao.findByCity(session, city);

            tx.commit();

            return venues;

        } catch (PersistenceException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Long createVenue(String address, String city, String name){

        Transaction tx = null;
        try{
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();

            Venue venue = new Venue();
            venue.setAddress(address);
            venue.setCity(city);
            venue.setCreatedAt(LocalDateTime.now());
            venue.setName(name);

            Long id = this.venueDao.create(session, venue);

            tx.commit();

            return id;
        } catch (PersistenceException e) {
            if(tx != null){
                tx.rollback();
            }
            throw e;
        }
    }

}
