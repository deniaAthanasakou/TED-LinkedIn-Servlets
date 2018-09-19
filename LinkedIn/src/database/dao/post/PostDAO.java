package database.dao.post;

import java.util.List;

import database.entities.Post;

public interface PostDAO {

    public List<Post> list();

    public int create(Post post);
    
    public int count();
    
    public List<Post> findPosts(int id);
    
    public void insertLike(int userId, int postId);
    
    public void deleteLike(int userId, int postId);
    
    public int checkLiked(int userId, int postId);
    
    public int countLikes(int postId);
    
    public Post getPost (int id);
    
}
