package core.domain.reservation;

public enum PaymentStatusType {
	FAILED("실패"), IN_PROGRESS("진행중"), COMPLETED("완료");

	private final String description;

	public String getDescription() {
		return this.description;
	}

	PaymentStatusType(String description) {
		this.description = description;
	}
}
