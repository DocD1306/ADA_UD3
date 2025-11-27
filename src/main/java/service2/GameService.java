package service2;

import config.HibernateUtil;
import dao2.GameDao;
import dao2.hibernateimpl2.GameHibernateDao;
import domain2.Game;
import dto2.GameNumberTimesPlayed;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class GameService {

    private final SessionFactory sessionFactory;
    private final GameDao gameDao;

    public GameService(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.gameDao = new GameHibernateDao();
    }

    public List<GameNumberTimesPlayed> topGamesByNumberTimesPlayed(LocalDateTime startTime, LocalDateTime endTime){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            List<GameNumberTimesPlayed> games = this.gameDao.topGamesByTimesPlayedBetweenRange(session, startTime, endTime);

            tx.commit();

            return games;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    public Long createGame(String code, String name, LocalDate releaseYear){

        Transaction tx = null;

        try{
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            Game game = new Game();
            game.setCode(code);
            game.setName(name);
            game.setReleaseYear(releaseYear);

            Long id = this.gameDao.create(session, game);

            tx.commit();

            return id;

        } catch (RuntimeException e){
            if (tx != null) tx.rollback();
            throw e;
        }

    }

}
