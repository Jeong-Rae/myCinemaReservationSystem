package feature.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.domain.user.User;
import infrastructure.config.DatabaseConfig;
import infrastructure.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        List<User> response = new ArrayList<>();
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            response = userRepository.findAllUsers(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}