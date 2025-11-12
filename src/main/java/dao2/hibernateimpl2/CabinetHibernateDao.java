package dao2.hibernateimpl2;

import dao2.CabinetDao;
import domain2.Cabinet;

public class CabinetHibernateDao extends GenericHibernateDao<Cabinet, Long> implements CabinetDao {

    public CabinetHibernateDao(){
        super(Cabinet.class);
    }
}
