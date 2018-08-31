package database.dao.comment;

import java.util.List;

import database.entities.Comment;

public interface CommentDAO {

    public List<Comment> list();

	public int create(Comment comment, Long userId, Long postId);
    
    public int count();
    
    public List<Comment> findComments(Long id);
}
