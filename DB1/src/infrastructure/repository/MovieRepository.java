package infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.movie.Movie;

public class MovieRepository {
	public List<Movie> findAllMovies(Connection conn) {
		String sql = "SELECT * FROM movie";
		List<Movie> response = new ArrayList<Movie>();

		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			
			 while (rs.next()) {
	                Movie movie = Movie.RsToMovie(rs);
	                response.add(movie);
			 }
			 
		} catch (SQLException e) {
            System.out.println("Movie 테이블 조회 실패");
            e.printStackTrace();
        }
		
		return response;
	}
	
	public MovieRepository() {
	}
}
