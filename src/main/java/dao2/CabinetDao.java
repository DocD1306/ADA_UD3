package dao2;

import domain2.Cabinet;
import dto2.ActiveCabinetsByGenre;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public interface CabinetDao extends GenericDao<Cabinet, Long> {

    List<ActiveCabinetsByGenre> activeCabinetsByGenre(Session session, String genre);

    List<Cabinet> cabinetsWithoutGamesSince(Session session, LocalDateTime rangeDate);

}
