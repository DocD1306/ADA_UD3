package dao;

import domain.AccessCard;
import org.hibernate.Session;

public interface AccessCardDao extends GenericDao<AccessCard, Long>{

    AccessCard findByUserId(Session session, Long userId);

    AccessCard findByCardUid(Session session, String userId);

}
