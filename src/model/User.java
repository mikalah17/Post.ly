package model;

import java.util.ArrayList;

public class User {
    private int ID;
    private String username;
    private String password;
    private ArrayList<Post> posts;
    private ArrayList<User> users;
    private ArrayList<Integer> UsersIDs = new ArrayList<>();
    private boolean isActive = true; // Add this field

    // Constructor that accepts id, username, and isActive as parameters
    public User(int ID, String username, boolean isActive) {
        this.ID = ID;
        this.username = username;
        this.isActive = isActive;
    }

    // Default constructor
    public User() {}

    // Getter and Setter methods for all fields
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

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    // isActive Getter and Setter
    public boolean isActive() {
        return this.isActive;
    }

    public void setActive(boolean active) {
        this.isActive = active;
    }
}
