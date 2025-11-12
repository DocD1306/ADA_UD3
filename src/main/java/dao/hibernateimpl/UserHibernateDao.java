package dao.hibernateimpl;

import dao.UserDao;
import domain.User;

public class UserHibernateDao extends GenericHibernateDao<User, Long> implements UserDao {

    public UserHibernateDao(){
        super(User.class);
    }

}
