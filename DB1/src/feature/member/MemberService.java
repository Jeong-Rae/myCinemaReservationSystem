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

    public MemberService(MemberRepository userRepository) {
        this.userRepository = userRepository;
    }
}