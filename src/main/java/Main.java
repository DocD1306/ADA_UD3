import service.*;

public class Main {

    public static void main(String[] args) {

        UserService userService = new UserService();
        BookingService bookingService = new BookingService();
        TagService tagService = new TagService();
        SpaceService spaceService = new SpaceService();
        VenueService venueService = new VenueService();

        try{
            System.out.println(spaceService.findTop5CitiesWithMoreSpaces()); // todo

            System.out.println("Operaciones realizadas");
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


    }

}
