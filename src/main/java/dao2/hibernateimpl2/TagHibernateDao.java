package dao2.hibernateimpl2;

import dao2.TagDao;
import domain2.Tag;

public class TagHibernateDao extends GenericHibernateDao<Tag, Long> implements TagDao {

    public TagHibernateDao(){
        super(Tag.class);
    }

}
