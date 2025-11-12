package service;

import config.HibernateUtil;
import dao.SpaceDao;
import dao.VenueDao;
import dao.hibernateimpl.SpaceHibernateDao;
import dao.hibernateimpl.VenueHibernateDao;
import domain.Space;
import domain.Venue;
import dto.SpaceNameVenueCity;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SpaceService {

    private final SessionFactory sf;
    private final SpaceDao spaceDao;
    private final VenueDao venueDao;


    public SpaceService() {
        this.sf = HibernateUtil.getSessionFactory();
        this.spaceDao = new SpaceHibernateDao();
        this.venueDao = new VenueHibernateDao();
    }

    public List<String> findTop5CitiesWithMoreSpaces(){
        Transaction tx = null;
        try{
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();


            List<String> cities = this.spaceDao.top5CitiesWithMoreSpaces(session);

            tx.commit();
            return cities;
        } catch (PersistenceException e) {
            if(tx != null){
                tx.rollback();
            }
            throw e;
        }
    }

    public Long createSpace(Space space, Long venueId){
        Transaction tx = null;
        try{
            Session session = sf.getCurrentSession();
            tx = session.beginTransaction();


            Venue sede = venueDao.findById(session, venueId);
            if(sede == null){
                throw new EntityNotFoundException("No existe la sede " + venueId);
            }

            space.setVenue(sede);

            Long id = spaceDao.create(session, space);

            tx.commit();
            return id;
        } catch (PersistenceException e) {
            if(tx != null){
                tx.rollback();
            }
            throw e;
        }
    }

    public List<SpaceNameVenueCity> getSpaceByCode(String code){

        if(!code.isBlank()){
            Transaction tx = null;
            try{
                Session session = sf.getCurrentSession();
                tx = session.beginTransaction();

                // HQL / JPQL

                List<Venue> venues = session.createNamedQuery("Venue.findByName", Venue.class)
                        .setParameter("name", "Elche")
                        .getResultList();

                // Nativas
                session.createNativeQuery("select * from venues v where v.name = :name", Venue.class);
                

                TypedQuery<SpaceNameVenueCity> query = session.createQuery(
                        "select s from Space s where s.hourlyPrice between :min and :max",
                        SpaceNameVenueCity.class);

                query.setParameter("codigo", code);

                List<SpaceNameVenueCity> rows = query.getResultList();

                tx.commit();
                return rows;
            } catch (PersistenceException e){
                if(tx != null) tx.rollback();
                throw e;
            }
        } else {
            throw new PersistenceException("Código inválido");
        }
    }


}
