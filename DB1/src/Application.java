import java.util.List;

import core.domain.movie.Movie;
import feature.admin.AdminController;
import feature.movie.MovieService;
import infrastructure.repository.MovieRepository;

public class Application {

	public static void main(String[] args) {
		MovieRepository movieRepository = new MovieRepository();
		MovieService movieService = new MovieService(movieRepository);
		
		AdminController adminController = new AdminController(movieService);
		
		List<Movie> movies = adminController.getMovies();
		
		for (Movie movie : movies) {
			System.out.println(movie);
		}
	}

}
