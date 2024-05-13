package infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.domain.seat.Seat;

public class SeatRepository {
	public List<Seat> findAllSeats(Connection conn) {
        String sql = "SELECT * FROM seat";
        List<Seat> response = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
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
    
    // 좌석 에약
    public Long insertToSeat(Connection connection, Seat seat) {
    	String sql = "INSERT INTO seat (is_active, `row_number`, col_number, screen_id, screening_schedule_id) VALUES (?, ?, ?, ?, ?)";
    	long seatId = 0L;

        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
        	 pstmt.setBoolean(1, seat.getIsActive());
             pstmt.setInt(2, seat.getRowNumber());
             pstmt.setInt(3, seat.getColNumber());
             pstmt.setLong(4, seat.getScreenId());
             pstmt.setLong(5, seat.getScreeningScheduleId());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        seatId = generatedKeys.getLong(1);
                    }
                }
            }
            
        } catch (SQLException e) {
            System.out.println("[insertToSeat] Reservation 테이블에 INSERT 실패");
            e.printStackTrace();
        }
        
        return seatId;
    }
    
    public int updateSeatBySqlNative(Connection connection, String setClause) throws SQLException {
        String sql = "UPDATE seat ";
        if (setClause != null && !setClause.trim().isEmpty()) {
            sql += setClause.trim();
        }

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    public int deleteSeatBySqlNative(Connection connection, String whereClause) throws SQLException {
        String sql = "DELETE FROM seat ";
        if (whereClause != null && !whereClause.trim().isEmpty()) {
            sql += whereClause.trim();
        }

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    public SeatRepository() {
    }
}