package dao.hibernateimpl;

import dao.AccessCardDao;
import domain.AccessCard;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class AccessCardHibernateDao extends GenericHibernateDao<AccessCard, Long> implements AccessCardDao {

    public AccessCardHibernateDao() {
        super(AccessCard.class);
    }

    public AccessCard findByUserId(Session session, Long userId){
        String jpql = "SELECT a FROM AccessCard a WHERE a.user.id = :userId";
        Query<AccessCard> query = session.createQuery(jpql, AccessCard.class);
        try{
            return query.setParameter("userId", userId).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public AccessCard findByCardUid(Session session, String cardUid){
        String jpql = "SELECT a FROM AccessCard a WHERE a.cardUid = :cardUid";
        Query<AccessCard> query = session.createQuery(jpql, AccessCard.class);
        try{
            return query.setParameter("cardUid", cardUid).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
