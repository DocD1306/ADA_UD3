package service;

import config.HibernateUtil;
import dao.AccessCardDao;
import dao.UserDao;
import dao.hibernateimpl.AccessCardHibernateDao;
import dao.hibernateimpl.UserHibernateDao;
import domain.AccessCard;
import domain.User;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;

public class UserService {

    private final SessionFactory sessionFactory;
    private final AccessCardDao accessCardDao;
    private final UserDao userDao;

    public UserService(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.accessCardDao = new AccessCardHibernateDao();
        this.userDao = new UserHibernateDao();
    }

    public Long createUser(String email, String fullName){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            User user = new User();
            user.setEmail(email);
            user.setFullName(fullName);
            user.setCreatedAt(LocalDateTime.now());

            Long id = this.userDao.create(session, user);

            user = this.userDao.findById(session, id);

            tx.commit();

            return id;

        } catch (RuntimeException e) {
            if(tx != null) tx.rollback();
            throw new RuntimeException(e);
        }
    }

    public Long assignAccesCard(Long userId, String uid){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            User user = this.userDao.findById(session, userId);

            if (user == null){
                throw new EntityNotFoundException("No existe ningún usuario con ID: " + userId);
            }

            AccessCard accessCard = new AccessCard();
            accessCard.setCardUid(uid);
            accessCard.setUser(user);
            accessCard.setActive(true);
            accessCard.setIssuedAt(LocalDateTime.now());

            Long id = this.accessCardDao.create(session, accessCard);

            user.setAccessCard(accessCard);

            tx.commit();

            return id;

        } catch (RuntimeException e) {
            if(tx != null) tx.rollback();
            throw new RuntimeException(e);
        }


    }


    public Long updateAcessCard(Long userId, boolean active, String newUid){

        Transaction tx = null;

        try {
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            User user = this.userDao.findById(session, userId);

            if (user == null){
                throw new EntityNotFoundException("No existe ningún usuario con ID: " + userId);
            }

            AccessCard accessCard = this.accessCardDao.findByCardUid(session, newUid);

            if(accessCard != null){
                throw new EntityExistsException("Ya exite un AccessCard con el uid: " + newUid);
            }

            accessCard = this.accessCardDao.findByUserId(session, userId);

            if (accessCard == null){
                throw new EntityNotFoundException("No existe ninguna AccessCard para el usuario con ID: " + userId);
            }

            accessCard.setActive(active);
            accessCard.setCardUid(newUid);

            user.setAccessCard(accessCard);

            this.accessCardDao.update(session, accessCard);

            tx.commit();

            return accessCard.getId();

        } catch (RuntimeException e) {
            if(tx != null) tx.rollback();
            throw new RuntimeException(e);
        }

    }

    public boolean removeAccessCard(Long userId){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();

            tx = session.beginTransaction();

            User user = this.userDao.findById(session, userId);

            if(user == null){
                throw new EntityNotFoundException("No existe ningún usuario con el ID: " + userId);
            }

            AccessCard accessCard = user.getAccessCard();

            if(accessCard == null){
                throw new EntityNotFoundException("El usuario no tiene una AccessCard asignada");
            }

            this.accessCardDao.delete(session, accessCard);

            user.setAccessCard(null);

            tx.commit();

            return true;

        } catch (RuntimeException e){
            if(tx != null) tx.rollback();
            throw new RuntimeException(e);
        }

    }


}
