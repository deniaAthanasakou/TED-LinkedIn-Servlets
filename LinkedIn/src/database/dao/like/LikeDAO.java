package database.dao.like;

import java.util.List;

import database.entities.Like;

public interface LikeDAO {
	public void insertLike(int userId, int postId);
	
	public void deleteLike(int userId, int postId);
		
	public int checkLiked(int userId, int postId);
	
	public int countLikes(int postId);
	
	public List<Like> getLikes(int userId);
	
	public List<Like> getLikesPost(int postId);
}
