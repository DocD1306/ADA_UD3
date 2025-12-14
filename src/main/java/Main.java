import service.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {

    // Actividad 3.4.2
//    public static void main(String[] args) {
//
//
//        UserService userService = new UserService();
//        BookingService bookingService = new BookingService();
//        TagService tagService = new TagService();
//        SpaceService spaceService = new SpaceService();
//        VenueService venueService = new VenueService();
//
//        Transaction tx = null;
//
//        try {
//            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//            Session session = sessionFactory.openSession();
//            tx = session.beginTransaction();
//
//            Long venueId = venueService.createVenue("Direccion", "Elche", "Venue Inventado");
//            Long spaceId1 = spaceService.createSpace(true, 50, "AAAB", new BigDecimal(3.3), "Space 1", SpaceType.MEETING_ROOM, venueId);
//            Long spaceId2 = spaceService.createSpace(true, 50, "AAC", new BigDecimal(3.3), "Space 2", SpaceType.OFFICE, venueId);
//            venueService.assignSpaceToVenue(spaceId1, venueId);
//            venueService.assignSpaceToVenue(spaceId2, venueId);
//
//            Long userId = userService.createUser("emailuser6@gmail.com", "Usario nombre");
//
//            Long accessCardId = userService.assignAccesCard(userId, "1235");
//
//            // No se puede eliminar un venue por las claves foráneas de Space que apuntan a esta tabla
//            //venueService.deleteVenue(venueId);
//            userService.deleteUser(userId);
//
//            System.out.println("TODAS LAS OPERACIONES REALIZADAS CORRECTAMENTE");
//            tx.rollback();
//        } catch (Exception e) {
//            if(tx != null) tx.rollback();
//            System.err.println(e.getMessage());
//            e.printStackTrace();
//        }
//
//
//    }

    public static void main(String[] args) {

        UserService userService = new UserService();
        BookingService bookingService = new BookingService();
        TagService tagService = new TagService();
        SpaceService spaceService = new SpaceService();
        VenueService venueService = new VenueService();

        try{
            System.out.println("Venues por ciudad\n" + venueService.findVenuesByCity("elche"));
            System.out.println("\nTop 5 ciudades con más espacios\n" + spaceService.findTop5CitiesWithMoreSpaces());
            System.out.println("\nVenues con más ingresos confirmados en rango de fechas\n" + venueService.venuesIncomeBetweenDates(LocalDateTime.now().minusDays(50), LocalDateTime.now().plusDays(50)));
            System.out.println("\nEspacios activos por capacidad mínima y precio máximo\n" + spaceService.findActiveSpacesByMinimumCapacityAndMaxPrice(0, new BigDecimal(50000)));
            System.out.println("\nEspacios nunca reservados\n" + spaceService.neverReservedSpaces());
            System.out.println("\nReservas confirmadas por venue y rango\n"+ bookingService.findConfirmedBookingByVenueAndRange("Venue-001",LocalDateTime.now().minusDays(50), LocalDateTime.now().plusDays(50)));
            System.out.println("\nTop 3 espacios por ingresos confirmados\n" + spaceService.findTop3SpacesByConfirmedIncome());
            System.out.println("\nTags más usados\n"+tagService.mostUsedTags());
            System.out.println("\nTags con nombre que empiece por un texto dado ignorando mayúsculas y minúsculas ('w')\n" + tagService.tagsWhereNameStartsLike("w"));
            System.out.println("\nConteo de espacios por combinación de venue y tag\n" + spaceService.findSpacesNumberByVenueNameTagNameCombination());

            System.out.println("\nOperaciones realizadas");
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

        /*

        SALIDA POR CONSOLA

Venues por ciudad
[Venue(id=1, address=Calle calle, city=Elche, createdAt=2025-11-03T15:39:48, name=Diego), Venue(id=2, address=Calle elche, city=elche, createdAt=2025-11-09T14:41:45.321252, name=Venue-001)]

Top 5 ciudades con más espacios
[Elche]

Venues con más ingresos confirmados en rango de fechas
[VenueIncomeBetweenDates[venue=Venue(id=2, address=Calle elche, city=elche, createdAt=2025-11-09T14:41:45.321252, name=Venue-001), totalPrice=50.30]]

Espacios activos por capacidad mínima y precio máximo
[Space(id=1, active=true, capacity=10, code=MR-001, hourlyPrice=20.50, name=Meeting Room, type=MEETING_ROOM, venue=Venue(id=1, address=Calle calle, city=Elche, createdAt=2025-11-03T15:39:48, name=Diego)), Space(id=2, active=true, capacity=30, code=Space-001, hourlyPrice=20.30, name=Nombre Space, type=MEETING_ROOM, venue=Venue(id=2, address=Calle elche, city=elche, createdAt=2025-11-09T14:41:45.321252, name=Venue-001)), Space(id=3, active=true, capacity=20, code=COD022, hourlyPrice=30.00, name=ELX, type=MEETING_ROOM, venue=Venue(id=1, address=Calle calle, city=Elche, createdAt=2025-11-03T15:39:48, name=Diego))]

Espacios nunca reservados
[Space(id=1, active=true, capacity=10, code=MR-001, hourlyPrice=20.50, name=Meeting Room, type=MEETING_ROOM, venue=Venue(id=1, address=Calle calle, city=Elche, createdAt=2025-11-03T15:39:48, name=Diego)), Space(id=3, active=true, capacity=20, code=COD022, hourlyPrice=30.00, name=ELX, type=MEETING_ROOM, venue=Venue(id=1, address=Calle calle, city=Elche, createdAt=2025-11-03T15:39:48, name=Diego))]

Reservas confirmadas por venue y rango
[Booking(id=1, createdAt=2025-11-09T14:41:45.444700, endTime=2025-11-14T14:41:45.439576, startTime=2025-11-04T14:41:45.439576, status=CONFIRMED, totalPrice=50.30)]

Top 3 espacios por ingresos confirmados
[SpaceConfirmedIncome[space=Space(id=2, active=true, capacity=30, code=Space-001, hourlyPrice=20.30, name=Nombre Space, type=MEETING_ROOM, venue=Venue(id=2, address=Calle elche, city=elche, createdAt=2025-11-09T14:41:45.321252, name=Venue-001)), totalConfirmedIncome=50.30]]

Tags más usados
[TagNumberTimesUsed[tag=Tag(id=1, name=etiqueta), numberTimesUsed=1], TagNumberTimesUsed[tag=Tag(id=7, name=wifi), numberTimesUsed=1], TagNumberTimesUsed[tag=Tag(id=2, name=Retro), numberTimesUsed=0], TagNumberTimesUsed[tag=Tag(id=3, name=Fighting), numberTimesUsed=0], TagNumberTimesUsed[tag=Tag(id=4, name=tag1), numberTimesUsed=0], TagNumberTimesUsed[tag=Tag(id=5, name=tag1), numberTimesUsed=0], TagNumberTimesUsed[tag=Tag(id=6, name=tag1), numberTimesUsed=0]]

Tags con nombre que empiece por un texto dado ignorando mayúsculas y minúsculas ('w')
[Tag(id=7, name=wifi)]

Conteo de espacios por combinación de venue y tag
[SpacesNumberByVenueNameTagNameCombination[venueName=Venue-001, tagName=etiqueta, combinationNumber=1], SpacesNumberByVenueNameTagNameCombination[venueName=Diego, tagName=wifi, combinationNumber=1]]
         */

}
