package dao2.hibernateimpl2;

import dao2.PlayerDao;
import domain2.Match;
import domain2.Player;
import domain2.RfidCard;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;

public class PlayerHibernateDao extends GenericHibernateDao<Player, Long> implements PlayerDao {

    public PlayerHibernateDao() {
        super(Player.class);
    }

    @Override
    public List<Player> inactivePlayersWithRecentGames(Session session, LocalDateTime filterDate) {

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Player> criteriaQuery = cb.createQuery(Player.class);
        Root<Match> root = criteriaQuery.from(Match.class);
        Join<Match, RfidCard> joinRfidCard = root.join("rfidCard");
        Join<RfidCard, Player> joinPlayer = joinRfidCard.join("player");

        criteriaQuery.select(joinPlayer).distinct(true)
                .where(
                    cb.and(
                            cb.isFalse(joinRfidCard.get("active")),
                            cb.greaterThan(root.get("endAt"), filterDate)
                    )
                );

        Query<Player> query = session.createQuery(criteriaQuery);

        return query.getResultList();
    }
}
