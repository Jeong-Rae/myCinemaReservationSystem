import java.util.List;

import core.domain.movie.Movie;
import core.domain.screeningschedule.ScreeningSchedule;
import core.domain.seat.Seat;
import feature.admin.AdminController;
import feature.member.MemberService;
import feature.movie.MovieService;
import feature.movie.SearchCriteria;
import feature.reservation.ReservationService;
import feature.screen.ScreenService;
import feature.screeningschedule.ScreeningScheduleService;
import feature.seat.SeatService;
import feature.ticket.TicketService;
import feature.user.UserController;
import infrastructure.repository.MemberRepository;
import infrastructure.repository.MovieRepository;
import infrastructure.repository.ReservationRepository;
import infrastructure.repository.ScreenRepository;
import infrastructure.repository.ScreeningScheduleRepository;
import infrastructure.repository.SeatRepository;
import infrastructure.repository.TicketRepository;

public class Application {

    public static void main(String[] args) {
        MovieRepository movieRepository = new MovieRepository();
        MovieService movieService = new MovieService(movieRepository);

        ScreenRepository screenRepository = new ScreenRepository();
        ScreenService screenService = new ScreenService(screenRepository);

        ScreeningScheduleRepository screeningScheduleRepository = new ScreeningScheduleRepository();
        ScreeningScheduleService screeningScheduleService = new ScreeningScheduleService(screeningScheduleRepository);

        TicketRepository ticketRepository = new TicketRepository();
        TicketService ticketService = new TicketService(ticketRepository);

        SeatRepository seatRepository = new SeatRepository();
        SeatService seatService = new SeatService(seatRepository);

        MemberRepository memberRepository = new MemberRepository();
        MemberService memberService = new MemberService(memberRepository);

        ReservationRepository reservationRepository = new ReservationRepository();
        ReservationService reservationService = new ReservationService(reservationRepository);

        AdminController adminController = new AdminController(
                movieService,
                screenService,
                screeningScheduleService,
                ticketService,
                seatService,
                memberService,
                reservationService
        );
        
        UserController userController = new UserController(
                movieService,
                screenService,
                screeningScheduleService,
                ticketService,
                seatService,
                memberService,
                reservationService
        );

        List<Movie> movies = userController.searchMovies(new SearchCriteria("기생충", null, null, null));

        System.out.println("Movies:");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        

        List<ScreeningSchedule> screeningSchedules = userController.getScreeningScheduleByMovieId(movies.get(0).getMovieId());
        
        System.out.println("\nScreening Schedules:");
        for (ScreeningSchedule screeningSchedule : screeningSchedules) {
            System.out.println(screeningSchedule);
        }
//
//        System.out.println("\nTickets:");
//        for (Ticket ticket : tickets) {
//            System.out.println(ticket);
//        }

       
        List<Seat> seats = userController.getUnavailableSeats(1L);
        System.out.println("\nSeats:");
        for (Seat seat : seats) {
            System.out.println(seat);
        }
//
//        System.out.println("\nUsers:");
//        for (Member user : users) {
//            System.out.println(user);
//        }
//
//        System.out.println("\nReservations:");
//        for (Reservation reservation : reservations) {
//            System.out.println(reservation);
//        }
    }

}