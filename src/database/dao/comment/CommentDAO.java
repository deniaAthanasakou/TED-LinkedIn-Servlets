package database.dao.comment;

import java.util.List;

import database.entities.Comment;

public interface CommentDAO {
	
    public List<Comment> list();									//get all comments

	public int create(Comment comment, int userId, int postId);		//create comment
    
    public int count();												//count how many comments exist
    
    public List<Comment> findComments(int id);						//find comments of post
}
