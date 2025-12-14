import config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import service2.*;

import java.time.LocalDateTime;

public class Main2 {


    public static void main(String[] args) {

        CabinetService cabinetService = new CabinetService();
        PlayerService playerService = new PlayerService();
        MatchService matchService = new MatchService();
        ArcadeService arcadeService = new ArcadeService();
        GameService gameService = new GameService();

        Transaction tx = null;
        try {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            tx = session.beginTransaction();

            System.out.println("\nArcades por nombre\n" + arcadeService.findByName("Arcade1"));
            System.out.println("\nTop jeugos por número de partidas en rango de fechas\n" + gameService.topGamesByNumberTimesPlayed(LocalDateTime.now().minusDays(220), LocalDateTime.now().plusDays(150)));
            System.out.println("\nIngresos estimados por arcade\n"+arcadeService.estimatedIncomesByArcade());
            System.out.println("\nCabinets activos por genero\n" +cabinetService.findActiveCabinetsByGenre("action"));
            System.out.println("\nJugadores con tarjeta inactiva con partidas recientes\n"+playerService.findInactivePlayersWithRecentMatches(LocalDateTime.now().minusDays(100)));
            System.out.println("\nCabinets sin juegos desde\n"+cabinetService.findCabinetsWithNoGamesSince(LocalDateTime.now()));


            tx.commit();
            System.out.println("Todas las operaciones realizadas");
        } catch (Exception e){
            if(tx != null) tx.rollback();
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
