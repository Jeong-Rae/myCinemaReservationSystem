package feature.screeningschedule;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.screeningschedule.ScreeningSchedule;
import infrastructure.config.DatabaseConfig;
import infrastructure.repository.ScreeningScheduleRepository;

public class ScreeningScheduleService {
	private final ScreeningScheduleRepository screeningScheduleRepository;

	public List<ScreeningSchedule> findAllScreeningSchedules() {
		List<ScreeningSchedule> response = new ArrayList<>();
		try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
			response = screeningScheduleRepository.findAllScreeningSchedules(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return response;
	}

	public ScreeningScheduleService(ScreeningScheduleRepository screeningScheduleRepository) {
		this.screeningScheduleRepository = screeningScheduleRepository;
	}
}