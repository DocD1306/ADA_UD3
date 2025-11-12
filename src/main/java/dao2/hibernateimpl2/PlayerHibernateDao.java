package dao2.hibernateimpl2;

import dao2.PlayerDao;
import domain2.Player;

public class PlayerHibernateDao extends GenericHibernateDao<Player, Long> implements PlayerDao {

    public PlayerHibernateDao() {
        super(Player.class);
    }



}
