package infrastructure.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.domain.screeningschedule.ScreeningSchedule;

public class ScreeningScheduleRepository {
    public List<ScreeningSchedule> findAllScreeningSchedules(Connection conn) {
        String sql = "SELECT * FROM screening_schedule";
        List<ScreeningSchedule> response = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ScreeningSchedule screeningSchedule = ScreeningSchedule.RsToScreeningSchedule(rs);
                response.add(screeningSchedule);
            }
        } catch (SQLException e) {
            System.out.println("[findAllScreeningSchedules] ScreeningSchedule 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }

    public List<ScreeningSchedule> findByMovie(Connection connection, Long movieId) {
        String sql = "SELECT * FROM screening_schedule WHERE movie_id = " + movieId;
        List<ScreeningSchedule> response = new ArrayList<>();

        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                ScreeningSchedule screeningSchedule = ScreeningSchedule.RsToScreeningSchedule(rs);
                response.add(screeningSchedule);
            }
        } catch (SQLException e) {
            System.out.println("[findByMovie] ScreeningSchedule 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }
    
    public void insertScreeningScheduleBySqlNative(Connection connection, String insertData) throws SQLException {
        String sql = "INSERT INTO screening_schedule (start_date, start_time, day_of_week, session_number, movie_id, screen_id) VALUES " + insertData.trim();

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }
    
    public int updateScreeningScheduleBySqlNative(Connection connection, String setClause) throws SQLException {
        String sql = "UPDATE screening_schedule ";
        if (setClause != null && !setClause.trim().isEmpty()) {
            sql += setClause.trim();
        }

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    public int deleteScreeningScheduleBySqlNative(Connection connection, String whereClause) throws SQLException {
        String sql = "DELETE FROM screening_schedule ";
        if (whereClause != null && !whereClause.trim().isEmpty()) {
            sql += whereClause.trim();
        }

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }
}
