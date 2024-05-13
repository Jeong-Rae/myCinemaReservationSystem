package feature.admin;

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
import infrastructure.repository.db2.DatabaseInitializer;

public class AdminController {
    private final MovieService movieService;
    private final ScreenService screenService;
    private final ScreeningScheduleService screeningScheduleService;
    private final TicketService ticketService;
    private final SeatService seatService;
    private final MemberService memberService;
    private final ReservationService reservationService;

    // DB 초기화
    public void initializeDatabase() {
        DatabaseInitializer databaseInitializer = new DatabaseInitializer();
        databaseInitializer.initializeDatabase();
    }

    // SELECT

    public List<Member> getAllMembers() {
        return memberService.findAllMembers();
    }

    public List<Movie> getAllMovies() {
        return movieService.findAllMovies();
    }
    
    public List<Reservation> getAllReservations() {
        return reservationService.findAllReservations();
    }

    public List<ScreeningSchedule> getAllScreeningSchedules() {
        return screeningScheduleService.findAllScreeningSchedules();
    }

    public List<Screen> getAllScreens() {
        return screenService.findAllScreens();
    }

    public List<Seat> getAllSeats() {
        return seatService.findAllSeats();
    }
    
    public List<Ticket> getAllTickets() {
        return ticketService.findAllTickets();
    }
    
    // INSERT
    

    // DELETE
    public int deleteMembersByWhereClause(String whereClause) {
    	return memberService.deleteMember(whereClause);
    }
    
    public int deleteMoviesByWhereClause(String whereCluse) {
		return movieService.deleteMovie(whereCluse);
	}
    
    public int deleteReservationssByWhereClause(String whereClause) {
    	return reservationService.deleteReservation(whereClause);
    }
    
    public int deleteScreeningSchedulesByWhereClause(String whereCluse) {
		return screeningScheduleService.deleteScreeningSchedule(whereCluse);
	}
    
    public int deleteScreensByWhereClause(String whereClause) {
    	return screenService.deleteScreen(whereClause);
    }
    
    public int deleteSeatsByWhereClause(String whereCluse) {
		return seatService.deleteSeat(whereCluse);
	}
    
    public int deleteTicketsByWhereClause(String whereClause) {
    	return ticketService.deleteTicket(whereClause);
    }
    
    

    // UPDATE
    public int updateMembersBySetClause(String setClause) {
    	return memberService.updateMember(setClause);
    }
    
    public int updateMoviesBySetClause(String setCluse) {
		return movieService.updateMovie(setCluse);
	}
    
    public int updateeReservationssByWhereClause(String setClause) {
    	return reservationService.updateReservation(setClause);
    }
    
    public int updateScreeningSchedulesByWhereClause(String setCluse) {
		return screeningScheduleService.updateScreeningSchedule(setCluse);
	}
    
    public int updateScreensByWhereClause(String setClause) {
    	return screenService.updateScreen(setClause);
    }
    
    public int updateSeatsByWhereClause(String setCluse) {
		return seatService.updateSeat(setCluse);
	}
    
    public int updateTicketsByWhereClause(String seteClause) {
    	return ticketService.updateTicket(seteClause);
    }
    
    

    public AdminController(
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