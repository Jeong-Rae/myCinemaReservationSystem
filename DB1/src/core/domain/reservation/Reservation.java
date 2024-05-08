package core.domain.reservation;

import java.util.Date;

public class Reservation {
	private Long reservationId;
	private PaymentMethodType paymentMethod;
	private PaymentStatusType paymentStatus;
	private int amount;
	private Date paymentDate;
	private Long userId;

	public Reservation(PaymentMethodType paymentMethod, PaymentStatusType paymentStatus, int amount, Date paymentDate) {
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}

	public Long getReservationId() {
		return this.reservationId;
	}

	public PaymentMethodType getPaymentMethod() {
		return this.paymentMethod;
	}

	public void updatePaymentMethod(PaymentMethodType paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentStatusType getPaymentStatus() {
		return this.paymentStatus;
	}

	public void updatePaymentStatus(PaymentStatusType paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public int getAmount() {
		return this.amount;
	}

	public void updateAmount(int amount) {
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void updatePaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Reservation[" + "reservationId=" + this.reservationId + ", paymentMethod='" + this.paymentMethod + '\''
				+ ", paymentStatus='" + this.paymentStatus + '\'' + ", amount=" + this.amount + ", paymentDate="
				+ this.paymentDate + ", userId=" + this.userId + ']';
	}
}
