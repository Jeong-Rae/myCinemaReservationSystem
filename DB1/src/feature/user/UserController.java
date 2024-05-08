package feature.user;

import java.util.List;

import core.domain.member.Member;
import core.domain.movie.Movie;
import core.domain.reservation.Reservation;
import core.domain.screen.Screen;
import core.domain.screeningschedule.ScreeningSchedule;
import core.domain.seat.Seat;
import core.domain.ticket.Ticket;
import feature.member.MemberService;
import feature.movie.MovieService;
import feature.reservation.ReservationService;
import feature.screen.ScreenService;
import feature.screeningschedule.ScreeningScheduleService;
import feature.seat.SeatService;
import feature.ticket.TicketService;

public class UserController {
    private final MovieService movieService;
    private final ScreenService screenService;
    private final ScreeningScheduleService screeningScheduleService;
    private final TicketService ticketService;
    private final SeatService seatService;
    private final MemberService memberService;
    private final ReservationService reservationService;

    // SELECT

    public List<Movie> getMovies() {
        return movieService.findAllMovies();
    }

    public List<Screen> getScreens() {
        return screenService.findAllScreens();
    }

    public List<ScreeningSchedule> getScreeningSchedules() {
        return screeningScheduleService.findAllScreeningSchedules();
    }

    public List<Ticket> getTickets() {
        return ticketService.findAllTickets();
    }

    public List<Seat> getSeats() {
        return seatService.findAllSeats();
    }

    public List<Member> getMembers() {
        return memberService.findAllMembers();
    }

    public List<Reservation> getReservations() {
        return reservationService.findAllReservations();
    }

    // INSERT

    // DELETE

    // UPDATE

    public UserController(
            MovieService movieService,
            ScreenService screenService,
            ScreeningScheduleService screeningScheduleService,
            TicketService ticketService,
            SeatService seatService,
            MemberService userService,
            ReservationService reservationService
    ) {
        this.movieService = movieService;
        this.screenService = screenService;
        this.screeningScheduleService = screeningScheduleService;
        this.ticketService = ticketService;
        this.seatService = seatService;
        this.memberService = userService;
        this.reservationService = reservationService;
    }
}