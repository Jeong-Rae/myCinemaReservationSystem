package feature.user;

import java.util.ArrayList;
import java.util.List;

import core.domain.movie.Movie;
import core.domain.reservation.Reservation;
import core.domain.screen.Screen;
import core.domain.screeningschedule.ScreeningSchedule;
import core.domain.seat.Seat;
import core.domain.ticket.Ticket;
import core.domain.ticket.TicketRequest;
import feature.member.MemberService;
import feature.movie.MovieService;
import feature.movie.SearchCriteria;
import feature.reservation.ReservationRequest;
import feature.reservation.ReservationResponse;
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
    
    // 예약 불가 좌석 조회
    public List<Seat> getUnavailableSeats(Long screeningScheduleId) {
		return seatService.findUnavailableSeats(screeningScheduleId);
	}

    // screenId 기반 스크린 조회
    // 없으면 NULL 반환됨.
    public Screen getScreenById(Long screenId) {
    	return screenService.findByScreenId(screenId);
    }
    
    // INSERT
    
    // 예약 생성
    public ReservationResponse createReservation(ReservationRequest request) {
    	List<TicketRequest> ticketRequests = request.ticketRequests();
    	Reservation reservation = reservationService.createReservation(request);
    	
    	List<Ticket> tickets = new ArrayList<Ticket>();
    	for (TicketRequest ticketRequest : ticketRequests) {
    		// 좌석 저장
    		SeatRequest seatRequest = ticketRequest.seatRequest();
    		Seat seat = seatService.createSeat(seatRequest);
    		
    		//티켓 저장
    		Ticket ticket = ticketService.createTicket(ticketRequest, seat, reservation);
    		tickets.add(ticket);
    	}
    	
    	System.out.println("[createReservation] 예약 완료");
    	ReservationResponse response = new ReservationResponse(reservation, tickets);
    	
    	return response;
    }
    
    public long getMemberId() {
    	return this.memberService.findAllMembers().get(0).getMemberId();
    }
    
    // 본인이 예약한 정보 조회
    
    /*
     SELECT 
	    M.title AS Movie_Title,
	    SS.start_date AS Start_Date,
	    SS.start_time AS Start_Time,
	    S.name AS Screen_Name,
	    ST.row_number AS `Row_Number`,
	    ST.col_number AS Col_Number,
	    R.amount AS Amount
	FROM 
	    reservation R
	    INNER JOIN ticket T ON R.reservation_id = T.reservation_id
	    INNER JOIN screening_schedule SS ON T.screening_schedule_id = SS.schedule_id
	    INNER JOIN movie M ON SS.movie_id = M.movie_id
	    INNER JOIN screen S ON T.screen_id = S.screen_id
	    INNER JOIN seat ST ON T.seat_id = ST.seat_id
	WHERE 
	    1=1 OR R.reservation_id = 1
     */
    

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