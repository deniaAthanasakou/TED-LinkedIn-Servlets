package database.dao.post;

import java.util.List;

import database.entities.Post;

public interface PostDAO {

    public List<Post> list();

    public int create(Post post);
    
    public int count();
    
    public List<Post> findPosts(Long id);
    
    public void insertLike(Long userId, Long postId);
    
    public void deleteLike(Long userId, Long postId);
    
    public int checkLiked(Long userId, Long postId);
    
    public int countLikes(Long postId);
    
}
