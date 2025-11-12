package service2;

import config.HibernateUtil;
import dao2.CabinetDao;
import dao2.MatchDao;
import dao2.RfidCardDao;
import dao2.hibernateimpl2.CabinetHibernateDao;
import dao2.hibernateimpl2.MatchHibernateDao;
import dao2.hibernateimpl2.RfidCardHibernateDao;
import domain2.Cabinet;
import domain2.Match;
import domain2.Result;
import domain2.RfidCard;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class MatchService {

    private final SessionFactory sessionFactory;
    private final MatchDao matchDao;
    private final RfidCardDao rfidCardDao;
    private final CabinetDao cabinetDao;

    public MatchService(){
        this.sessionFactory = HibernateUtil.getSessionFactory();
        this.matchDao = new MatchHibernateDao();
        this.rfidCardDao = new RfidCardHibernateDao();
        this.cabinetDao = new CabinetHibernateDao();
    }

    public List<Match> listMatches(){


        Transaction tx = null;

        try {
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            List<Match> matches = this.matchDao.listAllMatches(session);

            return matches;

        } catch (RuntimeException e){
            if(tx != null) tx.rollback();
            throw e;
        }

    }

    public Long createMatch(Long rfidCardId, Long cabinetId, Result result){

        Transaction tx = null;

        try {
            Session session = this.sessionFactory.getCurrentSession();
            tx = session.beginTransaction();

            RfidCard rfidCard = this.rfidCardDao.findById(session, rfidCardId);

            if(rfidCard == null) {
                throw new EntityNotFoundException("No exite ningún RfidCard con ID: " + rfidCardId);
            }

            Cabinet cabinet = this.cabinetDao.findById(session, cabinetId);

            if(cabinet == null) {
                throw new EntityNotFoundException("No exite ningún Cabinet con ID: " + cabinetId);
            }

            Random random = new Random();
            Match match = new Match();

            match.setStartedAt(LocalDateTime.now());
            match.setDurationSec(random.nextLong(5, 120));
            match.setScore(random.nextLong(1, 1000));
            match.setResult(Result.values()[random.nextInt(Result.values().length)]);
            match.setCreditsUsed(random.nextLong(1, 6));
            match.setRfidCard(rfidCard);
            match.setCabinet(cabinet);

            Long id = this.matchDao.create(session, match);

            tx.commit();

            return id;
        } catch (RuntimeException e){
            if(tx != null) tx.rollback();
            throw e;
        }

    }

}
