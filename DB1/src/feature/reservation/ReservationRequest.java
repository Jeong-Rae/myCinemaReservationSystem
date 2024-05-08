package feature.reservation;

import java.util.List;

import core.domain.reservation.PaymentMethodType;
import core.domain.ticket.TicketRequest;

public record ReservationRequest(
		List<TicketRequest> ticketRequests,
		PaymentMethodType paymentMethodType,
		Long memberId
) {
}
