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
    
    // 예약 불가 좌석 조회
    public List<Seat> findUnavailableSeats(Long screeningScheduleId) {
        List<Seat> response = new ArrayList<>();
        
        try (Connection connection = DatabaseConfig.getConnectionUser()) {
            response = seatRepository.findReservedSeats(connection, screeningScheduleId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }
    
    //좌석예약
    public Seat createSeat(SeatRequest request) {
    	Seat seat = new Seat(true, request.rowNumber(), request.colNumber());
    	seat.setScreenId(request.screenId());
    	seat.setScreeningScheduleId(request.screeningScheduleId());
    	Long seatId;
    	
    	try (Connection connection = DatabaseConfig.getConnectionUser()) {
            seatId = seatRepository.insertToSeat(connection, seat);
            seat.setSeatId(seatId);
           
            System.out.println("[createSeat] seat: " + seat);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    	
    	
    	return seat;
    	
    }
    
    public int updateSeat(String setClause) {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            return seatRepository.updateSeatBySqlNative(connection, setClause);
        } catch (SQLException e) {
            System.out.println("[updateSeat] 좌석 업데이트 실패");
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteSeat(String whereClause) {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            return seatRepository.deleteSeatBySqlNative(connection, whereClause);
        } catch (SQLException e) {
            System.out.println("[deleteSeat] 좌석 삭제 실패");
            e.printStackTrace();
            return 0;
        }
    }

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }
}