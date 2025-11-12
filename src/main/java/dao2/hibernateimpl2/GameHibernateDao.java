package dao2.hibernateimpl2;

import dao2.GameDao;
import domain2.Game;

public class GameHibernateDao extends GenericHibernateDao<Game, Long> implements GameDao {

    public GameHibernateDao(){
        super(Game.class);
    }

}
