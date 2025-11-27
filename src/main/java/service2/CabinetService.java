package service2;

import config.HibernateUtil;
import dao2.ArcadeDao;
import dao2.CabinetDao;
import dao2.GameDao;
import dao2.TagDao;
import dao2.hibernateimpl2.ArcadeHibernateDao;
import dao2.hibernateimpl2.CabinetHibernateDao;
import dao2.hibernateimpl2.GameHibernateDao;
import dao2.hibernateimpl2.TagHibernateDao;
import domain2.*;
import dto2.ActiveCabinetsByGenre;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CabinetService {

    private final SessionFactory sessionFactory;
    private final ArcadeDao arcadeDao;
    private final GameDao gameDao;
    private final TagDao tagDao;
    private final CabinetDao cabinetDao;

    public CabinetService() {
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.arcadeDao = new ArcadeHibernateDao();
        this.gameDao = new GameHibernateDao();
        this.tagDao = new TagHibernateDao();
        this.cabinetDao = new CabinetHibernateDao();
    }

    public List<Cabinet> findCabinetsWithNoGamesSince(LocalDateTime rangeDate){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            List<Cabinet> cabinets = this.cabinetDao.cabinetsWithoutGamesSince(session, rangeDate);

            tx.commit();

            return cabinets;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public List<ActiveCabinetsByGenre> findActiveCabinetsByGenre(String genre){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            List<ActiveCabinetsByGenre> cabinets = this.cabinetDao.activeCabinetsByGenre(session, genre);

            tx.commit();

            return cabinets;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }

    }

    public void updateCabinetStatus(Long cabinetId, Status status){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Cabinet cabinet = this.cabinetDao.findById(session, cabinetId);

            if(cabinet == null){
                throw new EntityNotFoundException("No existe ningún Cabinet con ID: " + cabinetId);
            }

            cabinet.setStatus(status);

            this.cabinetDao.update(session, cabinet);

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }

    }

    public Long createCabinet(String slug, LocalDate buildYear, Status status, Long gameId, Long arcadeId){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Arcade arcade = this.arcadeDao.findById(session, arcadeId);

            if(arcade == null){
                throw new EntityNotFoundException("No existe ningún Arcade con el ID: " + arcadeId);
            }

            Game game = this.gameDao.findById(session, gameId);

            if(game == null){
                throw new EntityNotFoundException("No existe ningún Game con el ID: " + gameId);
            }

            Cabinet cabinet = new Cabinet();
            cabinet.setSlug(slug);
            cabinet.setBulidYear(buildYear);
            cabinet.setStatus(status);
            cabinet.setGame(game);
            cabinet.setArcade(arcade);

            Long id = this.cabinetDao.create(session, cabinet);

            tx.commit();

            return id;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
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
            throw e;
        }

    }




    public void assignArcadeToCabinet(Long cabinetId, Long arcadeId){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Arcade arcade = this.arcadeDao.findById(session, arcadeId);

            if(arcade == null){
                throw new EntityNotFoundException("No existe ningún Arcade con el ID: " + arcadeId);
            }

            Cabinet cabinet = this.cabinetDao.findById(session, cabinetId);

            if(cabinet == null){
                throw new EntityNotFoundException("No existe ningún Cabinet con el ID: " + cabinetId);
            }

            cabinet.setArcade(arcade);

            this.cabinetDao.update(session, cabinet);

            tx.commit();
        }catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }

    }

    public void assignGameToCabinet(Long cabinetId, Long gameId){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Game game = this.gameDao.findById(session, gameId);

            if(game == null){
                throw new EntityNotFoundException("No existe ningún Game con el ID: " + gameId);
            }

            Cabinet cabinet = this.cabinetDao.findById(session, cabinetId);

            if(cabinet == null){
                throw new EntityNotFoundException("No existe ningún Cabinet con el ID: " + cabinetId);
            }

            cabinet.setGame(game);

            this.cabinetDao.update(session, cabinet);

            tx.commit();
        }catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }

    }

    public void addTagToCabinet(Long cabinetId, Long tagId){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Cabinet cabinet = this.cabinetDao.findById(session, cabinetId);

            if(cabinet == null){
                throw new EntityNotFoundException("No existe ningún Cabinet con el ID: " + cabinetId);
            }

            Tag tag = this.tagDao.findById(session, tagId);

            if(tag == null){
                throw new EntityNotFoundException("No existe ningún Tag con el ID: " + tagId);
            }

            List<Tag> tags = cabinet.getTags();

            tags.add(tag);

            cabinet.setTags(tags);

            this.cabinetDao.update(session, cabinet);

            tx.commit();
        }catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }

    }

    public void removeTagFromCabinet(Long cabinetId, Long tagId){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Cabinet cabinet = this.cabinetDao.findById(session, cabinetId);

            if(cabinet == null){
                throw new EntityNotFoundException("No existe ningún Cabinet con el ID: " + cabinetId);
            }

            List<Tag> tags = cabinet.getTags();

            Tag tag = this.tagDao.findById(session, tagId);

            if(tag == null){
                throw new EntityNotFoundException("No existe ningún Tag con el ID: " + tagId);
            }

            tags.remove(tag);

            cabinet.setTags(tags);

            this.cabinetDao.update(session, cabinet);

            tx.commit();
        }catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }

    }

}
