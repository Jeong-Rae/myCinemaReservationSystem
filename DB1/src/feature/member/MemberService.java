package feature.member;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.member.Member;
import infrastructure.config.DatabaseConfig;
import infrastructure.repository.MemberRepository;

public class MemberService {
    private final MemberRepository userRepository;

    public List<Member> findAllMembers() {
        List<Member> response = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            response = userRepository.findAllUsers(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }
    
    public void insertMember(String insertData) throws SQLException {
    	try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
    		userRepository.insertMemberBySqlNative(connection, insertData);
    	} catch (SQLException e) {
    		System.out.println("[insertMember] 멤버 등록 실패");
    		throw e;
		}
    }

    public int updateMember(String setClause) {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            return userRepository.updateMemberBySqlNative(connection, setClause);
        } catch (SQLException e) {
            System.out.println("[updateMember] 멤버 업데이트 실패");
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteMember(String whereClause) {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            return userRepository.deleteMemberBySqlNative(connection, whereClause);
        } catch (SQLException e) {
            System.out.println("[deleteMember] 멤버 삭제 실패");
            e.printStackTrace();
            return 0;
        }
    }

    public MemberService(MemberRepository userRepository) {
        this.userRepository = userRepository;
    }
}
