package feature.admin;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import core.domain.member.Member;
import core.domain.movie.Movie;
import core.domain.reservation.Reservation;
import core.domain.screen.Screen;
import core.domain.screeningschedule.ScreeningSchedule;
import core.domain.seat.Seat;
import core.domain.ticket.Ticket;

public class AdminViewModel {
	private final AdminController adminController;
	public List<Member> members = new ArrayList();
	public List<Movie> movies = new ArrayList();
	public List<Reservation> reservations = new ArrayList();
	public List<ScreeningSchedule> schedules = new ArrayList();
	public List<Screen> screens = new ArrayList();
	public List<Seat> seats = new ArrayList();
	public List<Ticket> tickets = new ArrayList();
	public TableButtonType currentTable = TableButtonType.MEMBER;
	
	public AdminViewModel(AdminController adminController) {
		this.adminController = adminController;
		this.members = this.adminController.getAllMembers();
	}
	
	public DefaultTableModel membersToTableModel() {
		String[] columns = {
			"회원번호",
			"회원이름",
			"전화번호",
			"이메일"
		};
		
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // 모든 셀을 편집 불가능하게 설정
            }
		};
		
		this.members.forEach(member -> 
			tableModel.addRow(new Object[] {
					String.valueOf(member.getMemberId()),
					member.getName(),
					member.getPhoneNumber(),
					member.getEmail()
				})
		);
		
		return tableModel;
	}
	
	public DefaultTableModel moviesToTableModel() {
		String[] columns = {
				"영화ID",
				"제목", 
				"상영시간",
				"등급",
				"감독",
				"배우",
				"장르",
				"개봉일자",
				"평점",
				"설명"
		};
		
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // 모든 셀을 편집 불가능하게 설정
            }
		};
		
		this.movies.forEach(movie -> 
			tableModel.addRow(new Object[] {
						String.valueOf(movie.getMovieId()),
						movie.getTitle(), 
						movie.getDuration(),
						movie.getRating().getDescription(),
						movie.getDirector(),
						movie.getActor(),
						movie.getGenre(),
						movie.getReleaseDate().toString(),
						String.valueOf(movie.getScore()),
						movie.getDescription()
				})
		);
		
		return tableModel;
	}
	
	public DefaultTableModel reservationsToTableModel() {
		String[] columns = {
			"예약번호",
			"결제방법",
			"결제상태",
			"결제금액",
			"예약일자",
			"회원번호"
		};
		
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // 모든 셀을 편집 불가능하게 설정
            }
		};
		
		this.reservations.forEach(reservation -> 
			tableModel.addRow(new Object[] {
					String.valueOf(reservation.getReservationId()),
					reservation.getPaymentMethod().getDescription(),
					reservation.getPaymentStatus().getDescription(),
					String.valueOf(reservation.getAmount()),
					reservation.getPaymentDate().toString(),
					String.valueOf(reservation.getMemberId())
				})
		);
		
		return tableModel;
	}
	
	public DefaultTableModel schedulesToTableModel() {
		String[] columns = {
				"일정번호",
				"상영일자",
				"상영시간",
				"상영요일",
				"상영회자",
				"영화번호",
				"상영관번호"
			};
			
			DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
				@Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // 모든 셀을 편집 불가능하게 설정
	            }
			};
			
			this.schedules.forEach(schedule -> 
				tableModel.addRow(new Object[] {
						String.valueOf(schedule.getScheduleId()),
						schedule.getStartDate().toString(),
						schedule.getStartTime(),
						schedule.getDayOfWeek().toString(),
						String.valueOf(schedule.getSessionNumber()),
						String.valueOf(schedule.getMovieId()),
						String.valueOf(schedule.getScreenId())
					})
			);
			
			return tableModel;
	}
	
	public DefaultTableModel screensToTableModel() {
		String[] columns = {
				"상영관번호",
				"상영관이름",
				"상영관사용여부",
				"행 좌석 수",
				"열 좌석 수"
			};
			
			DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
				@Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // 모든 셀을 편집 불가능하게 설정
	            }
			};
			
			this.screens.forEach(screen -> 
				tableModel.addRow(new Object[] {
						String.valueOf(screen.getScreenId()),
						screen.getName(),
						String.valueOf(screen.getIsActive()),
						String.valueOf(screen.getSeatRow()),
						String.valueOf(screen.getSeatCol())
					})
			);
			
			return tableModel;
	}
	
	public DefaultTableModel seatsToTableModel() {
		String[] columns = {
				"좌석번호",
				"좌석사용여부",
				"행 번호",
				"열 번호",
				"상영관번호",
				"상영일지번호"
			};
			
			DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
				@Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // 모든 셀을 편집 불가능하게 설정
	            }
			};
			
			this.seats.forEach(seat -> 
				tableModel.addRow(new Object[] {
						String.valueOf(seat.getSeatId()),
						String.valueOf(seat.getIsActive()),
						String.valueOf(seat.getRowNumber()),
						String.valueOf(seat.getColNumber()),
						String.valueOf(seat.getScreenId()),
						String.valueOf(seat.getScreeningScheduleId())
					})
			);
			
			return tableModel;
	}
	
	public DefaultTableModel ticketsToTableModel() {
		String[] columns = {
				"티켓번호",
				"발권여부",
				"표준가격",
				"판매가격",
				"상영일자번호",
				"상영관번호",
				"예약번호",
				"좌석번호"
			};
			
			DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
				@Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // 모든 셀을 편집 불가능하게 설정
	            }
			};
			
			this.tickets.forEach(ticket -> 
				tableModel.addRow(new Object[] {
						String.valueOf(ticket.getTicketId()),
						String.valueOf(ticket.getIsIssued()),
						String.valueOf(ticket.getStandardPrice()),
						String.valueOf(ticket.getSalePrice()),
						String.valueOf(ticket.getScreeningScheduleId()),
						String.valueOf(ticket.getScreenId()),
						String.valueOf(ticket.getReservationId()),
						String.valueOf(ticket.getSeatId())
					})
			);
			
			System.out.println(this.tickets);
			
			return tableModel;
	}
	
	public void tableButtonClicked(TableButtonType buttonType) {
		this.currentTable = buttonType;
		
		switch (this.currentTable) {
		case MEMBER:
			this.members = this.adminController.getAllMembers();
			break;
		case MOVIE:
			this.movies = this.adminController.getAllMovies();
			break;
		case RESERVATION:
			this.reservations = this.adminController.getAllReservations();
			break;
		case SCHEDULE:
			this.schedules = this.adminController.getAllScreeningSchedules();
			break;
		case SCREEN:
			this.screens = this.adminController.getAllScreens();
			break;
		case SEAT:
			this.seats = this.adminController.getAllSeats();
			break;
		case TICKET:
			this.tickets = this.adminController.getAllTickets();
			break;
		}
	}
	
	public boolean insertButtonClicked(String insertData) {
		boolean result = false;
		
		switch (this.currentTable) {
		case MEMBER:
			result = this.adminController.insertMemberByData(insertData);
			this.members = this.adminController.getAllMembers();
			break;
		case MOVIE:
			result = this.adminController.insertMovieByData(insertData);
			this.movies = this.adminController.getAllMovies();
			break;
		case RESERVATION:
			result = this.adminController.insertReservationByData(insertData);
			this.reservations = this.adminController.getAllReservations();
			break;
		case SCHEDULE:
			result = this.adminController.insertScreeningScheduleByData(insertData);
			this.schedules = this.adminController.getAllScreeningSchedules();
			break;
		case SCREEN:
			result = this.adminController.insertScreenByData(insertData);
			this.screens = this.adminController.getAllScreens();
			break;
		case SEAT:
			result = this.adminController.insertSeatByData(insertData);
			this.seats = this.adminController.getAllSeats();
			break;
		case TICKET:
			result = this.adminController.insertTicketByData(insertData);
			this.tickets = this.adminController.getAllTickets();
			break;
		}
		
		System.out.println("insert");
		
		return result;
	}
	
	public boolean deleteButtonClicked(String whereClause) {
		boolean result = false;
		
		switch (this.currentTable) {
		case MEMBER:
			result = this.adminController.deleteMembersByWhereClause(whereClause) != 0;
			this.members = this.adminController.getAllMembers();
			break;
		case MOVIE:
			result = this.adminController.deleteMoviesByWhereClause(whereClause) != 0;
			this.movies = this.adminController.getAllMovies();
			break;
		case RESERVATION:
			result = this.adminController.deleteReservationssByWhereClause(whereClause) != 0;
			this.reservations = this.adminController.getAllReservations();
			break;
		case SCHEDULE:
			result = this.adminController.deleteScreeningSchedulesByWhereClause(whereClause) != 0;
			this.schedules = this.adminController.getAllScreeningSchedules();
			break;
		case SCREEN:
			result = this.adminController.deleteScreensByWhereClause(whereClause) != 0;
			this.screens = this.adminController.getAllScreens();
			break;
		case SEAT:
			result = this.adminController.deleteSeatsByWhereClause(whereClause) != 0;
			this.seats = this.adminController.getAllSeats();
			break;
		case TICKET:
			result = this.adminController.deleteTicketsByWhereClause(whereClause) != 0;
			this.tickets = this.adminController.getAllTickets();
			break;
		}
		
		return result;
	}
	
	public boolean updateButtonClicked(String setClause) {
		boolean result = false;
		
		switch (this.currentTable) {
		case MEMBER:
			result = this.adminController.updateMembersBySetClause(setClause) != 0;
			this.members = this.adminController.getAllMembers();
			break;
		case MOVIE:
			result = this.adminController.updateMoviesBySetClause(setClause) != 0;
			this.movies = this.adminController.getAllMovies();
			break;
		case RESERVATION:
			result = this.adminController.updateeReservationssByWhereClause(setClause) != 0;
			this.reservations = this.adminController.getAllReservations();
			break;
		case SCHEDULE:
			result = this.adminController.updateScreeningSchedulesByWhereClause(setClause) != 0;
			this.schedules = this.adminController.getAllScreeningSchedules();
			break;
		case SCREEN:
			result = this.adminController.updateScreensByWhereClause(setClause) != 0;
			this.screens = this.adminController.getAllScreens();
			break;
		case SEAT:
			result = this.adminController.updateSeatsByWhereClause(setClause) != 0;
			this.seats = this.adminController.getAllSeats();
			break;
		case TICKET:
			result = this.adminController.updateTicketsByWhereClause(setClause) != 0;
			this.tickets = this.adminController.getAllTickets();
			break;
		}
		
		return result;
	}
	
	public void resetButtonClicked() {
		this.adminController.initializeDatabase();
		
		switch (this.currentTable) {
		case MEMBER:
			this.members = this.adminController.getAllMembers();
			break;
		case MOVIE:
			this.movies = this.adminController.getAllMovies();
			break;
		case RESERVATION:
			this.reservations = this.adminController.getAllReservations();
			break;
		case SCHEDULE:
			this.schedules = this.adminController.getAllScreeningSchedules();
			break;
		case SCREEN:
			this.screens = this.adminController.getAllScreens();
			break;
		case SEAT:
			this.seats = this.adminController.getAllSeats();
			break;
		case TICKET:
			this.tickets = this.adminController.getAllTickets();
			break;
		}
	}
}
