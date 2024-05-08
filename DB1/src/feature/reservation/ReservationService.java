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

    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }
}