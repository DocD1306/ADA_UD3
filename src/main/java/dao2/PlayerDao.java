package dao2;

import domain2.Player;
import org.hibernate.Session;

import java.time.LocalDateTime;
import java.util.List;

public interface PlayerDao extends GenericDao<Player, Long> {

    List<Player> inactivePlayersWithRecentGames(Session session, LocalDateTime filterDate);

}
