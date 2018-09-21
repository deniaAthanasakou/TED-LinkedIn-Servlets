package database.dao.like;

public interface LikeDAO {
	public void insertLike(int userId, int postId);
	
	public void deleteLike(int userId, int postId);
		
	public int checkLiked(int userId, int postId);
	
	public int countLikes(int postId);
}
