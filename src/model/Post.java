package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Post {
	
	    
	    private int ID;
	    private String content;
	    private User user;
	    public LocalDateTime dateTime;
	    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyy");
	    
	    public Post() {}
	    
	    
	    public Post(String content, User user) {
	    	this.content = content;
	    	this.user = user;
	    	dateTime = LocalDateTime.now();
	    }
	    public int getID() {
	        return ID;
	    }
	    
	    public void setID (int ID) {
	        this.ID = ID;
	    }
	    
	    public String getContent() {
	        return content;
	    }
	    
	    public void setContent (String content) {
	        this.content = content;
	    }
	    
	    public User getUser() {
	        return user; 
	    }
	    
	    public void setUser (User user) {
	        this.user = user;
	    }
	    
	    public LocalDateTime getDateTime () {
	        return dateTime;
	    }
	    
	    public void setDateTime (LocalDateTime dateTime) {
	        this.dateTime = dateTime;
	    }
	    
	    public String getDateTimeToString() {
	    	return dateTimeFormatter.format(dateTime);
	    	
	    }
	    
	    public void setDateTimefromString(String dateTime) {
	        try {
	            this.dateTime = LocalDateTime.parse(dateTime);
	        } catch (DateTimeParseException e) {
	           
	            this.dateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
	    }
	    }
	    public String getDateToString() {
	    	return dateFormatter.format(dateTime);
	    }
	}



