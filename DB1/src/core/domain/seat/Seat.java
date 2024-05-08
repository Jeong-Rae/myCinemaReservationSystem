package core.domain.seat;

public class Seat {
	private Long seatId;
	private boolean isActive;
	private Long cinemaId;

	public Seat(long seatId, boolean isActive) {
		this.seatId = seatId;
		this.isActive = isActive;
	}

	public long getSeatId() {
		return this.seatId;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void updateIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public long getCinemaId() {
		return this.cinemaId;
	}

	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
	}

	@Override
	public String toString() {
		return "Seat[" + "seatId=" + this.seatId + ", isActive=" + this.isActive + ", cinemaId=" + this.cinemaId + ']';
	}
}
