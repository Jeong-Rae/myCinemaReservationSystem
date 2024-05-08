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
            System.out.println("[findAllSeats] Seat 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }
    
    //예약 불가능한 좌석
    public List<Seat> findReservedSeats(Connection connection, Long screeningScheduleId) {
    	String sql = "select B.seat_id, B.row_number, B.col_number, B.is_active, B.screen_id, B.screening_schedule_id"
    			+ " from ticket A"
    			+ " join seat B"
    			+ " on A.seat_id = B.seat_id"
    			+ " where B.is_active = true AND B.screening_schedule_id = " + screeningScheduleId;
    	
    	List<Seat> response = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Seat seat = Seat.RsToSeat(rs);
                
                response.add(seat);
            }
        } catch (SQLException e) {
            System.out.println("[findReservedSeats] Seat 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }

    public SeatRepository() {
    }
}