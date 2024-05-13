package infrastructure.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.domain.reservation.Reservation;

public class ReservationRepository {
    public List<Reservation> findAllReservations(Connection conn) {
        String sql = "SELECT * FROM reservation";
        List<Reservation> response = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Reservation reservation = Reservation.RsToReservation(rs);
                response.add(reservation);
            }
        } catch (SQLException e) {
            System.out.println("[findAllReservations] Reservation 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }

    public int updateReservationBySqlNative(Connection connection, String setClause) throws SQLException {
        String sql = "UPDATE reservation ";
        if (setClause != null && !setClause.trim().isEmpty()) {
            sql += setClause.trim();
        }

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    public int deleteReservationBySqlNative(Connection connection, String whereClause) throws SQLException {
        String sql = "DELETE FROM reservation ";
        if (whereClause != null && !whereClause.trim().isEmpty()) {
            sql += whereClause.trim();
        }

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }
}
