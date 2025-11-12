package dao2.hibernateimpl2;

import dao2.ArcadeDao;
import domain2.Arcade;

public class ArcadeHibernateDao extends GenericHibernateDao<Arcade, Long> implements ArcadeDao {

    public ArcadeHibernateDao(){
        super(Arcade.class);
    }

}
