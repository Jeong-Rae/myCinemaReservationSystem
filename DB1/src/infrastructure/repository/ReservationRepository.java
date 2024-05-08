package infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.reservation.Reservation;

public class ReservationRepository {
    public List<Reservation> findAllReservations(Connection conn) {
        String sql = "SELECT * FROM reservation";
        List<Reservation> response = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Reservation reservation = Reservation.RsToReservation(rs);
                response.add(reservation);
            }
        } catch (SQLException e) {
            System.out.println("Reservation 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }

    public ReservationRepository() {
    }
}