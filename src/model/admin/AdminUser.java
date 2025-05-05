package model.admin;

import model.User;

public class AdminUser extends User {
    private boolean isBanned;
    private int postCount;
    
    public AdminUser(User user, boolean isBanned, int postCount) {
        super(user.getID(), user.getusername(), user.getpassword());
        this.isBanned = isBanned;
        this.postCount = postCount;
    }
    
    // Getters and setters
    public boolean isBanned() { return isBanned; }
    public int getPostCount() { return postCount; }
    public void setBanned(boolean banned) { isBanned = banned; }
}