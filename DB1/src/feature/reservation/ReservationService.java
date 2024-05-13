package feature.reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.reservation.Reservation;
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
