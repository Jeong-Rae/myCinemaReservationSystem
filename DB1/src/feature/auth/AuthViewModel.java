package feature.auth;

public class AuthViewModel {
	private final AuthController authController;
	public AuthViewModelDelegate delegate;
	private String id = "";
	private String password = "";
	
	public AuthViewModel(AuthController authController) {
		this.authController = authController;
	}
	
	public boolean managerLoginButtonClicked() {
		if (id.equals("user1")) {
			return false;
		}
		
		try {
			if(this.authController.login(this.id, this.password) == null) {
				return false;
			}
		} catch (Throwable ex) {
			return false;
		}
		
		this.delegate.managerLoginCompleted();
		
		return true;
	}
	
	public boolean userLoginButtonClicked() {
		if (id.equals("root")) {
			return false;
		}
		
		try {
			if(this.authController.login(this.id, this.password) == null) {
				return false;
			}
		} catch (Throwable ex) {
			return false;
		}
		
		this.delegate.userLoginCompleted();
		
		return true;
	}
	
	public void idFieldReleased(String text) {
		this.id = text;
	}
	
	public void passwordFieldReleased(String text) {
		this.password = text;
	}
}
