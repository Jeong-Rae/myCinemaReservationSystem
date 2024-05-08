package feature.screen;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.screen.Screen;
import infrastructure.config.DatabaseConfig;
import infrastructure.repository.ScreenRepository;

public class ScreenService {
    private final ScreenRepository screenRepository;

    public List<Screen> findAllScreens() {
        List<Screen> response = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            response = screenRepository.findAllScreens(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }

    public ScreenService(ScreenRepository screenRepository) {
        this.screenRepository = screenRepository;
    }
}