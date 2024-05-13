package infrastructure.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import core.domain.member.Member;

public class MemberRepository {
    public List<Member> findAllUsers(Connection conn) {
        String sql = "SELECT * FROM USER";
        List<Member> response = new ArrayList<>();

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Member member = Member.RsToUser(rs);
                response.add(member);
            }
        } catch (SQLException e) {
            System.out.println("[findAllUsers] User 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }
    
    public Member findTopOrderByMemberId(Connection connection) {
    	String sql = "SELECT * FROM USER ORDER BY member_id LIMIT 1";
    	
    	try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                return Member.RsToUser(rs);
            }
        } catch (SQLException e) {
            System.out.println("[findTopOrderByMemberId] User 테이블 조회 실패");
            e.printStackTrace();
        }
    	
    	return null;
    }

    public int updateMemberBySqlNative(Connection connection, String setClause) throws SQLException {
        String sql = "UPDATE USER ";
        if (setClause != null && !setClause.trim().isEmpty()) {
            sql += setClause.trim();
        }

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }

    public int deleteMemberBySqlNative(Connection connection, String whereClause) throws SQLException {
        String sql = "DELETE FROM USER ";
        if (whereClause != null && !whereClause.trim().isEmpty()) {
            sql += whereClause.trim();
        }

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }
    
    public MemberRepository() {
    }
}
