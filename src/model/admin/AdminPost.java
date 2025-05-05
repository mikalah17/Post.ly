package model.admin;

import model.Post;
import java.time.LocalDateTime;

public class AdminPost extends Post {
    private int reportCount;
    private boolean isFlagged;
    
    public AdminPost(Post post, int reportCount, boolean isFlagged) {
        super(post.getContent(), post.getUser());
        this.reportCount = reportCount;
        this.isFlagged = isFlagged;
    }
    
    // Getters and setters
    public int getReportCount() { return reportCount; }
    public boolean isFlagged() { return isFlagged; }
    public void setFlagged(boolean flagged) { isFlagged = flagged; }
}