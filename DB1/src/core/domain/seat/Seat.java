package core.domain.seat;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Seat {
	private Long seatId;
	private boolean isActive;
	private Long screenId;

	public Seat(boolean isActive) {
		this.isActive = isActive;
	}

	public long getSeatId() {
		return this.seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public boolean getIsActive() {
		return this.isActive;
	}

	public void updateIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public long getScreenId() {
		return this.screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	@Override
	public String toString() {
		return "Seat[" + "seatId=" + this.seatId + ", isActive=" + this.isActive + ", cinemaId=" + this.screenId + ']';
	}

	public static Seat RsToSeat(ResultSet rs) throws SQLException {
		Long seatId = rs.getLong("seat_id");
		boolean isActive = rs.getBoolean("is_active");
		Long screenId = rs.getLong("screen_id");

		Seat seat = new Seat(isActive);
		seat.setSeatId(seatId);
		seat.setScreenId(screenId);

		return seat;
	}
}
