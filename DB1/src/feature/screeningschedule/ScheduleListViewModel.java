package feature.screeningschedule;

import java.util.ArrayList;
import java.util.List;

import core.domain.movie.Movie;
import core.domain.screeningschedule.ScreeningSchedule;

public class ScheduleListViewModel {
	private final ScreeningScheduleService screeningScheduleUseCase;
	public ScheduleListViewModelDelegate delegate;
	public Movie movie;
	public List<ScreeningSchedule> scheduleList;
	
	public ScheduleListViewModel(Movie movie, ScreeningScheduleService screeningScheduleUseCase) {
		this.movie = movie;
		this.screeningScheduleUseCase = screeningScheduleUseCase;
		this.scheduleList = this.screeningScheduleUseCase.findScreeningSchedulesByMovieId(this.movie.getMovieId());
	}
	
	public Object[] scheduleListToArray() {
		return this.scheduleList.toArray();
	}
	
	public void scheduleListCellTapped(ScreeningSchedule schedule) {
		this.delegate.scheduleListCellTapped(schedule);
	}
}
