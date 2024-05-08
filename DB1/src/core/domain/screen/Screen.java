package core.domain.screen;

public class Screen {
	private Long screenId;
	private String name;
	private boolean isActive;
	private int seatRow;
	private int seatCol;

	public Screen(String name, boolean isActive, int seatRow, int seatCol) {
		this.name = name;
		this.isActive = isActive;
		this.seatRow = seatRow;
		this.seatCol = seatCol;
	}

	public long getCinemaId() {
		return this.screenId;
	}

	public void updateCinemaId(Long cinemaId) {
		this.screenId = cinemaId;
	}

	public String getName() {
		return this.name;
	}

	public void updateName(String name) {
		this.name = name;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void updateIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public int getSeatRow() {
		return this.seatRow;
	}

	public void updateSeatRow(int seatRow) {
		this.seatRow = seatRow;
	}

	public int getSeatCol() {
		return this.seatCol;
	}

	public void updateSeatCol(int seatCol) {
		this.seatCol = seatCol;
	}

	@Override
	public String toString() {
		return "Cinema[" + "cinemaId=" + this.screenId + ", name=\"" + this.name + "\"" + ", isActive=" + this.isActive
				+ ", seatRow=" + this.seatRow + ", seatCol=" + this.seatCol + ']';
	}
}
