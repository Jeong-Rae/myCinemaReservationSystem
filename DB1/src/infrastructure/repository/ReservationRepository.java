package infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.domain.reservation.Reservation;
import feature.reservation.ReverationSummary;

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
    
    public List<ReverationSummary> findReverationsByMemberId(Connection connection, Long memberId) {
    	String sql = "SELECT \r\n"
    			+ "		R.reservation_id AS reservation_id,\r\n"
    			+ "	    M.title AS movie_title,\r\n"
    			+ "	    SS.start_date AS start_date,\r\n"
    			+ "	    SS.start_time AS start_time,\r\n"
    			+ "	    S.name AS screen_Name,\r\n"
    			+ "	    ST.row_number AS `row_number`,\r\n"
    			+ "	    ST.col_number AS col_number,\r\n"
    			+ "	    R.amount AS amount\r\n"
    			+ "	FROM \r\n"
    			+ "	    reservation R\r\n"
    			+ "	    INNER JOIN ticket T ON R.reservation_id = T.reservation_id\r\n"
    			+ "	    INNER JOIN screening_schedule SS ON T.screening_schedule_id = SS.schedule_id\r\n"
    			+ "	    INNER JOIN movie M ON SS.movie_id = M.movie_id\r\n"
    			+ "	    INNER JOIN screen S ON T.screen_id = S.screen_id\r\n"
    			+ "	    INNER JOIN seat ST ON T.seat_id = ST.seat_id\r\n"
    			+ "	WHERE \r\n"
    			+ "	    R.member_id = " + memberId;
    	
    	List<ReverationSummary> response = new ArrayList<>();
    	 try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
             while (rs.next()) {
            	 response.add(ReverationSummary.from(rs));
             }
         } catch (SQLException e) {
             System.out.println("[findAllReservations] Reservation 테이블 조회 실패");
             e.printStackTrace();
         }
    	 
    	 return response;
    }
    
    public Long insertToReservation(Connection connection, Reservation reservation) {
    	String sql = "INSERT INTO reservation (payment_method, payment_status, amount, payment_date, member_id) VALUES (?, ?, ?, ?, ?)";
    	Long reservationId = 0L;
    	
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
        	pstmt.setString(1, reservation.getPaymentMethod().name());
            pstmt.setString(2, reservation.getPaymentStatus().name());
            pstmt.setInt(3, reservation.getAmount());
            pstmt.setDate(4, new java.sql.Date(reservation.getPaymentDate().getTime()));
            pstmt.setLong(5, reservation.getMemberId());
            
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        reservationId = generatedKeys.getLong(1);
                    }
                }
            }
            
        } catch (SQLException e) {
            System.out.println("[insertReservation] Reservation 테이블에 INSERT 실패");
            e.printStackTrace();
        }
        
        return reservationId;
    }
    
    public void insertReservationBySqlNative(Connection connection, String insertData) throws SQLException {
        String sql = "INSERT INTO reservation (payment_method, payment_status, amount, payment_date, member_id) VALUES " + insertData.trim();

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
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
