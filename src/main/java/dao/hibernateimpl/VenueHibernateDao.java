package dao.hibernateimpl;

import dao.VenueDao;
import domain.Venue;
import org.hibernate.Session;

import java.util.List;

public class VenueHibernateDao extends GenericHibernateDao<Venue, Long> implements VenueDao {

    public VenueHibernateDao(){
        super(Venue.class);
    }

    @Override
    public List<Venue> findByCity(Session session, String city) {
        return session.createNamedQuery("Venue.findByCity", Venue.class)
                .setParameter("city", city).getResultList();
    }


}
