package domain.user;

public class User {
	private Long userId;
	private String name;
	private String phoneNumber;
	private String email;

	public User(String name, String phoneNumber, String email) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public long getUserId() {
		return this.userId;
	}

	public String getName() {
		return this.name;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void updatePhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void updateEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User[" + "userId=" + this.userId + ", name='" + this.name + '\'' + ", phoneNumber='" + this.phoneNumber
				+ '\'' + ", email='" + this.email + '\'' + ']';
	}
}
