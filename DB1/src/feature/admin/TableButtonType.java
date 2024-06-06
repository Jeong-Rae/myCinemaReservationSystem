package feature.admin;

public enum TableButtonType {
	MEMBER("회원"),
	MOVIE("영화"),
	RESERVATION("예약"),
	SCHEDULE("상영일정"),
	SCREEN("상영관"),
	SEAT("좌석"),
	TICKET("티켓");

	private final String description;

	public String getDescription() {
		return this.description;
	}

	TableButtonType(String description) {
		this.description = description;
	}
}
