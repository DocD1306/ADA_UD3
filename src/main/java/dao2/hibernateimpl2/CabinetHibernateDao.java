package dao2.hibernateimpl2;

import dao2.CabinetDao;
import domain2.Cabinet;
import domain2.Match;
import dto2.ActiveCabinetsByGenre;
import jakarta.persistence.criteria.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class CabinetHibernateDao extends GenericHibernateDao<Cabinet, Long> implements CabinetDao {

    public CabinetHibernateDao(){
        super(Cabinet.class);
    }

    @Override
    public List<ActiveCabinetsByGenre> activeCabinetsByGenre(Session session, String genre) {
        String hql = """
        SELECT new dto2.ActiveCabinetsByGenre(c, g.genre) 
        FROM Cabinet c JOIN c.game g 
        WHERE lower(g.genre) = :genre AND c.status = 'ACTIVE'
        """;

        Query<ActiveCabinetsByGenre> query = session.createQuery(hql, ActiveCabinetsByGenre.class)
                .setParameter("genre", genre.toLowerCase());

        return query.getResultList();
    }

    @Override
    public List<Cabinet> cabinetsWithoutGamesSince(Session session, LocalDateTime rangeDate) {

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<Cabinet> criteriaQuery = cb.createQuery(Cabinet.class);
        Root<Cabinet> cabinetRoot = criteriaQuery.from(Cabinet.class);

        Subquery<Match> subquery = criteriaQuery.subquery(Match.class);
        Root<Match> matchRoot = subquery.from(Match.class);
        subquery.select(matchRoot)
                .where(
                        cb.and(
                                cb.equal(matchRoot.get("cabinet"), cabinetRoot),
                                cb.greaterThan(matchRoot.get("endAt"), rangeDate)
                        )
                );

        criteriaQuery.select(cabinetRoot)
                .where(cb.not(cb.exists(subquery)));

        Query<Cabinet> query = session.createQuery(criteriaQuery);

        return query.getResultList();

    }
}
