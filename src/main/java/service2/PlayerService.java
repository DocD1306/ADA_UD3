package service2;

import config.HibernateUtil;
import dao2.PlayerDao;
import dao2.RfidCardDao;
import dao2.hibernateimpl2.PlayerHibernateDao;
import dao2.hibernateimpl2.RfidCardHibernateDao;
import domain2.Player;
import domain2.RfidCard;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public class PlayerService {

    private final SessionFactory sessionFactory;
    private final PlayerDao playerDao;
    private final RfidCardDao rfidCardDao;

    public PlayerService(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.playerDao = new PlayerHibernateDao();
        this.rfidCardDao = new RfidCardHibernateDao();
    }

    public List<Player> findInactivePlayersWithRecentMatches(LocalDateTime filterDate){
        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            List<Player> players = this.playerDao.inactivePlayersWithRecentGames(session, filterDate);

            tx.commit();

            return players;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Long createPlayer(String nickname, String email){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Player player = new Player();
            player.setNickname(nickname);
            player.setEmail(email);
            player.setCreatedAt(LocalDateTime.now());

            Long id = this.playerDao.create(session, player);

            tx.commit();

            return id;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }

    }

    public Long emitRfidCard(Long playerId, String uId){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Player player = this.playerDao.findById(session, playerId);

            if(player == null){
                throw new EntityNotFoundException("No exite ningún Player con el ID: " + playerId);
            }

            RfidCard rfidCard = this.rfidCardDao.findByPlayerId(session, playerId);

            if(rfidCard != null){
                throw new EntityExistsException("Ya existe una RfidCard para el Player con ID: " + playerId);
            }

            rfidCard = new RfidCard();

            rfidCard.setPlayer(player);
            rfidCard.setUId(uId);
            rfidCard.setActive(true);
            rfidCard.setIssuedAt(LocalDateTime.now());

            Long id = this.rfidCardDao.create(session, rfidCard);

            tx.commit();

            return id;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void activateDeactivateRfidCard(Long playerId, boolean activated){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();


            RfidCard rfidCard = this.rfidCardDao.findByPlayerId(session, playerId);

            if(rfidCard == null){
                throw new EntityNotFoundException("No exite ninguna RfidCard para el jugador con ID: " + playerId);
            }

            rfidCard.setActive(activated);

            this.rfidCardDao.update(session, rfidCard);

            tx.commit();

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }

    }

    public void updateUid(Long playerId, String newUId){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();


            RfidCard rfidCard = this.rfidCardDao.findByPlayerId(session, playerId);

            if(rfidCard == null){
                throw new EntityNotFoundException("No exite ninguna RfidCard para el jugador con ID: " + playerId);
            }

            rfidCard.setUId(newUId);

            this.rfidCardDao.update(session, rfidCard);

            tx.commit();

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public void retireRfidCard(Long playerId){
        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();


            RfidCard rfidCard = this.rfidCardDao.findByPlayerId(session, playerId);

            if(rfidCard == null){
                throw new EntityNotFoundException("No exite ninguna RfidCard para el jugador con ID: " + playerId);
            }

            this.rfidCardDao.delete(session, rfidCard);

            tx.commit();

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }
    }

}
