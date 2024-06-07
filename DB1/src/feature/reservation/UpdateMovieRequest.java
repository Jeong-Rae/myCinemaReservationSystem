package feature.reservation;

import core.domain.reservation.Reservation;

public record UpdateMovieRequest(
		Reservation reservation,
		Long movieId
) {

}
