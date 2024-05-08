package core.domain.member;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Member {
	private Long memberId;
	private String name;
	private String phoneNumber;
	private String email;

	public Member(String name, String phoneNumber, String email) {
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public long getMemberId() {
		return this.memberId;
	}
	
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
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
		return "User[" + "userId=" + this.memberId + ", name='" + this.name + '\'' + ", phoneNumber='" + this.phoneNumber
				+ '\'' + ", email='" + this.email + '\'' + ']';
	}
	
	public static Member RsToUser(ResultSet rs) throws SQLException {
	    Long memberId = rs.getLong("member_id");
	    String name = rs.getString("name");
	    String phoneNumber = rs.getString("phone_number");
	    String email = rs.getString("email");

	    Member user = new Member(name, phoneNumber, email);
	    user.setMemberId(memberId);

	    return user;
	}
}
