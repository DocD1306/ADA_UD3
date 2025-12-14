package service;

import config.HibernateUtil;
import dao.SpaceDao;
import dao.VenueDao;
import dao.hibernateimpl.SpaceHibernateDao;
import dao.hibernateimpl.VenueHibernateDao;
import domain.Space;
import domain.Venue;
import dto.VenueIncomeBetweenDates;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class VenueService {

    private final SessionFactory sf;
    private final VenueDao venueDao;
    private final SpaceDao spaceDao;


    public VenueService(){
        this.sf = HibernateUtil.getSessionFactory();
        this.venueDao = new VenueHibernateDao();
        this.spaceDao = new SpaceHibernateDao();
    }

    public void deleteVenue(Long venueId){

        Transaction tx = null;
        try{
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();

            Venue venue = this.venueDao.findById(session, venueId);

            if(venue == null){
                throw new EntityNotFoundException("No existe ninguna Venue con id: " + venueId);
            }

            this.venueDao.delete(session, venue);

            tx.commit();
        } catch (PersistenceException e) {
            if (tx != null) tx.rollback();
            throw e;
        }

    }

    public void assignSpaceToVenue(Long spaceId, Long venueId){
        Transaction tx = null;
        try{
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();

            Venue venue = this.venueDao.findById(session, venueId);

            if(venue == null){
                throw new EntityNotFoundException("No existe ninguna Venue con id: " + venueId);
            }

            Space space = this.spaceDao.findById(session, spaceId);

            if(space == null){
                throw new EntityNotFoundException("No existe ningún Space con id: " + venueId);
            }

            List<Space> spaces = venue.getSpaces();

            if(!spaces.contains(space)) spaces.add(space);

            tx.commit();
        } catch (PersistenceException e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public List<VenueIncomeBetweenDates> venuesIncomeBetweenDates(LocalDateTime startTime, LocalDateTime endTime){

        Transaction tx = null;
        try{
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();

            List<VenueIncomeBetweenDates> venues = this.venueDao.incomeBetweenDates(session, startTime, endTime);

            tx.commit();

            return venues;

        } catch (PersistenceException e) {
            if (tx != null) tx.rollback();
            throw e;
        }

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
