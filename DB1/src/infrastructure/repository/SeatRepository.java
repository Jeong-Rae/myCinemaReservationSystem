package infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.seat.Seat;

public class SeatRepository {
    public List<Seat> findAllSeats(Connection conn) {
        String sql = "SELECT * FROM seat";
        List<Seat> response = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Seat seat = Seat.RsToSeat(rs);
                response.add(seat);
            }
        } catch (SQLException e) {
            System.out.println("Seat 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }

    public SeatRepository() {
    }
}