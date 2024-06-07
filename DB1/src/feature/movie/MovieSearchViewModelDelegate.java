package feature.movie;

import core.domain.movie.Movie;

public interface MovieSearchViewModelDelegate {
	void movieTableCellTapped(Movie movie);
	void updateMovieButtonClicked(long reservationId);
	void updateScheduleButtonClicked(Movie movie, long reservationId);
}
