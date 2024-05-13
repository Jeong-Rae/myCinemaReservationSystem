package infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.domain.ticket.Ticket;

public class TicketRepository {
	public List<Ticket> findAllTickets(Connection conn) {
        String sql = "SELECT * FROM ticket";
        List<Ticket> response = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ticket ticket = Ticket.RsToTicket(rs);
                response.add(ticket);
            }
        } catch (SQLException e) {
            System.out.println("Ticket 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }

	public Long insertToTicket(Connection connection, Ticket ticket) {
		String sql = "INSERT INTO ticket (is_issued, standard_price, sale_price, screening_schedule_id, screen_id, reservation_id, seat_id)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		Long ticketId = 0L;

		try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
			pstmt.setBoolean(1, ticket.getIsIssued());
			pstmt.setInt(2, ticket.getStandardPrice());
			pstmt.setInt(3, ticket.getSalePrice());
			pstmt.setLong(4, ticket.getScreeningScheduleId());
			pstmt.setLong(5, ticket.getScreenId());
			pstmt.setLong(6, ticket.getReservationId());
			pstmt.setLong(7, ticket.getSeatId());

			int affectedRows = pstmt.executeUpdate();
			if (affectedRows > 0) {
				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						ticketId = generatedKeys.getLong(1);
					}
				}
			}

		} catch (SQLException e) {
			System.out.println("[insertReservation] Reservation 테이블에 INSERT 실패");
			e.printStackTrace();
		}

		return ticketId;
	}
	
	public int updateTicketBySqlNative(Connection connection, String setClause) throws SQLException {
        String sql = "UPDATE ticket " + setClause;
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    public int deleteTicketBySqlNative(Connection connection, String whereClause) throws SQLException {
        String sql = "DELETE FROM ticket " + whereClause;
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

	public TicketRepository() {
	}
}