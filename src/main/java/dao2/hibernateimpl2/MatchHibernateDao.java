package dao2.hibernateimpl2;

import dao2.MatchDao;
import domain2.Match;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class MatchHibernateDao extends GenericHibernateDao<Match, Long> implements MatchDao {

    public MatchHibernateDao(){
        super(Match.class);
    }


    @Override
    public List<Match> listAllMatches(Session session) {
        String jpql = "SELECT m FROM Match m";
        Query<Match> query = session.createQuery(jpql, Match.class);
        return query.getResultList();
    }
}
