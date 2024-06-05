package feature.reservation;

public record getReservationInfoRequest(
		Long reservationId,
		Long userId
) {}
