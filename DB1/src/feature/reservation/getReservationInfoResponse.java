package feature.reservation;

import java.util.List;

import core.domain.reservation.Reservation;
import core.domain.ticket.Ticket;

public record getReservationInfoResponse(
		Reservation reservation,
		List<Ticket> tickets
) {}
