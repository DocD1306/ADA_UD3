package dao2.hibernateimpl2;

import dao2.ArcadeDao;
import domain2.Arcade;
import dto2.ArcadeEstimatedIncome;
import jakarta.persistence.Tuple;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ArcadeHibernateDao extends GenericHibernateDao<Arcade, Long> implements ArcadeDao {

    public ArcadeHibernateDao(){
        super(Arcade.class);
    }

    @Override
    public Arcade findByName(Session session, String name) {
        Query<Arcade> query = session.createNamedQuery("Arcade.arcadeByName", Arcade.class).setParameter("name", name);
        return query.getSingleResultOrNull();
    }

    @Override
    public List<ArcadeEstimatedIncome> estimatedIncomeByArcade(Session session) {
        String sql = """
                SELECT a.*, sum((m.duration_sec / 3600) * c.hourly_cost) as expectedIncome
                FROM arcades a LEFT JOIN cabinets c on a.id = c.arcade_id 
                LEFT JOIN matches m on c.id = m.cabinet_id
                GROUP BY a.id""";
        Query<Tuple> query = session.createNativeQuery(sql, Tuple.class);
        List<Tuple> tuples = query.getResultList();
        List<ArcadeEstimatedIncome> result = new ArrayList<>();
        for(Tuple tuple: tuples){
            result.add(
                    new ArcadeEstimatedIncome(
                            new Arcade(Long.valueOf(tuple.get("id").toString()), tuple.get("name").toString(), tuple.get("address").toString()),
                            (BigDecimal) tuple.get("expectedIncome")
                    )
            );
        }
        return result;
    }
}
