package dao;


import domain.Space;
import domain.User;
import org.hibernate.Session;

import java.util.List;

public interface SpaceDao extends GenericDao<Space, Long>{

    Space updateSpaceWithUser(Space space, User u);

    List<String> top5CitiesWithMoreSpaces(Session session);

}
