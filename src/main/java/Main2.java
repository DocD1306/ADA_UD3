import config.HibernateUtil;
import domain2.Result;
import domain2.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import service2.CabinetService;
import service2.MatchService;
import service2.PlayerService;

import java.time.LocalDate;

public class Main2 {

    public static void main(String[] args) {

        CabinetService cabinetService = new CabinetService();
        PlayerService playerService = new PlayerService();
        MatchService matchService = new MatchService();

        Transaction tx = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();
            LocalDate date = LocalDate.of(2006, 1, 2);

            Long arcadeId = cabinetService.createArcade("Arcade1", "Calle arcade 1");
            Long gameId = cabinetService.createGame("Game-001", "Juego1", date);

            Long cabinetId = cabinetService.createCabinet("slug-001", date, Status.ACTIVE, gameId, arcadeId);

            cabinetService.assignArcadeToCabinet(cabinetId, arcadeId);
            cabinetService.assignGameToCabinet(cabinetId, gameId);

            Long playerId = playerService.createPlayer("player1", "player1@gmail.com");

            Long rfidCardId = playerService.emitRfidCard(playerId, "uid-001");

            Long tagId =cabinetService.createTag("tag1");

            cabinetService.addTagToCabinet(cabinetId, tagId);

            matchService.createMatch(rfidCardId, cabinetId, Result.WIN);
            matchService.createMatch(rfidCardId, cabinetId, Result.LOSE);

            System.out.println(matchService.listMatches());

            cabinetService.updateCabinetStatus(cabinetId, Status.MAINTENANCE);

            tx.commit();
            System.out.println("Todas las operaciones realizadas");
        } catch (Exception e){
            if(tx != null) tx.rollback();
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
