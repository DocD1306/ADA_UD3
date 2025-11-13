package dao;

import domain.Venue;
import dto.VenueIncomeBetweenDates;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public interface VenueDao extends GenericDao<Venue, Long> {

    List<Venue> findByCity(Session session, String city);

    List<VenueIncomeBetweenDates> incomeBetweenDates(Session session, LocalDateTime startTime, LocalDateTime endtime);

}
