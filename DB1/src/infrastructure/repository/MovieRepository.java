package infrastructure.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.domain.movie.Movie;
import feature.movie.SearchCriteria;

public class MovieRepository {
	public List<Movie> findAllMovies(Connection connection) {
		String sql = "SELECT * FROM movie";
		List<Movie> response = new ArrayList<Movie>();

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

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

	public List<Movie> findMoviesByFilter(Connection connection, SearchCriteria criteria) throws SQLException{
		List<Movie> movies = new ArrayList<Movie>();
		StringBuilder sql = new StringBuilder("SELECT * FROM movie WHERE 1=1");

		if (criteria.title() != null) {
			sql.append(" AND title = '").append(criteria.title().replace("'", "''")).append("'");
		}
		if (criteria.director() != null) {
			sql.append(" AND director = '").append(criteria.director().replace("'", "''")).append("'");
		}
		if (criteria.actor() != null) {
			sql.append(" AND actor = '").append(criteria.actor().replace("'", "''")).append("'");
		}
		if (criteria.genre() != null) {
			sql.append(" AND genre = '").append(criteria.genre().replace("'", "''")).append("'");
		}

		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql.toString())) {
			while (rs.next()) {
				movies.add(Movie.RsToMovie(rs));
			}
		}

		return movies;
	}
	
	public void insertMovieBySqlNative(Connection connection, String insertData) throws SQLException {
        String sql = "INSERT INTO movie (title, duration, rating, director, actor, genre, description, release_date, score) VALUES " + insertData.trim();

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

	public int updateMovieBySqlNative(Connection connection, String setClause) throws SQLException{
		String sql = "UPDATE movie ";
		if (setClause != null && !setClause.trim().isEmpty()) {
			sql += setClause.trim();
		}

		try (Statement stmt = connection.createStatement()) {
			int updateCount = stmt.executeUpdate(sql);
			return updateCount;
		}
	}
	
	public int deleteMovieBySqlNative(Connection connection, String whereClause) throws SQLException{
	    String sql = "DELETE FROM movie ";
	    if (whereClause != null && !whereClause.trim().isEmpty()) {
	        sql += whereClause.trim();
	    }

	    try (Statement stmt = connection.createStatement()) {
	        int deleteCount = stmt.executeUpdate(sql);
	        return deleteCount;
	    }
	}

	

	public MovieRepository() {
	}
}
