package infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.ticket.Ticket;

public class TicketRepository {
    public List<Ticket> findAllTickets(Connection conn) {
        String sql = "SELECT * FROM ticket";
        List<Ticket> response = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
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

    public TicketRepository() {
    }
}