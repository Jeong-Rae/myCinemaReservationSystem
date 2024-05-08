package feature.seat;

public record SeatRequest(
		int colNumber,
		int rowNumber,
		Long screeningScheduleId,
		Long screenId
) {

}
