package dao.hibernateimpl;

import dao.TagDao;
import domain.Tag;

public class TagHibernateDao extends GenericHibernateDao<Tag, Long> implements TagDao {

    public TagHibernateDao(){
        super(Tag.class);
    }

}
