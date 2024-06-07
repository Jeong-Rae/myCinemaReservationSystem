package feature.screeningschedule;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.screeningschedule.ScreeningSchedule;
import infrastructure.config.DatabaseConfig;
import infrastructure.repository.ScreeningScheduleRepository;

public class ScreeningScheduleService {
    private final ScreeningScheduleRepository screeningScheduleRepository;

    public List<ScreeningSchedule> findAllScreeningSchedules() {
        List<ScreeningSchedule> response = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            response = screeningScheduleRepository.findAllScreeningSchedules(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }

    public List<ScreeningSchedule> findScreeningSchedulesByMovieId(Long movieId) {
        List<ScreeningSchedule> response = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnectionUser()) {
            response = screeningScheduleRepository.findByMovie(connection, movieId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }
    
    public void insertScreeningSchedule(String insertData) throws SQLException {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            screeningScheduleRepository.insertScreeningScheduleBySqlNative(connection, insertData);
        } catch (SQLException e) {
            System.out.println("[insertScreeningSchedule] 상영일정 등록 실패");
            throw e;
        }
    }
    
    public int updateScreeningSchedule(String setClause) {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            return screeningScheduleRepository.updateScreeningScheduleBySqlNative(connection, setClause);
        } catch (SQLException e) {
            System.out.println("[updateScreeningSchedule] 상영 스케줄 업데이트 실패");
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteScreeningSchedule(String whereClause) {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            return screeningScheduleRepository.deleteScreeningScheduleBySqlNative(connection, whereClause);
        } catch (SQLException e) {
            System.out.println("[deleteScreeningSchedule] 상영 스케줄 삭제 실패");
            e.printStackTrace();
            return 0;
        }
    }

    public ScreeningScheduleService(ScreeningScheduleRepository screeningScheduleRepository) {
        this.screeningScheduleRepository = screeningScheduleRepository;
    }
}
