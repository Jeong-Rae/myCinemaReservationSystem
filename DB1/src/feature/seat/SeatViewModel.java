package feature.seat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import core.domain.movie.Movie;
import core.domain.screeningschedule.ScreeningSchedule;
import core.domain.seat.Seat;
import feature.screeningschedule.ScheduleListViewModel;
import feature.screeningschedule.ScheduleListViewModelDelegate;

public class SeatViewModel implements ScheduleListViewModelDelegate {
	private final SeatService seatUseCase;
	private List<Seat> selectedSeats = new ArrayList<>();
	public List<Seat> seats;
	
	public ScreeningSchedule schedule;
	public Movie movie;
	public int row = 0;
	public int col = 0;
	public SeatViewModel(Movie movie, ScheduleListViewModel scheduleListViewModel, SeatService seatUseCase) {
		this.seatUseCase = seatUseCase;
		this.movie = movie;
		scheduleListViewModel.delegate = this;
	}

	@Override
	public void scheduleListCellTapped(ScreeningSchedule schedule) {
		// TODO Auto-generated method stub
		this.schedule = schedule;
		
		this.seats = this.seatUseCase.findUnavailableSeats(this.schedule.getScheduleId());
		
		List<Integer> rowList = this.seats.stream().map(seat -> seat.getRowNumber()).toList();
		this.row = Collections.max(rowList);
		
		List<Integer> colList = this.seats.stream().map(seat -> seat.getColNumber()).toList();
		this.col = Collections.max(colList);
	}
	
	public void seatCellClicked(boolean isSelected, Seat seat) {
		if (isSelected) {
			this.selectedSeats.remove(seat);
		} else {
			this.selectedSeats.add(seat);
		}
	}
}
