package infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    public ReservationRepository() {
    }
}