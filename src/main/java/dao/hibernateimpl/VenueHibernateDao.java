package dao.hibernateimpl;

import dao.VenueDao;
import domain.Venue;
import dto.VenueIncomeBetweenDates;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
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

    @Override
    public List<VenueIncomeBetweenDates> incomeBetweenDates(Session session, LocalDateTime startTime, LocalDateTime endtime) {

        String hql = """
        SELECT new dto.VenueIncomeBetweenDates(v, sum(b.totalPrice)) 
        FROM Venue v JOIN Space s on s.venue = v JOIN Booking b on b.space = s 
        WHERE b.startTime BETWEEN :startTime AND :endTime AND b.endTime BETWEEN :startTime and :endTime GROUP BY v""";

        Query<VenueIncomeBetweenDates> query = session.createQuery(hql, VenueIncomeBetweenDates.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endtime);

        return query.getResultList();
    }
}
