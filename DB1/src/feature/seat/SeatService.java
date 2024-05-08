package feature.seat;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.seat.Seat;
import infrastructure.config.DatabaseConfig;
import infrastructure.repository.SeatRepository;

public class SeatService {
    private final SeatRepository seatRepository;

    public List<Seat> findAllSeats() {
        List<Seat> response = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            response = seatRepository.findAllSeats(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }
}