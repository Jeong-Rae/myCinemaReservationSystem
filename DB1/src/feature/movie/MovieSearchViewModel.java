package feature.movie;

import java.util.List;
import javax.swing.table.DefaultTableModel;

import core.domain.member.Member;
import core.domain.movie.Movie;
import core.domain.reservation.Reservation;
import core.domain.ticket.Ticket;
import feature.user.UserController;

public class MovieSearchViewModel {
	private final UserController userController;
	public MovieSearchViewModelDelegate delegate;
	public List<Movie> movies;
	public List<Reservation> memberReservations;
	public List<Ticket> reservationTickets;
	public Member member;
	public String title = null;
	public String director = null;
	public String actor = null;
	public String genre = null;
	
	public MovieSearchViewModel(UserController userController) {
		this.userController = userController;
		this.movies = this.userController.searchMovies(
				new SearchCriteria(
				this.title, 
				this.director, 
				this.actor, 
				this.genre
			)
		);
		this.member = this.userController.getMember();
		this.memberReservations = this.userController.getReservationsByMemberId(this.member.getMemberId());
		this.reservationTickets = this.userController.getTicketsByReservationId(this.memberReservations.get(0).getReservationId());
	}
	
	public void titleTextFieldKeyReleased(String title) {
		this.title = title;
		System.out.println(this.title);
		this.title = this.title.isBlank() ? null : title;
		this.updateMoviesByCriteria();
	}
	
	public void directorTextFieldKeyReleased(String director) {
		this.director = director;
		director = director.isBlank() ? null : director;
		this.updateMoviesByCriteria();
	}
	
	public void actorTextFieldKeyReleased(String actor) {
		this.actor = actor;
		actor = actor.isBlank() ? null : actor;
		this.updateMoviesByCriteria();
	}
	
	public void genreTextFieldKeyReleased(String genre) {
		this.genre = genre;
		genre = genre.isBlank() ? null : genre;
		this.updateMoviesByCriteria();
	}
	
	public void movieTableCellTapped(Movie movie) {
		delegate.movieTableCellTapped(movie);
	}
	
	public DefaultTableModel moviesToTableModel() {
		String[] columns = {
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
			"예약일자"
		};
		
		DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
			@Override
            public boolean isCellEditable(int row, int column) {
                return false; // 모든 셀을 편집 불가능하게 설정
            }
		};
		
		this.memberReservations.forEach(reservation -> 
			tableModel.addRow(new Object[] {
					String.valueOf(reservation.getReservationId()),
					reservation.getPaymentMethod().getDescription(),
					reservation.getPaymentStatus().getDescription(),
					String.valueOf(reservation.getAmount()),
					reservation.getPaymentDate().toString()
				})
		);
		
		return tableModel;
	}

	public DefaultTableModel ticketsToTableModel() {
		String[] columns = {
				"영화명",
				"판매가격",
				"상영일자",
				"상영관번호",
				"좌석번호"
			};
			
			DefaultTableModel tableModel = new DefaultTableModel(columns, 0) {
				@Override
	            public boolean isCellEditable(int row, int column) {
	                return false; // 모든 셀을 편집 불가능하게 설정
	            }
			};
			
			this.reservationTickets.forEach(ticket -> 
				tableModel.addRow(new Object[] {
						this.userController
							.getMovieByScreeningSchedule(
									this.userController
										.getScreeningScheduleById(ticket.getScreeningScheduleId())
						)
						.getTitle(),
						String.valueOf(ticket.getSalePrice()),
						this.userController
							.getScreeningScheduleById(ticket.getScreeningScheduleId())
							.getStartDate()
							.toString(),
						String.valueOf(ticket.getScreenId()),
						String.valueOf(ticket.getSeatId())
					})
			);
			
			return tableModel;
	}
	
	public void reservationCellSelected(int reservaionRow) {
		Reservation selectedReservation = this.memberReservations.get(reservaionRow);
		this.reservationTickets = this.userController.getTicketsByReservationId(selectedReservation.getReservationId());
	}
	
	private void updateMoviesByCriteria() {
		System.out.println(this.title + ", " + this.director + ", "  + this.actor + ", "  + this.genre);
		this.movies = this.userController.searchMovies(
		new SearchCriteria(
				this.title, 
				this.director, 
				this.actor, 
				this.genre));
	}
}
