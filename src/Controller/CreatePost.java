package Controller;

import java.sql.SQLException;

import model.Database;
import model.Post;
import View.Alert;

public class CreatePost {

	private Post post;
	private Database database;
	
	
	public CreatePost(Post post, Database database) {
		this.post = post;
		this.database = database;
		
	}
	
	public boolean posted() {
		boolean posted = false;

			String insert = "INSERT INTO `postlets`(`content`, `user`, `created_at`) VALUES" +  "('"+post.getContent()+"',"
					+ "'"+post.getUser().getID()+"','"+post.dateTime+"');";
		try {
			database.getStatement().execute(insert);
			posted = true;
		} catch (SQLException e) {
			new Alert(e.getMessage(), null);
			posted = false;
			
		}
		
		return posted;
		
	}
}
	
