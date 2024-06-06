package feature.auth;

public class AuthViewModel {
	private final AuthController authController;
	
	public AuthViewModel(AuthController authController) {
		this.authController = authController;
	}
	
	public boolean managerLoginButtonClicked(String id, String password) {
		if (id == "user1") {
			return false;
		}
		
		try {
			this.authController.login(id, password);
		} catch (IllegalArgumentException ex) {
			return false;
		}
		
		return true;
	}
	
	public boolean userLoginButtonClicked(String id, String password) {
		if (id == "root") {
			return false;
		}
		
		try {
			this.authController.login(id, password);
		} catch (IllegalArgumentException ex) {
			return false;
		}
		
		return true;
	}
}
