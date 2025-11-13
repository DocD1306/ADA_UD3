package dao.hibernateimpl;

import dao.BookingDao;
import domain.Booking;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class BookingHibernateDao extends GenericHibernateDao<Booking, Long> implements BookingDao {

    public BookingHibernateDao(){
        super(Booking.class);
    }

    @Override
    public List<Booking> findConfirmedBookingsByVenueAndRange(Session session, String venueName, LocalDateTime startTime, LocalDateTime endTime) {

        Query<Booking> query = session.createNamedQuery("Booking.confirmedBookingsByVenueAndRange", Booking.class)
                .setParameter("venueName", venueName)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime);

        return query.getResultList();

    }
}
