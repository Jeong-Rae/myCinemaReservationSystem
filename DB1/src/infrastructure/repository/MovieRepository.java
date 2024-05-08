package infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.movie.Movie;
import feature.movie.SearchCriteria;

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

	public List<Movie> findMoviesByFilter(Connection connection, SearchCriteria criteria) {
		List<Movie> movies = new ArrayList<Movie>();
		StringBuilder sql = new StringBuilder("SELECT * FROM movie WHERE 1=1");

		if (criteria.title() != null) {
			sql.append(" AND title = ");
			sql.append("'");
			sql.append(criteria.title());
			sql.append("'");
		}
		if (criteria.director() != null) {
			sql.append(" AND director = ");
			sql.append("'");
			sql.append(criteria.director());
			sql.append("'");
		}
		if (criteria.actor() != null) {
			sql.append(" AND actor = ?");
			sql.append("'");
			sql.append(criteria.actor());
			sql.append("'");
		}
		if (criteria.genre() != null) {
			sql.append(" AND genre = ?");
			sql.append("'");
			sql.append(criteria.genre());
			sql.append("'");
		}

		try (PreparedStatement pstmt = connection.prepareStatement(sql.toString()); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				movies.add(Movie.RsToMovie(rs));
			}
		} catch (SQLException e) {
			System.out.println("[findMoviesByFilter] 영화 조회 실패");
			e.printStackTrace();
		}

		return movies;
	}

	public MovieRepository() {
	}
}
