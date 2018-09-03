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
import database.dao.user.UserDAOImpl;
import database.entities.Post;

public class PostDAOImpl implements PostDAO 
{
	//prepared Statements
	private static final String SQL_LIST = "SELECT id, text, date_posted, path_files, hasAudio, hasImages, hasVideos, likes, user_id FROM Post";
	private static final String SQL_INSERT = "INSERT INTO Post (text, date_posted, path_files, hasAudio, hasImages, hasVideos, likes, user_id) VALUES  (?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM Post";
	private static final String SQL_FIND_POSTS = "SELECT* FROM(\r\n" + 
			"	SELECT * FROM post WHERE post.user_id IN(\r\n" + 
			"		SELECT post.user_id FROM post WHERE post.user_id = ?\r\n" + 
			"		UNION\r\n" + 
			"		SELECT post.user_id FROM post,user,connection WHERE (user.id = ? AND user.id=connection.user_id AND post.user_id=connection.connectedUser_id)\r\n" + 
			"		UNION\r\n" + 
			"		SELECT post.user_id FROM post,user,connection  WHERE (user.id = ? AND user.id=connection.connectedUser_id AND post.user_id=connection.user_id)\r\n" + 
			"	)\r\n" + 
			"	UNION\r\n" + 
			"	SELECT * FROM post WHERE post.id IN(\r\n" + 
			"		SELECT ted.like.post_id FROM ted.like WHERE ted.like.user_id IN(\r\n" + 
			"			SELECT post.user_id FROM post,user,connection WHERE (user.id = ? AND user.id=connection.user_id AND post.user_id=connection.connectedUser_id)\r\n" + 
			"			UNION\r\n" + 
			"			SELECT post.user_id FROM post,user,connection WHERE (user.id = ? AND user.id=connection.connectedUser_id AND post.user_id=connection.user_id)\r\n" + 
			"		)\r\n" + 
			"	)\r\n" + 
			"    UNION\r\n" + 
			"	SELECT * FROM post WHERE post.id IN(\r\n" + 
			"		SELECT comment.post_id FROM comment WHERE comment.user_id IN(\r\n" + 
			"			SELECT post.user_id FROM post,user,connection WHERE (user.id = ? AND user.id=connection.user_id AND post.user_id=connection.connectedUser_id)\r\n" + 
			"			UNION\r\n" + 
			"			SELECT post.user_id FROM post,user,connection WHERE (user.id = ? AND user.id=connection.connectedUser_id AND post.user_id=connection.user_id)\r\n" + 
			"		)\r\n" + 
			"	)\r\n" + 
			") posts ORDER BY posts.date_posted DESC";
		
	
	private static final String SQL_INSERT_LIKE = "INSERT INTO ted.like (user_id,post_id) VALUES (?,?)";
	private static final String SQL_DELETE_LIKE = "DELETE FROM ted.like WHERE user_id = ? AND post_id = ?";
	private static final String SQL_CHECK_LIKE = "SELECT COUNT(*) FROM ted.like WHERE user_id = ? AND post_id = ?";
	private static final String SQL_COUNT_LIKES = "SELECT COUNT(*) FROM ted.like WHERE post_id = ?";
    
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
		//get values from post entity
		Object[] values = { post.getText(), DAOUtil.toSqlTimestamp(post.getDatePosted()), post.getPathFiles(), post.getHasAudio(), post.getHasImages(), post.getHasVideos(), post.getLikes(), post.getUser().getId()};

		//connect to DB
		try (Connection connection = factory.getConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);) 
		{			
			System.out.println(statement);
			int affectedRows = statement.executeUpdate();
			ret = affectedRows;
			if (ret == 0) {
				System.err.println("Creating post failed, no rows affected.");
				return ret;
			}
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					post.setId(generatedKeys.getInt(1));
					return ret;
				} 
				else {
					System.err.println("Creating post failed, no generated key obtained.");
					return -1;
				}
			}
		} 
		catch (SQLException e) {
			System.err.println("SQLException: Creating post failed, no generated key obtained.");
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
		
		//get id's and friends' posts
        try (
            Connection connection = factory.getConnection();
        		PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_FIND_POSTS, false, id, id, id, id, id, id, id);
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
	public void insertLike(Long userId, Long postId) {
        try (
            Connection connection = factory.getConnection();
        		PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_INSERT_LIKE, false, userId, postId);
        ) {
        	int rowsChanged = statement.executeUpdate();
        	if(rowsChanged==0) {
        		System.err.println("Error liking a post.");
        	}
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
	}
	
	@Override
	public void deleteLike(Long userId, Long postId) {
        try (
            Connection connection = factory.getConnection();
        		PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_DELETE_LIKE, false, userId, postId);
        ) {
        	int rowsChanged = statement.executeUpdate();
        	if(rowsChanged==0) {
        		System.err.println("Error deleting like of post.");
        	}
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
	}
	
	@Override
	public int checkLiked(Long userId, Long postId) {
		int size=0;
        try (
            Connection connection = factory.getConnection();
        		PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_CHECK_LIKE, false, userId, postId);
        		ResultSet resultSet = statement.executeQuery();
            ) {
                while (resultSet.next()) {
                	size = resultSet.getInt("COUNT(*)");
                }
        	if(size==1) {
        		return 1;
        	}
        	return 0;
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        return -1;
	}
	
	@Override
	public int countLikes(Long postId) {

		int size = 0;
		
        try (
            Connection connection = factory.getConnection();
        		PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_COUNT_LIKES, false, postId);
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
	
	
	
	private static Post map(ResultSet resultSet) throws SQLException {
        Post post = new Post();
        post.setId(resultSet.getInt("id"));
        post.setText(resultSet.getString("text"));
        post.setDatePosted(new java.util.Date(resultSet.getTimestamp("date_posted").getTime()));
        post.setPathFiles(resultSet.getString("path_files"));
        post.setHasAudio(resultSet.getByte("hasAudio"));
        post.setHasImages(resultSet.getByte("hasImages"));
        post.setHasVideos(resultSet.getByte("hasVideos"));
        post.setLikes(resultSet.getInt("likes"));
        UserDAO userDao = new UserDAOImpl(true);
        post.setUser(userDao.find(resultSet.getInt("user_id")));
	    return post;
	}
}
