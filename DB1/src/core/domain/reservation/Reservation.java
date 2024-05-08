package core.domain.reservation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Reservation {
	private Long reservationId;
	private PaymentMethodType paymentMethod;
	private PaymentStatusType paymentStatus;
	private int amount;
	private Date paymentDate;
	private Long memberId;

	public Reservation(PaymentMethodType paymentMethod, PaymentStatusType paymentStatus, int amount, Date paymentDate) {
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
		this.amount = amount;
		this.paymentDate = paymentDate;
	}

	public Long getReservationId() {
		return this.reservationId;
	}
	
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
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

	public long getMemberId() {
		return this.memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "Reservation[" + "reservationId=" + this.reservationId + ", paymentMethod='" + this.paymentMethod + '\''
				+ ", paymentStatus='" + this.paymentStatus + '\'' + ", amount=" + this.amount + ", paymentDate="
				+ this.paymentDate + ", userId=" + this.memberId + ']';
	}
	
	public static Reservation RsToReservation(ResultSet rs) throws SQLException {
	    Long reservationId = rs.getLong("reservation_id");
	    PaymentMethodType paymentMethod = PaymentMethodType.valueOf(rs.getString("payment_method"));
	    PaymentStatusType paymentStatus = PaymentStatusType.valueOf(rs.getString("payment_status"));
	    int amount = rs.getInt("amount");
	    Date paymentDate = rs.getDate("payment_date");
	    Long memberId = rs.getLong("member_id");

	    Reservation reservation = new Reservation(paymentMethod, paymentStatus, amount, paymentDate);
	    reservation.setReservationId(reservationId);
	    reservation.setMemberId(memberId);
	    
	    return reservation;
	}
}
