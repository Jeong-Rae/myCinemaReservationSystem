package core.domain.ticket;

public class Ticket {
	private Long ticketId;
	private boolean isIssued;
	private int standardPrice;
	private int salePrice;
	private Long screeningScheduleId;
	private Long screenId;
	private Long reservationId;
	private Long seatId;

	public Ticket(boolean isIssued, int standardPrice, int salePrice) {
		this.isIssued = isIssued;
		this.standardPrice = standardPrice;
		this.salePrice = salePrice;
	}

	public Long getTicketId() {
		return this.ticketId;
	}

	public boolean getIsIssued() {
		return this.isIssued;
	}

	public void updateIsIssued(boolean isIssued) {
		this.isIssued = isIssued;
	}

	public int getStandardPrice() {
		return this.standardPrice;
	}

	public void updateStandardPrice(int standardPrice) {
		this.standardPrice = standardPrice;
	}

	public int getSalePrice() {
		return this.salePrice;
	}

	public void updateSalePrice(int salePrice) {
		this.salePrice = salePrice;
	}

	public Long getScreeningScheduleId() {
		return this.screeningScheduleId;
	}

	public void setScreeningScheduleId(Long screeningScheduleId) {
		this.screeningScheduleId = screeningScheduleId;
	}

	public Long getScreenId() {
		return this.screenId;
	}

	public void setScreenId(Long cinemaId) {
		this.screenId = cinemaId;
	}

	public Long getReservationId() {
		return this.reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public Long getSeatId() {
		return seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	@Override
	public String toString() {
		return "Ticket[" + "ticketId=" + this.ticketId + ", isIssued=" + this.isIssued + ", standardPrice="
				+ this.standardPrice + ", salePrice=" + this.salePrice + ", screeningScheduleId="
				+ this.screeningScheduleId + ", screenId=" + this.screenId + ", reservationId=" + this.reservationId
				+ ", seatId=" + this.seatId + ']';
	}
}
