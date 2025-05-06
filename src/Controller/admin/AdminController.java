package Controller.admin;

import model.Database;

import model.admin.AdminStats;

public class AdminController {
    private final UserManagementController userController;
    private final PostManagementController postController;
    
    public AdminController(Database database) {
        this.userController = new UserManagementController(database);
        this.postController = new PostManagementController(database);
    }
    
    public AdminStats getSystemStats() {
        return new AdminStats(
            userController.getTotalUserCount(),
            userController.getActiveUserCount(),
            postController.getTotalPostCount(),
            postController.getReportedPostCount()
        );
    }
    
    public UserManagementController getUserController() {
        return userController;
    }
    
    public PostManagementController getPostController() {
        return postController;
    }
}