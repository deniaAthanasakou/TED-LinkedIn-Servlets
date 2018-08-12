package database.dao.post;

import java.util.List;

import database.entities.Post;

public interface PostDAO {

    public List<Post> list();

    public int create(Post post);
}
