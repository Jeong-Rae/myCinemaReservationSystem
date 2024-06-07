package feature.reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public record ReverationSummary(
		Long revervationId,
		String movieTitle,
		Date startDate,
		String endDate,
		String screenName,
		int rowNumber,
		int colNumber,
		int amount
) {
	public static ReverationSummary from(ResultSet rs) throws SQLException {
        return new ReverationSummary(
                rs.getLong("reservation_id"),
                rs.getString("movie_title"),
                rs.getDate("start_date"),
                rs.getString("start_time"),
                rs.getString("screen_name"),
                rs.getInt("row_number"),
                rs.getInt("col_number"),
                rs.getInt("amount")
        );
    }
}
