package dao2;

import domain2.RfidCard;
import org.hibernate.Session;

public interface RfidCardDao extends GenericDao<RfidCard, Long> {

    public RfidCard findByPlayerId(Session session, Long playerId);

}
