package feature.reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import core.domain.reservation.PaymentStatusType;
import core.domain.reservation.Reservation;
import core.domain.ticket.TicketRequest;
import infrastructure.config.DatabaseConfig;
import infrastructure.repository.ReservationRepository;

public class ReservationService {
    private final ReservationRepository reservationRepository;

    public List<Reservation> findAllReservations() {
        List<Reservation> response = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            response = reservationRepository.findAllReservations(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }
    
    // 예약 생성
    public Reservation createReservation(ReservationRequest request) {
    	int amout = 0;
    	for (TicketRequest ticketRequest : request.ticketRequests()) {
    		amout += ticketRequest.salePrice();
    	}
    	
    	Reservation reservation = new Reservation(request.paymentMethodType(), PaymentStatusType.COMPLETED, amout, new Date());
    	reservation.setMemberId(request.memberId());
    	
    	Long reservationId;
    	
    	try (Connection connection = DatabaseConfig.getConnectionUser()) {
    		reservationId = reservationRepository.insertToReservation(connection, reservation);
    		reservation.setReservationId(reservationId);
    		
    		System.out.println("[createReservation] reservation: "+ reservation);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	
    	return reservation;
    	
    }

    public int updateReservation(String setClause) {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            return reservationRepository.updateReservationBySqlNative(connection, setClause);
        } catch (SQLException e) {
            System.out.println("[updateReservation] 예약 업데이트 실패");
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteReservation(String whereClause) {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            return reservationRepository.deleteReservationBySqlNative(connection, whereClause);
        } catch (SQLException e) {
            System.out.println("[deleteReservation] 예약 삭제 실패");
            e.printStackTrace();
            return 0;
        }
    }

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
}
