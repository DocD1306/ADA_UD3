package dao.hibernateimpl;

import dao.SpaceDao;
import domain.Space;
import domain.User;
import dto.SpaceConfirmedIncome;
import dto.SpacesNumberByVenueNameTagNameCombination;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.List;

public class SpaceHibernateDao extends GenericHibernateDao<Space, Long> implements SpaceDao {


    public SpaceHibernateDao(){
        super(Space.class);
    }

    @Override
    public Space updateSpaceWithUser(Space space, User u) {
        return null;
    }

    @Override
    public List<String> top5CitiesWithMoreSpaces(Session session) {

        String jpql = "SELECT v.city FROM Venue v JOIN v.spaces GROUP BY v.city ORDER BY COUNT(v.city) DESC";

        Query<String> cities = session.createQuery(jpql, String.class).setMaxResults(5);

        return cities.getResultList();

    }

    //    @Override
//    public List<String> top5CitiesWithMoreSpaces(Session session) {
//        String hql = "SELECT v.city FROM Space s JOIN s.venue v GROUP BY v.city order by count(v.city) desc";
//        Query<String> query = session.createQuery(hql, String.class).setMaxResults(5);
//        return query.getResultList();
//    }
//


    @Override
    public List<Space> spacesByMinimumCapacityAndMaxPrice(Session session, int minimumCapacity, BigDecimal maxPrice) {

        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Space> cq = cb.createQuery(Space.class);
        Root<Space> root = cq.from(Space.class);

        cq.select(root).where(
                cb.and(
                        cb.greaterThanOrEqualTo(root.get("capacity"), minimumCapacity),
                        cb.lessThanOrEqualTo(root.get("hourlyPrice"), maxPrice)
                )
        );

        return session.createQuery(cq).getResultList();

    }

    //    @Override
//    public List<Space> spacesByMinimumCapacityAndMaxPrice(Session session, int minimumCapacity, BigDecimal maxPrice) {
//
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//        CriteriaQuery<Space> criteriaQuery = cb.createQuery(Space.class);
//        Root<Space> root = criteriaQuery.from(Space.class);
//
//        criteriaQuery.select(root)
//                .where(
//                        cb.and(
//                                cb.greaterThan(root.get("capacity"),minimumCapacity),
//                                cb.lessThan(root.get("hourlyPrice"), maxPrice),
//                                cb.equal(root.get("active"), true)
//                                )
//                );
//
//        return session.createQuery(criteriaQuery).getResultList();
//
//    }

    @Override
    public List<Space> neverReservedSpaces(Session session) {

        String sql = "SELECT s.* FROM spaces s LEFT JOIN bookings b on s.id = b.space_id WHERE b.space_id IS NULL";

        Query<Space> query = session.createNativeQuery(sql, Space.class);

        return query.getResultList();

    }

    //
//    @Override
//    public List<Space> neverReservedSpaces(Session session) {
//        String sql = "SELECT s.id, s.active, s.capacity, s.code, s.hourlyPrice, s.name, s.type, s.venue_id " +
//                "FROM spaces s LEFT JOIN bookings b on s.id = b.space_id WHERE b.space_id IS NULL";
//
//        Query<Space> query = session.createNativeQuery(sql, Space.class);
//
//        return query.getResultList();
//    }
//

    @Override
    public List<SpaceConfirmedIncome> top3SpacesByConfirmedIncome(Session session) {

        String jpql = "SELECT new dto.SpaceConfirmedIncome(s, sum(b.totalPrice)) FROM Space s JOIN s.bookings b WHERE b.status = 'CONFIRMED' GROUP BY s ORDER BY sum(b.totalPrice) DESC";

        Query<SpaceConfirmedIncome> query = session.createQuery(jpql, SpaceConfirmedIncome.class).setMaxResults(3);


        return query.getResultList();


    }

    //    @Override
//    public List<SpaceConfirmedIncome> top3SpacesByConfirmedIncome(Session session) {
//
//        String hql = "SELECT new dto.SpaceConfirmedIncome(s, sum(b.totalPrice)) FROM Space s JOIN s.bookings b WHERE b.status = 'CONFIRMED' " +
//                "GROUP BY s ORDER BY sum(b.totalPrice) DESC";
//
//        Query<SpaceConfirmedIncome> query = session.createQuery(hql, SpaceConfirmedIncome.class).setMaxResults(3);
//
//        return query.getResultList();
//
//    }
//

    @Override
    public List<SpacesNumberByVenueNameTagNameCombination> spacesNumberByVenueNameAndTagNameCombination(Session session) {

        String jpql = "SELECT new dto.SpacesNumberByVenueNameTagNameCombination(v.city, t.name, count(s)) FROM Space s JOIN s.venue v JOIN s.tags t " +
                "GROUP BY v.city, t.name ORDER BY count(s) DESC";

        Query<SpacesNumberByVenueNameTagNameCombination> query = session.createQuery(jpql, SpacesNumberByVenueNameTagNameCombination.class);

        return query.getResultList();
    }

//    @Override
//    public List<SpacesNumberByVenueNameTagNameCombination> spacesNumberByVenueNameAndTagNameCombination(Session session) {
//        String hql = """
//                SELECT new dto.SpacesNumberByVenueNameTagNameCombination(v.name, t.name, count(*))
//                FROM Space s JOIN s.venue v JOIN s.tags t
//                GROUP BY v.name, t.name ORDER BY count(*) DESC""";
//        Query<SpacesNumberByVenueNameTagNameCombination> query = session.createQuery(hql, SpacesNumberByVenueNameTagNameCombination.class);
//        return query.getResultList();
//    }
}
