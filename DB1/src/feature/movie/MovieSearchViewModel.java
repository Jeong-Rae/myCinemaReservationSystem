package feature.movie;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import core.domain.movie.Movie;

public class MovieSearchViewModel {
	private final MovieService movieUseCase;
	public List<Movie> movies;
	public String title = null;
	public String director = null;
	public String actor = null;
	public String genre = null;
	
	public MovieSearchViewModel(MovieService movieUseCase) {
		this.movieUseCase = movieUseCase;
		this.movies = this.movieUseCase.findAllMovies();
	}
	
	public void titleTextFieldKeyReleased(JTextField textField) {
		this.title = textField.getText();
		System.out.println(this.title);
		this.title = this.title.isBlank() ? null : title;
		this.updateMoviesByCriteria();
	}
	
	public void directorTextFieldKeyReleased(JTextField textField) {
		this.director = textField.getText();
		director = director.isBlank() ? null : director;
		this.updateMoviesByCriteria();
	}
	
	public void actorTextFieldKeyReleased(JTextField textField) {
		this.actor = textField.getText();
		actor = actor.isBlank() ? null : actor;
		this.updateMoviesByCriteria();
	}
	
	public void genreTextFieldKeyReleased(JTextField textField) {
		this.genre = textField.getText();
		genre = genre.isBlank() ? null : genre;
		this.updateMoviesByCriteria();
	}
	
	public DefaultTableModel moviesToTableModel() {
		String[] columns = {
				"제목", 
				"상영시간",
				"등급",
				"감독",
				"배우",
				"설명",
				"개봉일자",
				"평점"
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
						movie.getDescription(),
						movie.getReleaseDate().toString(),
						String.valueOf(movie.getScore())
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
