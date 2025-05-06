package model;

import java.util.ArrayList; 

public class User {
    private int ID;
    private String username;
    private String password;
    private ArrayList<Post> posts;
    private ArrayList<Integer> UsersIDs = new ArrayList<>();
    private boolean isAdmin;
    
    public User() {}
    
    public User(int id2, String getusername, String getpassword) {
		// TODO Auto-generated constructor stub
	}

	public int getID() {
        return ID; 
    }
    
    public void setID(int ID) {
        this.ID = ID; 
    }
    
    public String getusername() {
        return username;
    }
    
    public void setusername(String username) {
        this.username = username;
    }
    
    public String getpassword() {
        return password;
    }
    
    public void setpassword(String password) {
        this.password = password;
    }
    
    public ArrayList<Post> getPosts() {
        return posts;
    }
    
    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }
    
    public void setUsersIDs(ArrayList<Integer> UsersIDs) {
        this.UsersIDs = UsersIDs;
    }

    public ArrayList<Integer> getUsersIDs() {
        return UsersIDs;
    }
    
    public boolean isAdmin() {
        return isAdmin;
    }
    
    public void setAdmin(boolean admin) {
        this.isAdmin = admin;
    }
}