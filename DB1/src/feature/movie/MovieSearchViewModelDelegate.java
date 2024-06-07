package feature.movie;

import core.domain.movie.Movie;

public interface MovieSearchViewModelDelegate {
	void movieTableCellTapped(Movie movie);
	void updateMovieButtonClicked();
	void updateScheduleButtonClicked();
	void deleteButtonClicked();
}
