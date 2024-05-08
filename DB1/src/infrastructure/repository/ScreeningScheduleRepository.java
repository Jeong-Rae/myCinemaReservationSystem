package infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.screeningschedule.ScreeningSchedule;

public class ScreeningScheduleRepository {
    public List<ScreeningSchedule> findAllScreeningSchedules(Connection conn) {
        String sql = "SELECT * FROM screening_schedule";
        List<ScreeningSchedule> response = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ScreeningSchedule screeningSchedule = ScreeningSchedule.RsToScreeningSchedule(rs);
                response.add(screeningSchedule);
            }
        } catch (SQLException e) {
            System.out.println("ScreeningSchedule 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }

    public ScreeningScheduleRepository() {
    }
}