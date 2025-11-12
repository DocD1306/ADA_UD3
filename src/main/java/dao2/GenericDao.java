package dao2;

import org.hibernate.Session;

public interface GenericDao<T, ID> {

    ID create(Session session, T entity);

    T update(Session session, T entity);

    void delete(Session session, T entity);

    T findById(Session session, ID id);

    boolean deleteById(Session session, ID id);


}
