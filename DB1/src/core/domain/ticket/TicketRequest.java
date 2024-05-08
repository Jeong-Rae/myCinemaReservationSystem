package core.domain.ticket;

import feature.seat.SeatRequest;

public record TicketRequest(
		int standardPrice,
		int salePrice,
		SeatRequest seatRequest
) {
}
