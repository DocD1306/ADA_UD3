package service;

import config.HibernateUtil;
import dao.SpaceDao;
import dao.TagDao;
import dao.hibernateimpl.SpaceHibernateDao;
import dao.hibernateimpl.TagHibernateDao;
import domain.Space;
import domain.Tag;
import dto.TagNumberTimesUsed;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TagService {

    private final SessionFactory sessionFactory;
    private final TagDao tagDao;
    private final SpaceDao spaceDao;

    public TagService(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.tagDao = new TagHibernateDao();
        this.spaceDao = new SpaceHibernateDao();
    }

    public List<Tag> tagsWhereNameStartsLike(String starts){

        Transaction tx = null;

        try{

            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            List<Tag> tags = this.tagDao.tagsWhereNameStartsLike(session, starts);

            tx.commit();

            return tags;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw new RuntimeException(e);
        }
    }

    public List<TagNumberTimesUsed> mostUsedTags(){

        Transaction tx = null;

        try{

            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            List<TagNumberTimesUsed> tags = this.tagDao.mostUsedTags(session);

            tx.commit();

            return tags;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw new RuntimeException(e);
        }
    }

    public Long createTag(String name){

        Transaction tx = null;

        try{

            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Tag tag = new Tag();
            tag.setName(name);

            Long id = this.tagDao.create(session, tag);

            tx.commit();

            return id;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw new RuntimeException(e);
        }

    }

    public boolean addTagToSpace(Long spaceId, Long tagId){

        Transaction tx = null;

        try{

            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Tag tag = this.tagDao.findById(session, tagId);

            if(tag == null){
                throw new EntityNotFoundException("No existe ninguna Tag con el ID: " + tagId);
            }

            Space space = this.spaceDao.findById(session, spaceId);

            if(space == null){
                throw new EntityNotFoundException("No existe ningún Space con el ID: " + spaceId);
            }

            List<Tag> tags = space.getTags();
            if(!tags.contains(tag)){
                tags.add(tag);
            }

            space.setTags(tags);

            this.spaceDao.update(session, space);

            tx.commit();

            return true;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw new RuntimeException(e);
        }

    }

    public boolean removeTagFromSpace(Long spaceId, Long tagId){

        Transaction tx = null;

        try{

            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Tag tag = this.tagDao.findById(session, tagId);

            if(tag == null){
                throw new EntityNotFoundException("No existe ninguna Tag con el ID: " + tagId);
            }

            Space space = this.spaceDao.findById(session, spaceId);

            if(space == null){
                throw new EntityNotFoundException("No existe ningún Space con el ID: " + spaceId);
            }

            List<Tag> tags = space.getTags();
            tags.remove(tag);

            space.setTags(tags);

            this.spaceDao.update(session, space);

            tx.commit();

            return true;
        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw new RuntimeException(e);
        }

    }

}
