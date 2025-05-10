package model;

public class Admin extends User {
    public Admin() {
        super();
    }
    
    public boolean isAdmin() {
        return true;
    }
}