package dao2.hibernateimpl2;

import dao2.GameDao;
import domain2.Game;
import dto2.GameNumberTimesPlayed;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class GameHibernateDao extends GenericHibernateDao<Game, Long> implements GameDao {

    public GameHibernateDao(){
        super(Game.class);
    }

    @Override
    public List<GameNumberTimesPlayed> topGamesByTimesPlayedBetweenRange(Session session, LocalDateTime startTime, LocalDateTime endTime) {

        String hql = """
                SELECT new dto2.GameNumberTimesPlayed(g, count(m)) 
                FROM Game g LEFT JOIN Cabinet c on c.game = g LEFT JOIN Match m on m.cabinet = c 
                WHERE m.startedAt BETWEEN :startTime AND :endTime 
                AND m.endAt BETWEEN :startTime AND :endTime
                GROUP BY g""";
        Query<GameNumberTimesPlayed> query = session.createQuery(hql, GameNumberTimesPlayed.class)
                .setParameter("startTime", startTime)
                .setParameter("endTime", endTime);

        return query.getResultList();

    }
}
