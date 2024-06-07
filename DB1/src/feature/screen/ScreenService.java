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
    
    public Screen findByScreenId(Long screenId) {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            List<Screen> result = screenRepository.findByScreenId(connection, screenId);
            if (result.size() == 1) {
            	return result.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public void insertScreen(String insertData) throws SQLException {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            screenRepository.insertScreenBySqlNative(connection, insertData);
        } catch (SQLException e) {
            System.out.println("[insertScreen] 상영관 등록 실패");
            throw e;
        }
    }

    public int updateScreen(String setClause) {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            return screenRepository.updateScreenBySqlNative(connection, setClause);
        } catch (SQLException e) {
            System.out.println("[updateScreen] 스크린 업데이트 실패");
            e.printStackTrace();
            return 0;
        }
    }

    public int deleteScreen(String whereClause) {
        try (Connection connection = DatabaseConfig.getConnectionAdmin()) {
            return screenRepository.deleteScreenBySqlNative(connection, whereClause);
        } catch (SQLException e) {
            System.out.println("[deleteScreen] 스크린 삭제 실패");
            e.printStackTrace();
            return 0;
        }
    }

    public ScreenService(ScreenRepository screenRepository) {
        this.screenRepository = screenRepository;
    }
}
