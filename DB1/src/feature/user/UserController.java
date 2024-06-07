package feature.user;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.member.Member;
import core.domain.movie.Movie;
import core.domain.reservation.Reservation;
import core.domain.screen.Screen;
import core.domain.screeningschedule.ScreeningSchedule;
import core.domain.seat.Seat;
import core.domain.ticket.Ticket;
import core.domain.ticket.TicketInfoResponse;
import core.domain.ticket.TicketRequest;
import feature.member.MemberService;
import feature.movie.MovieService;
import feature.movie.SearchCriteria;
import feature.reservation.ReservationRequest;
import feature.reservation.ReservationResponse;
import feature.reservation.ReservationService;
import feature.reservation.ReverationSummary;
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
    
    public Movie getMovieByScreeningSchedule(ScreeningSchedule schedule) {
    	return this.movieService
    			.findAllMovies()
    			.stream()
    			.filter(movie -> movie.getMovieId() == schedule.getMovieId())
    			.toList()
    			.get(0);
    }
    
    // 영화 기준, 상영일정 조회
    public List<ScreeningSchedule> getScreeningScheduleByMovieId(Long movieId) {
    	return screeningScheduleService.findScreeningSchedulesByMovieId(movieId);
    }
    
    public ScreeningSchedule getScreeningScheduleById(long scheduleId) {
    	return this.screeningScheduleService
    			.findAllScreeningSchedules()
    			.stream()
    			.filter(schedule -> schedule.getScheduleId() == scheduleId)
    			.toList()
    			.get(0);
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
    
    // 예약정보 기반 티켓정보
    public List<TicketInfoResponse> getTicketInfoResponses(Long reservationId) {
		List<TicketInfoResponse> response = new ArrayList<TicketInfoResponse>();
		
		response = ticketService.findInfoResponses(reservationId);
		System.out.println("getTicketInfoResponses] ticket 정보 조회 reservationId: "+reservationId);
		
		return response;
	}
    
    // 예약 삭제
    // 티켓 전부 지우고, 예약 삭제
    public boolean removeReservation(Long reservationId) {
    	try {
            reservationService.deleteReservationAndTickets(reservationId);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    
    // 예약 정보 변경 -> 새로운 Request 정보 받아서 싹 update
    // 문제 생기면 null
    public ReservationResponse updateReservation(Long reservationId, ReservationRequest request) {
        try {
            return reservationService.updateReservationWithTicket(reservationId, request);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    
    public List<Reservation> getReservationsByMemberId(long memberId) {
    	return this.reservationService
    			.findAllReservations()
    			.stream()
    			.filter(reservation -> reservation.getMemberId() == memberId)
    			.toList();
    }
    
    public Member getMember() {
    	return this.memberService.findAllMembers().get(0);
    }
    
    public long getMemberId() {
    	return this.memberService.findAllMembers().get(0).getMemberId();
    }
    
    public List<Ticket> getTicketsByReservationId(long reservationId) {
    	return this.ticketService
    			.findAllTickets()
    			.stream()
    			.filter(ticket -> ticket.getReservationId() == reservationId)
    			.toList();
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
    public List<ReverationSummary> getReservationSummary(Member member) {
    	System.out.println("[getReservationSummary] 예약정보 조회 memberId: "+member.getMemberId());
    	return reservationService.findReverationSummaryesByMemberId(member.getMemberId());
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