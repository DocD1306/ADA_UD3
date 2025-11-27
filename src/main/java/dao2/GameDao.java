package dao2;

import domain2.Game;
import dto2.GameNumberTimesPlayed;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public interface GameDao extends GenericDao<Game, Long> {

    public List<GameNumberTimesPlayed> topGamesByTimesPlayedBetweenRange(Session session, LocalDateTime startTime, LocalDateTime endTime);

}
