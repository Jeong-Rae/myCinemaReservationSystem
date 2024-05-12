package infrastructure.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.member.Member;

public class MemberRepository {
    public List<Member> findAllUsers(Connection conn) {
        String sql = "SELECT * FROM USER";
        List<Member> response = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Member member = Member.RsToUser(rs);
                response.add(member);
            }
        } catch (SQLException e) {
            System.out.println("User 테이블 조회 실패");
            e.printStackTrace();
        }

        return response;
    }
    
    public Member findTopOrderByMemberId(Connection connection) {
    	String sql = "SELECT * FROM USER ORDER BY member_id LIMIT 1";
    	
    	try (PreparedStatement pstmt = connection.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                return Member.RsToUser(rs);
            }
        } catch (SQLException e) {
            System.out.println("User 테이블 조회 실패");
            e.printStackTrace();
        }
    	
    	return null;
    }

    public MemberRepository() {
    }
}
