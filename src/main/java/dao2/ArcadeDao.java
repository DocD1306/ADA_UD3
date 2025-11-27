package dao2;

import domain2.Arcade;
import dto2.ArcadeEstimatedIncome;
import org.hibernate.Session;

import java.util.List;

public interface ArcadeDao extends GenericDao<Arcade, Long> {

    Arcade findByName(Session session, String name);

    public List<ArcadeEstimatedIncome> estimatedIncomeByArcade(Session session);

}
