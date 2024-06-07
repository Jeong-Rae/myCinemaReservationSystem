package core.domain.ticket;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public record TicketInfoResponse(
    Long reservationId,
    Long ticketId,
    String movieTitle,
    Date startDate,
    String startTime,
    String dayOfWeek,
    int sessionNumber,
    String screenName,
    int standardPrice,
    int salePrice,
    int seatRow,
    int seatCol
) {
    public static TicketInfoResponse from(ResultSet rs) throws SQLException {
        return new TicketInfoResponse(
            rs.getLong("reservation_id"),
            rs.getLong("ticket_id"),
            rs.getString("movie_title"),
            rs.getDate("start_date"),
            rs.getString("start_time"),
            rs.getString("day_of_week"),
            rs.getInt("session_number"),
            rs.getString("screen_name"),
            rs.getInt("standard_price"),
            rs.getInt("sale_price"),
            rs.getInt("seat_row"),
            rs.getInt("seat_col")
        );
    }
}
