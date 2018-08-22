package database.dao.post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.dao.ConnectionFactory;
import database.dao.DAOUtil;
import database.dao.user.UserDAO;
import database.entities.Post;
import database.entities.User;

public class PostDAOImpl implements PostDAO 
{
	//prepared Statements
	private static final String SQL_LIST = "SELECT id, text, date_posted, path_files, hasAudio, hasImages, hasVideos, likes, user_id FROM Post";
	private static final String SQL_INSERT = "INSERT INTO Post (text, date_posted, path_files, hasAudio, hasImages, hasVideos, likes, user_id) VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM Post";
	private static final String SQL_FIND_POSTS = "SELECT id,text, date_posted, path_files, hasAudio, hasImages, hasVideos, likes FROM Post WHERE user_id = ? ORDER BY date_posted";

    
    private ConnectionFactory factory;
    
    public PostDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }

	@Override
	public List<Post> list() {
		List<Post> posts = new ArrayList<>();

        try (
            Connection connection = factory.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return posts;
	}

	@Override
	public int create(Post post) 
	{
		int ret = -1;
		//get values from user entity
		int isAdmin=0;
		Object[] values = { post.getText(), DAOUtil.toSqlTimestamp(post.getDatePosted()), post.getPathFiles(), post.getHasAudio(), post.getHasImages(), post.getHasVideos(), post.getLikes(), post.getUser().getId()};

		//connect to DB
		try (Connection connection = factory.getConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);) 
		{
			System.err.println("inside first try");
			
			System.out.println(statement);
			
			int affectedRows = statement.executeUpdate();
			System.err.println("after affectedRows");
			ret = affectedRows;
			if (ret == 0) {
				System.err.println("Creating user failed, no rows affected.");
				return ret;
			}
			System.err.println("before second try");
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				System.err.println("inside second try");
				if (generatedKeys.next()) {
					post.setId(generatedKeys.getInt(1));
					return ret;
				} 
				else {
					System.err.println("Creating user failed, no generated key obtained.");
					return -1;
				}
			}
		} 
		catch (SQLException e) {
			System.err.println("SQLException: Creating user failed, no generated key obtained.");
			return ret;
		}
	}
	
	@Override
	public int count() {

		int size = 0;
		
        try (
            Connection connection = factory.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_COUNT);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
            	size = resultSet.getInt("COUNT(*)");
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return size;
	}
	
	@Override
	public List<Post> findPosts(Long id) {
		List<Post> posts = new ArrayList<>();

        try (
            Connection connection = factory.getConnection();
        		PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_FIND_POSTS, false, id);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return posts;
	}
	
	private static Post map(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        post.setId(resultSet.getInt("id"));
        post.setText(resultSet.getString("text"));
        post.setDatePosted(resultSet.getDate("date_posted"));
        post.setPathFiles(resultSet.getString("path_files"));
        post.setHasAudio(resultSet.getByte("hasAudio"));
        post.setHasImages(resultSet.getByte("hasImages"));
        post.setHasVideos(resultSet.getByte("hasVideos"));
        post.setLikes(resultSet.getInt("likes"));
	    return post;
	}
}
