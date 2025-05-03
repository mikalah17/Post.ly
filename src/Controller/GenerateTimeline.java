package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import View.Alert;
import model.Database;
import model.Post;
import model.User;

public class GenerateTimeline {
	
	private ArrayList<Post> posts;
	
	public GenerateTimeline(User user, Database database) {
		posts = new ArrayList<>();
		
		String select ="SELECT * FROM `postlets` ORDER BY `created_at` DESC;";
		
	
		try {
			ResultSet rs = database.getStatement().executeQuery(select);
			ArrayList<Integer> usersIDs = new ArrayList<>();
			
			while (rs.next()) {
				Post p = new Post(); 
				p.setID(rs.getInt("id"));
				p.setContent(rs.getString("content"));
				usersIDs.add(rs.getInt("user"));
				p.setDateTimefromString(rs.getString("created_at"));
				posts.add(p);
				
			}
			for (int i=0;i<usersIDs.size();i++) {
				posts.get(i).setUser(new ReadUserByID(usersIDs.get(i), database).getUser());
				
			}
		} catch(SQLException e) {
			new Alert(e.getMessage(), null);
		}
		}
		
	
	public ArrayList<Post> getPosts() {
		return posts;
	}

}
