package infrastructure.repository.db2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
	private static final String DB_URL = "jdbc:mysql://localhost:3306";
	private static final String ROOT = "root";
	private static final String ROOT_PASSWORD = "1234";
	
	public void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, ROOT, ROOT_PASSWORD);
             Statement stmt = conn.createStatement()) {

        	initializeDB2(stmt);

            initMovie(stmt);
            initCinema(stmt);
            initScreeningSchedule(stmt);
            initSeat(stmt);
            initUser(stmt);
            initReservation(stmt);
            initTicket(stmt);

            finalizeDatabaseSettings(stmt);

            System.out.println("데이터베이스 초기화 성공");
        } catch (SQLException e) {
            System.out.println("데이터베이스 초기화 실패");
            e.printStackTrace();
        }
    }

    private void initializeDB2(Statement stmt) throws SQLException {
        executeUpdate("SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0", stmt);
        executeUpdate("SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0", stmt);
        executeUpdate("SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION'", stmt);
        executeUpdate("DROP SCHEMA IF EXISTS `db2`", stmt);
        executeUpdate("CREATE SCHEMA IF NOT EXISTS `db2` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci", stmt);
        executeUpdate("USE `db2`", stmt);
    }

    private void initMovie(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`movie`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`movie` (
                  `movie_id` BIGINT NOT NULL AUTO_INCREMENT,
                  `title` VARCHAR(128) NOT NULL,
                  `duration` TIME NOT NULL,
                  `rating` ENUM('전체관람가', '12세이상관람가', '15세이상관람가', '청소년관람불가') NOT NULL,
                  `director` VARCHAR(32) NOT NULL,
                  `actor` VARCHAR(32) NOT NULL,
                  `genre` VARCHAR(32) NOT NULL,
                  `description` VARCHAR(128) NOT NULL,
                  `relase_date` DATETIME NOT NULL,
                  `score` DECIMAL(4,2) NOT NULL,
                  PRIMARY KEY (`movie_id`),
                  UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE
                ) ENGINE = InnoDB
            """, stmt);
    }

    private void initCinema(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`cinema`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`cinema` (
                  `cinema_id` BIGINT NOT NULL AUTO_INCREMENT,
                  `is_active` TINYINT NOT NULL,
                  `seat_row` INT NOT NULL,
                  `seat_col` INT NOT NULL,
                  PRIMARY KEY (`cinema_id`)
                ) ENGINE = InnoDB
            """, stmt);
    }

    private void initScreeningSchedule(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`screening_schedule`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`screening_schedule` (
                  `schedule_id` BIGINT NOT NULL AUTO_INCREMENT,
                  `start_date` DATE NOT NULL,
                  `start_time` TIME NOT NULL,
                  `day_of_week` ENUM('SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT') NOT NULL,
                  `session_number` INT NOT NULL,
                  `movie_id` BIGINT NOT NULL,
                  `cinema_id` BIGINT NOT NULL,
                  PRIMARY KEY (`schedule_id`),
                  INDEX `fk_screening_schedule_movie_idx` (`movie_id` ASC) VISIBLE,
                  INDEX `fk_screening_schedule_cinema1_idx` (`cinema_id` ASC) VISIBLE,
                  CONSTRAINT `fk_screening_schedule_movie`
                    FOREIGN KEY (`movie_id`)
                    REFERENCES `db2`.`movie` (`movie_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION,
                  CONSTRAINT `fk_screening_schedule_cinema1`
                    FOREIGN KEY (`cinema_id`)
                    REFERENCES `db2`.`cinema` (`cinema_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION
                ) ENGINE = InnoDB
            """, stmt);
    }

    private void initSeat(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`seat`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`seat` (
                  `seat_id` BIGINT NOT NULL,
                  `is_active` TINYINT NOT NULL,
                  `cinema_id` BIGINT NOT NULL,
                  PRIMARY KEY (`seat_id`),
                  INDEX `fk_seat_cinema1_idx` (`cinema_id` ASC) VISIBLE,
                  CONSTRAINT `fk_seat_cinema1`
                    FOREIGN KEY (`cinema_id`)
                    REFERENCES `db2`.`cinema` (`cinema_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION
                ) ENGINE = InnoDB
            """, stmt);
    }

    private void initUser(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`user`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`user` (
                  `user_id` BIGINT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(32) NULL DEFAULT NULL,
                  `phone_number` VARCHAR(16) NULL DEFAULT NULL,
                  `email` VARCHAR(32) NULL DEFAULT NULL,
                  PRIMARY KEY (`user_id`)
                ) ENGINE = InnoDB
            """, stmt);
    }

    private void initReservation(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`reservation`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`reservation` (
                  `reservation_id` INT NOT NULL AUTO_INCREMENT,
                  `payment_method` ENUM('CARD', 'CASH', 'GIFTCON') NOT NULL,
                  `payment_status` ENUM('FAILED', 'IN_PROGRESS', 'COMPLETED') NOT NULL,
                  `amount` INT NOT NULL,
                  `payment_date` DATE NOT NULL,
                  `user_id` BIGINT NOT NULL,
                  PRIMARY KEY (`reservation_id`),
                  INDEX `fk_reservation_user1_idx` (`user_id` ASC) VISIBLE,
                  CONSTRAINT `fk_reservation_user1`
                    FOREIGN KEY (`user_id`)
                    REFERENCES `db2`.`user` (`user_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION
                ) ENGINE = InnoDB
            """, stmt);
    }

    private void initTicket(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`ticket`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`ticket` (
                  `ticket_id` BIGINT NOT NULL AUTO_INCREMENT,
                  `is_issued` TINYINT NOT NULL,
                  `standard_price` INT NOT NULL,
                  `sale_price` INT NOT NULL,
                  `screening_schedule_id` BIGINT NOT NULL,
                  `cinema_id` BIGINT NOT NULL,
                  `reservation_id` INT NOT NULL,
                  `seat_id` BIGINT NOT NULL,
                  PRIMARY KEY (`ticket_id`),
                  INDEX `fk_ticket_screening_schedule1_idx` (`screening_schedule_id` ASC) VISIBLE,
                  INDEX `fk_ticket_cinema1_idx` (`cinema_id` ASC) VISIBLE,
                  INDEX `fk_ticket_reservation1_idx` (`reservation_id` ASC) VISIBLE,
                  INDEX `fk_ticket_seat1_idx` (`seat_id` ASC) VISIBLE,
                  CONSTRAINT `fk_ticket_screening_schedule1`
                    FOREIGN KEY (`screening_schedule_id`)
                    REFERENCES `db2`.`screening_schedule` (`schedule_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION,
                  CONSTRAINT `fk_ticket_cinema1`
                    FOREIGN KEY (`cinema_id`)
                    REFERENCES `db2`.`cinema` (`cinema_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION,
                  CONSTRAINT `fk_ticket_reservation1`
                    FOREIGN KEY (`reservation_id`)
                    REFERENCES `db2`.`reservation` (`reservation_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION,
                  CONSTRAINT `fk_ticket_seat1`
                    FOREIGN KEY (`seat_id`)
                    REFERENCES `db2`.`seat` (`seat_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION
                ) ENGINE = InnoDB
            """, stmt);
    }

    private void finalizeDatabaseSettings(Statement stmt) throws SQLException {
        executeUpdate("SET SQL_MODE=@OLD_SQL_MODE", stmt);
        executeUpdate("SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS", stmt);
        executeUpdate("SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS", stmt);
    }

    private void executeUpdate(String sql, Statement stmt) throws SQLException {
        stmt.executeUpdate(sql);
    }
    
    public DatabaseInitializer() {
	}
   
}