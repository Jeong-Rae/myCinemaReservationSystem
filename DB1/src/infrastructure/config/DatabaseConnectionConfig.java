package infrastructure.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionConfig {
	private static final String DB_URL = "jdbc:mysql://localhost:3306/db2";
	private static final String ROOT = "root";
	private static final String ROOT_PASSWORD = "1234";
	private static final String USER = "user1";
	private static final String USER_PASSWORD = "user1";
	
	
	public Connection getConnectionAdmin() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 드라이버 로드
			Connection conn = DriverManager.getConnection(DB_URL, ROOT, ROOT_PASSWORD); // JDBC 연결
			System.out.println("DB 연결 완료 " + ROOT);
			
			return conn;
		} catch (ClassNotFoundException ex) {
			System.out.println("JDBC 드라이버 로드 오류");
		} catch (SQLException ex) {
			System.out.println("DB 연결 오류");
		}
		return null;
	}
	
	public Connection getConnectionUSER() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(DB_URL, USER, USER_PASSWORD);
			System.out.println("DB 연결 완료 " + USER);
			
			return conn;
		} catch (ClassNotFoundException ex) {
			System.out.println("JDBC 드라이버 로드 오류");
		} catch (SQLException ex) {
			System.out.println("DB 연결 오류");
		}
		return null;
	}
}
