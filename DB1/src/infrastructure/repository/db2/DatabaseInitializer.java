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

            createMovie(stmt);
            createScreen(stmt);
            createScreeningSchedule(stmt);
            createSeat(stmt);
            createMember(stmt);
            createReservation(stmt);
            createTicket(stmt);

            finalizeDatabaseSettings(stmt);

            createUserAccount(stmt);
            
            insertMovieExampleSet(stmt);
            insertScreenExampleSet(stmt);
            insertScreeningScheduleExampleSet(stmt);
            insertSeatExampleSet(stmt); 
            insertUserExampleSet(stmt);
            insertReservationExampleSet(stmt);
            insertTicketExampleSet(stmt);

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

    private void createUserAccount(Statement stmt) throws SQLException {
        // 프로시저 생성 쿼리
        String createProcedure = """
            CREATE PROCEDURE CreateUserIfNeeded()
            BEGIN
                DECLARE userExists INT;
                SET userExists = (SELECT COUNT(*) FROM mysql.user WHERE user = 'user1' AND host = 'localhost');
            
                IF userExists = 0 THEN
                    CREATE USER 'user1'@'localhost' IDENTIFIED BY 'user1';
                    GRANT SELECT, INSERT, UPDATE, DELETE ON db2.* TO 'user1'@'localhost';
                    FLUSH PRIVILEGES;
                END IF;
            END
        """;

        // 프로시저 호출 쿼리
        String callProcedure = "CALL CreateUserIfNeeded()";

        // 프로시저 삭제 쿼리
        String dropProcedure = "DROP PROCEDURE CreateUserIfNeeded";

        // 프로시저 생성
        stmt.executeUpdate(createProcedure);
        // 프로시저 호출
        stmt.executeUpdate(callProcedure);
        // 프로시저 삭제
        stmt.executeUpdate(dropProcedure);
    }

    private void createMovie(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`movie`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`movie` (
                  `movie_id` BIGINT NOT NULL AUTO_INCREMENT,
                  `title` VARCHAR(128) NOT NULL,
                  `duration` TIME NOT NULL,
                  `rating` ENUM("전체관람가", "12세이상관람가", "15세이상관람가", "청소년관람불가") NOT NULL,
                  `director` VARCHAR(32) NOT NULL,
                  `actor` VARCHAR(32) NOT NULL,
                  `genre` VARCHAR(32) NOT NULL,
                  `description` VARCHAR(128) NOT NULL,
                  `relase_date` DATE NOT NULL,
                  `score` DECIMAL(4,2) NOT NULL,
                  PRIMARY KEY (`movie_id`),
                  UNIQUE INDEX `title_UNIQUE` (`title` ASC) VISIBLE
                ) ENGINE = InnoDB
            """, stmt);
    }

    private void createScreen(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`screen`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`screen` (
                  `screen_id` BIGINT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(16) NOT NULL,
                  `is_active` TINYINT NOT NULL DEFAULT 1,
                  `row_size` INT NOT NULL,
                  `col_size` INT NOT NULL,
                  PRIMARY KEY (`screen_id`)
                ) ENGINE = InnoDB
            """, stmt);
    }

    private void createScreeningSchedule(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`screening_schedule`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`screening_schedule` (
                  `schedule_id` BIGINT NOT NULL AUTO_INCREMENT,
                  `start_date` DATE NOT NULL,
                  `start_time` TIME NOT NULL,
                  `day_of_week` ENUM("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT") NOT NULL,
                  `session_number` INT NOT NULL,
                  `movie_id` BIGINT NOT NULL,
                  `screen_id` BIGINT NOT NULL,
                  PRIMARY KEY (`schedule_id`),
                  INDEX `fk_screening_schedule_movie_idx` (`movie_id` ASC) VISIBLE,
                  INDEX `fk_screening_schedule_cinema1_idx` (`screen_id` ASC) VISIBLE,
                  CONSTRAINT `fk_screening_schedule_movie`
                    FOREIGN KEY (`movie_id`)
                    REFERENCES `db2`.`movie` (`movie_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION,
                  CONSTRAINT `fk_screening_schedule_cinema1`
                    FOREIGN KEY (`screen_id`)
                    REFERENCES `db2`.`screen` (`screen_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION
                ) ENGINE = InnoDB
            """, stmt);
    }

    private void createSeat(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`seat`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`seat` (
                  `seat_id` BIGINT NOT NULL AUTO_INCREMENT,
                  `is_active` TINYINT NOT NULL DEFAULT 1,
                  `row_number` INT NOT NULL,
                  `col_number` INT NOT NULL,
                  `screen_id` BIGINT NOT NULL,
                  `screening_schedule_id` BIGINT NOT NULL,
                  PRIMARY KEY (`seat_id`),
                  INDEX `fk_seat_cinema1_idx` (`screen_id` ASC) VISIBLE,
                  INDEX `fk_seat_screening_schedule1_idx` (`screening_schedule_id` ASC) VISIBLE,
                  CONSTRAINT `fk_seat_cinema1`
                    FOREIGN KEY (`screen_id`)
                    REFERENCES `db2`.`screen` (`screen_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION,
                  CONSTRAINT `fk_seat_screening_schedule1`
                    FOREIGN KEY (`screening_schedule_id`)
                    REFERENCES `db2`.`screening_schedule` (`schedule_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION)
                ENGINE = InnoDB;
            """, stmt);
    }

    private void createMember(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`member`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`member` (
                  `member_id` BIGINT NOT NULL AUTO_INCREMENT,
                  `name` VARCHAR(32) NOT NULL,
                  `phone_number` VARCHAR(16) NOT NULL,
                  `email` VARCHAR(32) NOT NULL,
                  PRIMARY KEY (`member_id`),
                  UNIQUE INDEX `phone_number_UNIQUE` (`phone_number` ASC) VISIBLE,
                  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
                ENGINE = InnoDB;
            """, stmt);
    }

    private void createReservation(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`reservation`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`reservation` (
                  `reservation_id` BIGINT NOT NULL AUTO_INCREMENT,
                  `payment_method` ENUM("CARD", "CASH", "GIFTCON") NOT NULL,
                  `payment_status` ENUM("FAILED", "IN_PROGRESS", "COMPLETED") NOT NULL,
                  `amount` INT NOT NULL,
                  `payment_date` DATE NOT NULL,
                  `member_id` BIGINT NOT NULL,
                  PRIMARY KEY (`reservation_id`),
                  INDEX `fk_reservation_user1_idx` (`member_id` ASC) VISIBLE,
                  CONSTRAINT `fk_reservation_user1`
                    FOREIGN KEY (`member_id`)
                    REFERENCES `db2`.`member` (`member_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION
                ) ENGINE = InnoDB
            """, stmt);
    }

    private void createTicket(Statement stmt) throws SQLException {
        executeUpdate("DROP TABLE IF EXISTS `db2`.`ticket`", stmt);
        executeUpdate("""
                CREATE TABLE IF NOT EXISTS `db2`.`ticket` (
                  `ticket_id` BIGINT NOT NULL AUTO_INCREMENT,
                  `is_issued` TINYINT NOT NULL DEFAULT 0,
                  `standard_price` INT NOT NULL,
                  `sale_price` INT NOT NULL,
                  `screening_schedule_id` BIGINT NOT NULL,
                  `screen_id` BIGINT NOT NULL,
                  `reservation_id` BIGINT NOT NULL,
                  `seat_id` BIGINT NOT NULL,
                  PRIMARY KEY (`ticket_id`),
                  INDEX `fk_ticket_screening_schedule1_idx` (`screening_schedule_id` ASC) VISIBLE,
                  INDEX `fk_ticket_cinema1_idx` (`screen_id` ASC) VISIBLE,
                  INDEX `fk_ticket_reservation1_idx` (`reservation_id` ASC) VISIBLE,
                  INDEX `fk_ticket_seat1_idx` (`seat_id` ASC) VISIBLE,
                  CONSTRAINT `fk_ticket_screening_schedule1`
                    FOREIGN KEY (`screening_schedule_id`)
                    REFERENCES `db2`.`screening_schedule` (`schedule_id`)
                    ON DELETE NO ACTION
                    ON UPDATE NO ACTION,
                  CONSTRAINT `fk_ticket_cinema1`
                    FOREIGN KEY (`screen_id`)
                    REFERENCES `db2`.`screen` (`screen_id`)
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
    
    private void insertMovieExampleSet(Statement stmt) throws SQLException {
        executeUpdate("""
            INSERT INTO movie VALUES
            (1, '기생충', '02:12:00', '15세이상관람가', '봉준호', '송강호', '드라마', '두 가정의 격차가 드러나는 사회 비판적 이야기', '2023-05-30 00:00:00', 8.6),
            (2, '베테랑', '02:04:00', '15세이상관람가', '류승완', '황정민', '액션', '불법행위를 하는 재벌 3세를 추적하는 형사의 이야기', '2023-08-05 00:00:00', 7.9),
            (3, '부산행', '01:58:00', '청소년관람불가', '연상호', '공유', '스릴러', '좀비 바이러스가 퍼지는 기차 안에서 벌어지는 생존기', '2023-07-20 00:00:00', 7.5),
            (4, '도둑들', '02:15:00', '15세이상관람가', '최동훈', '전지현', '범죄', '마카오 카지노에서 벌어지는 대규모 도둑질', '2023-07-25 00:00:00', 7.2),
            (5, '7번방의 선물', '02:07:00', '12세이상관람가', '이환경', '류승룡', '드라마', '정신지체를 가진 아버지와 그의 딸 이야기', '2023-01-23 00:00:00', 8.2),
            (6, '암살', '02:20:00', '15세이상관람가', '최동훈', '전지현', '역사', '일제강점기, 독립군의 일본 관료 암살 작전', '2023-02-22 00:00:00', 7.8),
            (7, '괴물', '01:59:00', '12세이상관람가', '봉준호', '송강호', '공포', '한강에 나타난 괴물과 한 가족의 싸움', '2023-03-27 00:00:00', 7.0),
            (8, '왕의 남자', '01:59:00', '12세이상관람가', '이준익', '감우성', '역사', '조선시대 광대들의 이야기', '2023-12-29 00:00:00', 7.5),
            (9, '내부자들', '02:10:00', '청소년관람불가', '우민호', '이병헌', '드라마', '정치와 비즈니스의 내부 비리를 파헤치는 이야기', '2023-11-19 00:00:00', 7.8),
            (10, '신과함께-죄와 벌', '02:20:00', '12세이상관람가', '김용화', '하정우', '판타지', '사후 세계에서 벌어지는 재판 과정을 그린 이야기', '2023-04-20 00:00:00', 7.3),
            (11, '택시운전사', '02:17:00', '15세이상관람가', '장훈', '송강호', '드라마', '1980년 광주 민주화 운동을 목격한 택시운전사 이야기', '2023-09-02 00:00:00', 8.9),
            (12, '아저씨', '02:00:00', '청소년관람불가', '이정범', '원빈', '액션', '납치된 이웃 소녀를 구하기 위한 남자의 사투', '2023-06-04 00:00:00', 7.8),
            (13, '국제시장', '02:06:00', '12세이상관람가', '윤제균', '황정민', '드라마', '한국 현대사를 배경으로 한 가족의 이야기', '2023-12-17 00:00:00', 6.8),
            (14, '최종병기 활', '02:02:00', '12세이상관람가', '김한민', '박해일', '역사', '조선시대, 왕을 구하기 위한 궁수의 활쏘기', '2023-10-10 00:00:00', 7.2),
            (15, '해운대', '02:00:00', '12세이상관람가', '윤제균', '설경구', '재난', '부산 해운대에 몰아치는 쓰나미를 중심으로 한 재난 영화', '2023-07-22 00:00:00', 6.7);
        """, stmt);
    }

    private void insertScreenExampleSet(Statement stmt) throws SQLException {
        executeUpdate("""
            INSERT INTO screen (screen_id, name, is_active, row_size, col_size) VALUES
            (1, '1관', 1, 10, 20),
            (2, '2관[LASER]', 1, 10, 20),
            (3, '3관[수퍼S]', 1, 8, 20),
            (4, '샤롯데', 1, 4, 8),
            (5, '5관', 1, 10, 20),
            (6, '6관', 1, 20, 20),
            (7, '7관', 1, 10, 20),
            (8, '8관', 1, 10, 20),
            (9, '9관', 1, 20, 20),
            (10, '10관', 1, 10, 20),
            (11, '11관', 1, 10, 20),
            (12, '12관', 1, 10, 20);
        """, stmt);
    }

    private void insertSeatExampleSet(Statement stmt) throws SQLException {
        executeUpdate("""
            INSERT INTO seat (is_active, `row_number`, col_number, screen_id, screening_schedule_id) VALUES
            (1, 1, 1, 1, 1),
            (1, 1, 1, 2, 2),
            (1, 1, 1, 3, 3),
            (1, 1, 1, 4, 4),
            (1, 2, 1, 1, 5),
            (1, 2, 1, 2, 6),
            (1, 2, 1, 3, 7),
            (1, 2, 1, 4, 8),
            (1, 3, 1, 1, 9),
            (1, 3, 1, 2, 10),
            (1, 3, 1, 3, 11),
            (1, 3, 1, 4, 12),
            (1, 1, 1, 1, 13),
            (1, 1, 1, 2, 14),
            (1, 1, 1, 3, 15);
        """, stmt);
    }

    private void insertScreeningScheduleExampleSet(Statement stmt) throws SQLException {
        executeUpdate("""
            INSERT INTO screening_schedule (movie_id, screen_id, start_date, day_of_week, session_number, start_time) VALUES
            -- 2024-05-01 (수요일)
            (1, 1, '2024-05-01', 'WED', 1, '10:00:00'),
            (2, 2, '2024-05-01', 'WED', 1, '11:00:00'),
            (3, 3, '2024-05-01', 'WED', 1, '12:00:00'),
            (4, 4, '2024-05-01', 'WED', 1, '13:00:00'),
            (5, 1, '2024-05-01', 'WED', 2, '14:00:00'),
            (6, 2, '2024-05-01', 'WED', 2, '15:00:00'),
            (7, 3, '2024-05-01', 'WED', 2, '16:00:00'),
            (8, 4, '2024-05-01', 'WED', 2, '17:00:00'),
            (9, 1, '2024-05-01', 'WED', 3, '18:00:00'),
            (10, 2, '2024-05-01', 'WED', 3, '19:00:00'),
            (11, 3, '2024-05-01', 'WED', 3, '20:00:00'),
            (12, 4, '2024-05-01', 'WED', 3, '21:00:00'),
            
            -- 2024-05-02 (목요일)
            (13, 1, '2024-05-02', 'THU', 1, '10:00:00'),
            (14, 2, '2024-05-02', 'THU', 1, '11:00:00'),
            (15, 3, '2024-05-02', 'THU', 1, '12:00:00'),
            (1, 4, '2024-05-02', 'THU', 1, '13:00:00'),
            (2, 1, '2024-05-02', 'THU', 2, '14:00:00'),
            (3, 2, '2024-05-02', 'THU', 2, '15:00:00'),
            (4, 3, '2024-05-02', 'THU', 2, '16:00:00'),
            (5, 4, '2024-05-02', 'THU', 2, '17:00:00'),
            (6, 1, '2024-05-02', 'THU', 3, '18:00:00'),
            (7, 2, '2024-05-02', 'THU', 3, '19:00:00'),
            (8, 3, '2024-05-02', 'THU', 3, '20:00:00'),
            (9, 4, '2024-05-02', 'THU', 3, '21:00:00'),
            
            -- 2024-05-03 (금요일)
            (10, 1, '2024-05-03', 'FRI', 1, '10:00:00'),
            (11, 2, '2024-05-03', 'FRI', 1, '11:00:00'),
            (12, 3, '2024-05-03', 'FRI', 1, '12:00:00'),
            (13, 4, '2024-05-03', 'FRI', 1, '13:00:00'),
            (14, 1, '2024-05-03', 'FRI', 2, '14:00:00'),
            (15, 2, '2024-05-03', 'FRI', 2, '15:00:00'),
            (1, 3, '2024-05-03', 'FRI', 2, '16:00:00'),
            (2, 4, '2024-05-03', 'FRI', 2, '17:00:00'),
            (3, 1, '2024-05-03', 'FRI', 3, '18:00:00'),
            (4, 2, '2024-05-03', 'FRI', 3, '19:00:00'),
            (5, 3, '2024-05-03', 'FRI', 3, '20:00:00'),
            (6, 4, '2024-05-03', 'FRI', 3, '21:00:00'),
            
            -- 2024-05-04 (토요일)
            (7, 1, '2024-05-04', 'SAT', 1, '10:00:00'),
            (8, 2, '2024-05-04', 'SAT', 1, '11:00:00'),
            (9, 3, '2024-05-04', 'SAT', 1, '12:00:00'),
            (10, 4, '2024-05-04', 'SAT', 1, '13:00:00'),
            (11, 1, '2024-05-04', 'SAT', 2, '14:00:00'),
            (12, 2, '2024-05-04', 'SAT', 2, '15:00:00'),
            (13, 3, '2024-05-04', 'SAT', 2, '16:00:00'),
            (14, 4, '2024-05-04', 'SAT', 2, '17:00:00'),
            (15, 1, '2024-05-04', 'SAT', 3, '18:00:00'),
            (1, 2, '2024-05-04', 'SAT', 3, '19:00:00'),
            (2, 3, '2024-05-04', 'SAT', 3, '20:00:00'),
            (3, 4, '2024-05-04', 'SAT', 3, '21:00:00'),
            
            -- 2024-05-05 (일요일)
            (4, 1, '2024-05-05', 'SUN', 1, '10:00:00'),
            (5, 2, '2024-05-05', 'SUN', 1, '11:00:00'),
            (6, 3, '2024-05-05', 'SUN', 1, '12:00:00'),
            (7, 4, '2024-05-05', 'SUN', 1, '13:00:00'),
            (8, 1, '2024-05-05', 'SUN', 2, '14:00:00'),
            (9, 2, '2024-05-05', 'SUN', 2, '15:00:00'),
            (10, 3, '2024-05-05', 'SUN', 2, '16:00:00'),
            (11, 4, '2024-05-05', 'SUN', 2, '17:00:00'),
            (12, 1, '2024-05-05', 'SUN', 3, '18:00:00'),
            (13, 2, '2024-05-05', 'SUN', 3, '19:00:00'),
            (14, 3, '2024-05-05', 'SUN', 3, '20:00:00'),
            (15, 4, '2024-05-05', 'SUN', 3, '21:00:00'),
            
            -- 2024-05-06 (월요일)
            (1, 1, '2024-05-06', 'MON', 1, '10:00:00'),
            (2, 2, '2024-05-06', 'MON', 1, '11:00:00'),
            (3, 3, '2024-05-06', 'MON', 1, '12:00:00'),
            (4, 4, '2024-05-06', 'MON', 1, '13:00:00'),
            (5, 1, '2024-05-06', 'MON', 2, '14:00:00'),
            (6, 2, '2024-05-06', 'MON', 2, '15:00:00'),
            (7, 3, '2024-05-06', 'MON', 2, '16:00:00'),
            (8, 4, '2024-05-06', 'MON', 2, '17:00:00'),
            (9, 1, '2024-05-06', 'MON', 3, '18:00:00'),
            (10, 2, '2024-05-06', 'MON', 3, '19:00:00'),
            (11, 3, '2024-05-06', 'MON', 3, '20:00:00'),
            (12, 4, '2024-05-06', 'MON', 3, '21:00:00'),
            
            -- 2024-05-07 (화요일)
            (13, 1, '2024-05-07', 'TUE', 1, '10:00:00'),
            (14, 2, '2024-05-07', 'TUE', 1, '11:00:00'),
            (15, 3, '2024-05-07', 'TUE', 1, '12:00:00'),
            (1, 4, '2024-05-07', 'TUE', 1, '13:00:00'),
            (2, 1, '2024-05-07', 'TUE', 2, '14:00:00'),
            (3, 2, '2024-05-07', 'TUE', 2, '15:00:00'),
            (4, 3, '2024-05-07', 'TUE', 2, '16:00:00'),
            (5, 4, '2024-05-07', 'TUE', 2, '17:00:00'),
            (6, 1, '2024-05-07', 'TUE', 3, '18:00:00'),
            (7, 2, '2024-05-07', 'TUE', 3, '19:00:00'),
            (8, 3, '2024-05-07', 'TUE', 3, '20:00:00'),
            (9, 4, '2024-05-07', 'TUE', 3, '21:00:00');
        """, stmt);
    }

    private void insertUserExampleSet(Statement stmt) throws SQLException {
        executeUpdate("""
            INSERT INTO member (`name`, phone_number, email) VALUES
            ('김정래', '010-9976-3892', 'kjr@google.com'),
            ('김도형', '010-1234-0000', 'kdh@google.com'), 
            ('이지우', '010-5678-1111', 'ljw@google.com'), 
            ('박서윤', '010-9012-2222', 'bsy@google.com'),
            ('최민준', '010-1234-3333', 'cmj@google.com'),
            ('이서준', '010-5678-4444', 'lsj@google.com'), 
            ('정서연', '010-9012-5555', 'jsy@google.com'), 
            ('조도윤', '010-1234-6666', 'jdy@google.com'), 
            ('강하윤', '010-5678-7777', 'khy@google.com'),
            ('윤시우', '010-9012-8888', 'ysw@google.com'),
            ('임민서', '010-1234-9999', 'lms@google.com'), 
            ('장연우', '010-5678-0000', 'jyw@google.com'), 
            ('김서형', '010-9012-1111', 'ksh@google.com'),
            ('최주원', '010-1234-2222', 'cjw@google.com'); 
        """, stmt);
    }

    private void insertReservationExampleSet(Statement stmt) throws SQLException {
        executeUpdate("""
            INSERT INTO reservation (payment_method, payment_status, amount, payment_date, member_id) VALUES
            ('CARD', 'COMPLETED', 12000, '2024-05-01', 1),
            ('CASH', 'COMPLETED', 12000, '2024-05-01', 2),
            ('GIFTCON', 'COMPLETED', 10000, '2024-05-01', 3),
            ('CARD', 'COMPLETED', 12000, '2024-05-02', 4),
            ('CARD', 'COMPLETED', 12000, '2024-05-02', 5),
            ('CASH', 'COMPLETED', 12000, '2024-05-02', 6),
            ('GIFTCON', 'COMPLETED', 10000, '2024-05-03', 7),
            ('CARD', 'COMPLETED', 12000, '2024-05-03', 8),
            ('CASH', 'COMPLETED', 12000, '2024-05-03', 9),
            ('CARD', 'COMPLETED', 12000, '2024-05-04', 10),
            ('GIFTCON', 'COMPLETED', 10000, '2024-05-04', 11),
            ('CARD', 'COMPLETED', 12000, '2024-05-04', 12),
            ('CASH', 'COMPLETED', 12000, '2024-05-04', 13),
            ('CARD', 'COMPLETED', 12000, '2024-05-04', 14),
            ('GIFTCON', 'COMPLETED', 10000, '2024-05-04', 1);
        """, stmt);
    }

    private void insertTicketExampleSet(Statement stmt) throws SQLException {
        executeUpdate("""
            INSERT INTO ticket (screening_schedule_id, screen_id, seat_id, reservation_id, is_issued, standard_price, sale_price) VALUES
            (1, 1, 1, 1, 0, 12000, 12000),
            (2, 2, 2, 2, 0, 12000, 12000),
            (3, 3, 3, 3, 0, 12000, 10000),
            (4, 4, 4, 4, 0, 12000, 12000),
            (5, 1, 5, 5, 0, 12000, 12000),
            (6, 2, 6, 6, 0, 12000, 12000),
            (7, 3, 7, 7, 0, 12000, 10000),
            (8, 4, 8, 8, 0, 12000, 12000),
            (9, 1, 9, 9, 0, 12000, 12000),
            (10, 2, 10, 10, 0, 12000, 12000),
            (11, 3, 11, 11, 0, 12000, 10000),
            (12, 4, 12, 12, 0, 12000, 12000),
            (13, 1, 13, 13, 0, 12000, 12000),
            (14, 2, 14, 14, 0, 12000, 12000),
            (15, 3, 15, 15, 0, 12000, 10000);
        """, stmt);
    }

    private void executeUpdate(String sql, Statement stmt) throws SQLException {
        stmt.executeUpdate(sql);
    }

    public DatabaseInitializer() {
    }
}
