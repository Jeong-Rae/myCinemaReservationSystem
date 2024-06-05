package feature.screen;

import java.util.ArrayList;
import java.util.List;

import core.domain.movie.Movie;
import core.domain.reservation.PaymentMethodType;
import core.domain.screen.Screen;
import core.domain.screeningschedule.ScreeningSchedule;
import core.domain.seat.Seat;
import feature.seat.SeatRequest;
import feature.user.UserController;

public class ScreenViewModel {
	private final UserController userController;
	public ScreenViewModelDelegate delegate;
	
	public Movie movie;
	public List<ScreeningSchedule> schedules;
	public List<SeatRequest> selectedSeats = new ArrayList<>();
	private List<Seat> unvailableSeats;
	public int totalPrice = 0;
	public ScreeningSchedule schedule;
	public Screen screen = new Screen("test", true, 10, 10);
	private PaymentMethodType paymentMethod;
	
	public ScreenViewModel(Movie movie, UserController userController) {
		this.userController = userController;
		this.movie = movie;
		this.schedules = this.userController.getScreeningScheduleByMovieId(this.movie.getMovieId());
		this.scheduleListCellTapped(this.schedules.get(0));
	}
	
	public void scheduleListCellTapped(ScreeningSchedule schedule) {
		this.schedule = schedule;
		this.unvailableSeats = this.userController.getUnavailableSeats(this.schedule.getScheduleId());
		this.selectedSeats = new ArrayList<>();
	}
	
	public void seatCellReleased (int rowNumber, int colNumber) {
		if (!this.selectedSeats.removeIf(seat -> seat.rowNumber() == rowNumber && seat.colNumber() == colNumber)) {
			SeatRequest seat = new SeatRequest(colNumber, rowNumber, this.schedule.getScheduleId(), this.schedule.getScreenId());
			this.selectedSeats.add(seat);
		}
		
		System.out.println("clicked: " + this.selectedSeats);
	}
	
	public boolean findUnvailableSeatFromMatrix(int rowNumber, int colNumber) {
		for(Seat seat: this.unvailableSeats) {
			if (seat.getRowNumber() == rowNumber && seat.getColNumber() == colNumber) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean findSelectedSeatFromMatrix(int rowNumber, int colNumber) {
		for(SeatRequest seat: this.selectedSeats) {
			if (seat.rowNumber() == rowNumber && seat.colNumber() == colNumber) {
				return true;
			}
		}
		
		return false;
	}
	
	public void paymentMethodButtonClicked(PaymentMethodType paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	public void reservationButtonReleased() {
		this.delegate.reservationButtonReleased();
	}
}
