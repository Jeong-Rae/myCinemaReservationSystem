package infrastructure.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.domain.screen.Screen;

public class ScreenRepository {
    public List<Screen> findAllScreens(Connection conn) {
        String sql = "SELECT * FROM screen";
        List<Screen> response = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Screen screen = Screen.RsToScreen(rs);
                response.add(screen);
            }
        } catch (SQLException e) {
            System.out.println("[findAllScreens] Screen 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }
    
    public List<Screen> findByScreenId(Connection conn, Long screenId) {
        String sql = "SELECT * FROM screen WHERE screen_id="+screenId;
        List<Screen> response = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Screen screen = Screen.RsToScreen(rs);
                response.add(screen);
            }
        } catch (SQLException e) {
            System.out.println("[findAllScreens] Screen 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }
    
    public void insertScreenBySqlNative(Connection connection, String insertData) throws SQLException {
        String sql = "INSERT INTO screen (name, is_active, row_size, col_size) VALUES " + insertData.trim();

        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    public int updateScreenBySqlNative(Connection connection, String setClause) throws SQLException {
        String sql = "UPDATE screen ";
        if (setClause != null && !setClause.trim().isEmpty()) {
            sql += setClause.trim();
        }

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    public int deleteScreenBySqlNative(Connection connection, String whereClause) throws SQLException {
        String sql = "DELETE FROM screen ";
        if (whereClause != null && !whereClause.trim().isEmpty()) {
            sql += whereClause.trim();
        }

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }
}
