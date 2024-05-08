package core.domain.reservation;

public enum PaymentMethodType {
	CARD("카드 결제"), CASH("현금 결제"), GIFTCON("기프트콘 결제");

	private final String description;

	public String getDescription() {
		return this.description;
	}

	PaymentMethodType(String description) {
		this.description = description;
	}
}
