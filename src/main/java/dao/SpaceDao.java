package dao;


import domain.Space;
import domain.User;
import dto.SpaceConfirmedIncome;
import dto.SpacesNumberByVenueNameTagNameCombination;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;

public interface SpaceDao extends GenericDao<Space, Long>{

    Space updateSpaceWithUser(Space space, User u);

    List<String> top5CitiesWithMoreSpaces(Session session);

    List<Space> spacesByMinimumCapacityAndMaxPrice(Session session, int minimumCapacity, BigDecimal maxPrice);

    List<Space> neverReservedSpaces(Session session);

    List<SpaceConfirmedIncome> top3SpacesByConfirmedIncome(Session session);

    List<SpacesNumberByVenueNameTagNameCombination> spacesNumberByVenueNameAndTagNameCombination(Session session);

}
