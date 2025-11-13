package dao;

import domain.Booking;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingDao extends GenericDao<Booking, Long> {

    List<Booking> findConfirmedBookingsByVenueAndRange(Session session, String venueName, LocalDateTime startTime, LocalDateTime endTime);

}
