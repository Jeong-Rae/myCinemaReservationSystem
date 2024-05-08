package core.domain.cinema;

public class Cinema {
	private Long cinemaId;
	private boolean isActive;
	private int seatRow;
	private int seatCol;

	public Cinema(boolean isActive, int seatRow, int seatCol) {
		this.isActive = isActive;
		this.seatRow = seatRow;
		this.seatCol = seatCol;
	}

	public long getCinemaId() {
		return this.cinemaId;
	}

	public void updateCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
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
		return "Cinema[" + "cinemaId=" + this.cinemaId + ", isActive=" + this.isActive + ", seatRow=" + this.seatRow
				+ ", seatCol=" + this.seatCol + ']';
	}
}
