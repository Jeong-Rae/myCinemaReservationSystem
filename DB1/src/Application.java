import feature.admin.AdminController;

public class Application {

	public static void main(String[] args) {
		AdminController adminController = new AdminController();
		adminController.initializeDatabase();
	}

}
