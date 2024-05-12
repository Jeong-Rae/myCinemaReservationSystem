package feature.auth;

import java.sql.Connection;
import java.sql.SQLException;

import core.domain.member.Member;
import infrastructure.config.DatabaseConfig;
import infrastructure.repository.MemberRepository;

public class AuthService {
	private final MemberRepository memberRepository;
	
	private static final String USER_USERNAME = "user1";
	private static final String USER_PASSOWRD = "user1";
	private static final String ADMIN_USERNAME = "root";
	private static final String ADMIN_PASSWORD = "1234";
	
	
	public Member login(String username, String password) {
		try(Connection connection = DatabaseConfig.getConnectionAdmin()) {
			
			if (username.equals(USER_USERNAME) && password.equals(USER_PASSOWRD)) {
				return memberRepository.findTopOrderByMemberId(connection);
			}
			if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
				return new Member("ADMIN", null, null);
			}
			
			throw new IllegalArgumentException("잘못된 유저명 또는 비밀번호입니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
	
	public AuthService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
}
