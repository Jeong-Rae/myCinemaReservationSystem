package feature.auth;

import core.domain.member.Member;

public class AuthController {
	private final AuthService authService;
	
	public Member login(String username, String password) throws IllegalArgumentException {
		try {
			return authService.login(username, password);
		} catch (IllegalArgumentException ex) {
			throw new IllegalArgumentException("잘못된 유저명 또는 비밀번호입니다.");
		}
	}
	
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
}
