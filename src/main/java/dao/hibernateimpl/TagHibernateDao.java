package dao.hibernateimpl;

import dao.TagDao;
import domain.Tag;
import dto.TagNumberTimesUsed;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class TagHibernateDao extends GenericHibernateDao<Tag, Long> implements TagDao {

    public TagHibernateDao(){
        super(Tag.class);
    }

    @Override
    public List<TagNumberTimesUsed> mostUsedTags(Session session) {
        String hql = "SELECT t, count(s) FROM Space s RIGHT JOIN s.tags t GROUP BY t ORDER BY count(s) desc";

        Query<TagNumberTimesUsed> query = session.createQuery(hql, TagNumberTimesUsed.class);

        return query.getResultList();
    }

    @Override
    public List<Tag> tagsWhereNameStartsLike(Session session, String starts) {
        String hql = "SELECT t FROM Tag t WHERE lower(t.name) LIKE :starts";

        Query<Tag> query = session.createQuery(hql, Tag.class).setParameter("starts", starts.toLowerCase() + "%");

        return query.getResultList();
    }
}
