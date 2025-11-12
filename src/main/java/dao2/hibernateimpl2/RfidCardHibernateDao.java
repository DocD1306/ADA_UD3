package dao2.hibernateimpl2;

import dao2.RfidCardDao;
import domain2.RfidCard;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class RfidCardHibernateDao extends GenericHibernateDao<RfidCard, Long> implements RfidCardDao {

    public RfidCardHibernateDao(){
        super(RfidCard.class);
    }

    @Override
    public RfidCard findByPlayerId(Session session, Long playerId) {
        String jpql = "SELECT r FROM RfidCard r WHERE r.player.id = :playerId";
        Query<RfidCard> query = session.createQuery(jpql, RfidCard.class);
        query.setParameter("playerId", playerId);
        return query.getSingleResultOrNull();
    }
}
