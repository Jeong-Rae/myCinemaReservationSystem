package infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.screen.Screen;

public class ScreenRepository {
    public List<Screen> findAllScreens(Connection conn) {
        String sql = "SELECT * FROM screen";
        List<Screen> response = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Screen screen = Screen.RsToScreen(rs);
                response.add(screen);
            }
        } catch (SQLException e) {
            System.out.println("Screen 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }

    public ScreenRepository() {
    }
}