package feature.movie;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JTextField;
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
	
	public void titleTextFieldKeyTyped(JTextField textField) {
		this.title = textField.getText();
		title = title == "" ? null : title;
		this.updateMoviesByCriteria();
	}
	
	public void directorTextFieldKeyTyped(JTextField textField) {
		this.director = textField.getText();
		director = director == "" ? null : director;
		this.updateMoviesByCriteria();
	}
	
	public void actorTextFieldKeyTyped(JTextField textField) {
		this.actor = textField.getText();
		actor = actor == "" ? null : actor;
		this.updateMoviesByCriteria();
	}
	
	public void genreTextFieldKeyTyped(JTextField textField) {
		this.genre = textField.getText();
		genre = genre == "" ? null : genre;
		this.updateMoviesByCriteria();
	}
	
	public Object[] moviesToString() {
		System.out.println(this.movies);
		return this.movies
				.stream()
				.map(movie -> movie.toString())
				.toArray();
	}
	
	private void updateMoviesByCriteria() {
		this.movies = this.movieUseCase.findMovieByCriteria(
				new SearchCriteria(
						this.title, 
						this.director, 
						this.actor, 
						this.genre));
	}
}
