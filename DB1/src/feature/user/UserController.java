package feature.user;

import java.util.List;

import core.domain.movie.Movie;
import feature.member.MemberService;
import feature.movie.MovieService;
import feature.movie.SearchCriteria;
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

    public List<Movie> searchMovies(SearchCriteria criteria) {
        return movieService.findMovieByCriteria(criteria);
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