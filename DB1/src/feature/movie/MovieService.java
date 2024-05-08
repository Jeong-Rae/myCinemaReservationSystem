package feature.movie;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.movie.Movie;
import infrastructure.config.DatabaseConfig;
import infrastructure.repository.MovieRepository;

public class MovieService {
	private final MovieRepository movieRepository;
	
	public List<Movie> findAllMovies() {
		List<Movie> response = new ArrayList<Movie>();
		try(Connection connection = DatabaseConfig.getConnectionAdmin()) {
			response = movieRepository.findAllMovies(connection);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	public List<Movie> findMovieByCriteria(SearchCriteria searchCriteria) {
		List<Movie> response = new ArrayList<Movie>();
		try(Connection connection = DatabaseConfig.getConnectionUser()) {
			response = movieRepository.findMoviesByFilter(connection, searchCriteria);
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	
	public MovieService(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}
}
