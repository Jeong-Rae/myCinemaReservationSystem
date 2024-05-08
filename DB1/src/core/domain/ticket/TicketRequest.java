package core.domain.ticket;

public record TicketRequest(
		int standardPrice,
		int salePrice,
		Long screeningScheduleId,
		Long screenId,
		Long seatId
) {
}
