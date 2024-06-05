package feature.movie;

import java.util.List;
import javax.swing.table.DefaultTableModel;

import core.domain.movie.Movie;

public class MovieSearchViewModel {
	private final MovieService movieUseCase;
	public MovieSearchViewModelDelegate delegate;
	public List<Movie> movies;
	public String title = null;
	public String director = null;
	public String actor = null;
	public String genre = null;
	
	public MovieSearchViewModel(MovieService movieUseCase) {
		this.movieUseCase = movieUseCase;
		this.movies = this.movieUseCase.findAllMovies();
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
	
	private void updateMoviesByCriteria() {
		System.out.println(this.title + ", " + this.director + ", "  + this.actor + ", "  + this.genre);
		this.movies = this.movieUseCase.findMovieByCriteria(
		new SearchCriteria(
				this.title, 
				this.director, 
				this.actor, 
				this.genre));
	}
}
