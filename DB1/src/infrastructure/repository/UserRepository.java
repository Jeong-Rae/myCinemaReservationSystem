package infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.user.User;

public class UserRepository {
    public List<User> findAllUsers(Connection conn) {
        String sql = "SELECT * FROM user";
        List<User> response = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                User user = User.RsToUser(rs);
                response.add(user);
            }
        } catch (SQLException e) {
            System.out.println("User 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }

    public UserRepository() {
    }
}
