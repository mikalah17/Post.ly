package model.admin;

public class AdminStats {
    private final int totalUsers;
    private final int activeUsers;
    private final int totalPosts;
    private final int reportedPosts;
    
    public AdminStats(int totalUsers, int activeUsers, int totalPosts, int reportedPosts) {
        this.totalUsers = totalUsers;
        this.activeUsers = activeUsers;
        this.totalPosts = totalPosts;
        this.reportedPosts = reportedPosts;
    }
    
    // Getters
    public int getTotalUsers() { return totalUsers; }
    public int getActiveUsers() { return activeUsers; }
    public int getTotalPosts() { return totalPosts; }
    public int getReportedPosts() { return reportedPosts; }
}