package feature.admin;

import java.util.List;

import core.domain.movie.Movie;
import feature.movie.MovieService;
import infrastructure.repository.db2.DatabaseInitializer;

public class AdminController {
	private final MovieService movieService;
	// DB 초기화
	public void initializeDatabase() {
		DatabaseInitializer databaseInitializer = new DatabaseInitializer();
		
		databaseInitializer.initializeDatabase();
	}
	
	// 전체 DB 조회
	public List<Movie> getMovies() {
		return movieService.findAllMovies();
	}
	
	// INSERT
	
	// DELETE
	
	// UPDATE
	
	public AdminController(MovieService movieService) {
		this.movieService = movieService;
	}
}
