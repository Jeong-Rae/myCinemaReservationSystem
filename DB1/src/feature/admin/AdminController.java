package feature.admin;

import infrastructure.repository.db2.DatabaseInitializer;

public class AdminController {
	// DB 초기화
	public void initializeDatabase() {
		DatabaseInitializer databaseInitializer = new DatabaseInitializer();
		
		databaseInitializer.initializeDatabase();
	}
	
	// 전체 DB 조회
	
	// INSERT
	
	// DELETE
	
	// UPDATE
	
	public AdminController() {
	}
}
