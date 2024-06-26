package core.domain.movie;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Movie {
	private Long movieId;
	private String title;
	private String duration;
	private RatingType rating;
	private String director;
	private String actor;
	private String genre;
	private String description;
	private Date releaseDate;
	private double score;

	public Movie(String title, String duration, RatingType rating, String director, String actor, String genre,
			String description, Date releaseDate, double score) {
		this.title = title;
		this.duration = duration;
		this.rating = rating;
		this.director = director;
		this.actor = actor;
		this.genre = genre;
		this.description = description;
		this.releaseDate = releaseDate;
		this.score = score;
	}

	public long getMovieId() {
		return this.movieId;
	}
	
	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return this.title;
	}

	public void updateTitle(String title) {
		this.title = title;
	}

	public String getDuration() {
		return this.duration;
	}

	public void updateDuration(String duration) {
		this.duration = duration;
	}

	public RatingType getRating() {
		return this.rating;
	}

	public void updateRating(RatingType rating) {
		this.rating = rating;
	}

	public String getDirector() {
		return this.director;
	}

	public void updateDirector(String director) {
		this.director = director;
	}

	public String getActor() {
		return this.actor;
	}

	public void updateActor(String actor) {
		this.actor = actor;
	}

	public String getGenre() {
		return this.genre;
	}

	public void updateGenre(String genre) {
		this.genre = genre;
	}

	public String getDescription() {
		return this.description;
	}

	public void updateDescription(String description) {
		this.description = description;
	}

	public Date getReleaseDate() {
		return this.releaseDate;
	}

	public void updateReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public double getScore() {
		return this.score;
	}

	public void updateScore(double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Movie[" + "movieId=" + this.movieId + ", title='" + this.title + '\'' + ", duration='" + this.duration
				+ '\'' + ", rating='" + this.rating + '\'' + ", director='" + this.director + '\'' + ", actor='"
				+ this.actor + '\'' + ", genre='" + this.genre + '\'' + ", description='" + this.description + '\''
				+ ", releaseDate=" + this.releaseDate + ", score=" + this.score + ']';
	}
	
	public static Movie RsToMovie(ResultSet rs) throws SQLException {
		Long movieId = rs.getLong("movie_id");
        String title = rs.getString("title");
        String duration = rs.getString("duration");
        RatingType rating = RatingType.descriptionToEnum(rs.getString("rating"));
        String director = rs.getString("director");
        String actor = rs.getString("actor");
        String genre = rs.getString("genre");
        String description = rs.getString("description");
        Date releaseDate = new Date(rs.getDate("relase_date").getTime());
        double score = rs.getDouble("score");
        
        Movie movie = new Movie(title, duration, rating, director, actor, genre, description, releaseDate, score);
        movie.setMovieId(movieId);
        
        return movie;
	}
}
