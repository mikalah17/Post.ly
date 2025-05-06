package Controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Database;
import model.Post;
import model.User;
import View.Alert;

public class ReadUserPosts {
	
	private ArrayList<Post> posts;
	
	public ReadUserPosts(User u, Database database) {
		posts = new ArrayList<>();
		String select = "SELECT * FROM `postlets` WHERE `user` = "+u.getID()+" ;";
		try {
			ResultSet rs = database.getStatement().executeQuery(select);
			while (rs.next()) {
				Post p = new Post();
				p.setID(rs.getInt("id"));
				p.setContent(rs.getString("content"));
				p.setDateTimefromString(rs.getString("created_at"));
				p.setUser(u);
				posts.add(p);
			}
		} catch (SQLException e) {
			new Alert(e.getMessage(), null);
		}
	}
	
	public ArrayList<Post> getPosts() {
		return posts;
	}

}
