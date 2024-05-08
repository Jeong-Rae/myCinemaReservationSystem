package feature.user;

import java.util.List;

import core.domain.movie.Movie;
import core.domain.reservation.Reservation;
import core.domain.screeningschedule.ScreeningSchedule;
import core.domain.seat.Seat;
import core.domain.ticket.Ticket;
import core.domain.ticket.TicketRequest;
import feature.member.MemberService;
import feature.movie.MovieService;
import feature.movie.SearchCriteria;
import feature.reservation.ReservationRequest;
import feature.reservation.ReservationService;
import feature.screen.ScreenService;
import feature.screeningschedule.ScreeningScheduleService;
import feature.seat.SeatRequest;
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

    // 필터링 검색
    public List<Movie> searchMovies(SearchCriteria criteria) {
        return movieService.findMovieByCriteria(criteria);
    }
    
    // 영화 기준, 상영일정 조회
    public List<ScreeningSchedule> getScreeningScheduleByMovieId(Long movieId) {
    	return screeningScheduleService.findScreeningSchedulesByMovieId(movieId);
    }
    
    public List<Seat> getUnavailableSeats(Long screeningScheduleId) {
		return seatService.findUnavailableSeats(screeningScheduleId);
	}

    // INSERT
    
    public void createReservation(ReservationRequest request) {
    	List<TicketRequest> ticketRequests = request.ticketRequests();
    	Reservation reservation = reservationService.createReservation(request);
    	
    	for (TicketRequest ticketRequest : ticketRequests) {
    		// 좌석 저장
    		SeatRequest seatRequest = ticketRequest.seatRequest();
    		Seat seat = seatService.createSeat(seatRequest);
    		System.out.println("[createReservation] seat: " + seat);
    		
    		//티켓 저장
    		Ticket ticket = ticketService.createTicket(ticketRequest, seat, reservation);
    		System.out.println("[createReservation] ticket: " + ticket);
    	}
    }

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