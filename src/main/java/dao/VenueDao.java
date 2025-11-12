package dao;

import domain.Venue;
import org.hibernate.Session;

import java.util.List;

public interface VenueDao extends GenericDao<Venue, Long> {

    List<Venue> findByCity(Session session, String city);


}
