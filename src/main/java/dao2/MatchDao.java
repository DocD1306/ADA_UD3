package dao2;

import domain2.Match;
import org.hibernate.Session;

import java.util.List;

public interface MatchDao extends GenericDao<Match, Long> {

    public List<Match> listAllMatches(Session session);

}
