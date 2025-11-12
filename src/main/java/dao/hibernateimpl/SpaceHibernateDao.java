package dao.hibernateimpl;

import dao.SpaceDao;
import domain.Space;
import domain.User;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class SpaceHibernateDao extends GenericHibernateDao<Space, Long> implements SpaceDao {


    public SpaceHibernateDao(){
        super(Space.class);
    }

    @Override
    public Space updateSpaceWithUser(Space space, User u) {
        return null;
    }

    @Override
    public List<String> top5CitiesWithMoreSpaces(Session session) {
        String hql = "SELECT v.city FROM Space s JOIN s.venue v GROUP BY v.city order by count(v.city) desc";
        Query<String> query = session.createQuery(hql, String.class).setMaxResults(5);
        return query.getResultList();
    }

}
