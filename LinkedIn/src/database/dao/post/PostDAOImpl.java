package database.dao.post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JavaFiles.AESCrypt;
import JavaFiles.VariousFunctions;
import database.dao.comment.CommentDAO;
import database.dao.comment.CommentDAOImpl;
import database.dao.like.LikeDAO;
import database.dao.like.LikeDAOImpl;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.dao.utils.ConnectionFactory;
import database.dao.utils.DAOUtil;
import database.entities.Post;

public class PostDAOImpl implements PostDAO 
{
	//prepared Statements
	private static final String SQL_LIST = "SELECT id, text, date_posted, path_files, hasAudio, hasImages, hasVideos, user_id FROM Post";
	private static final String SQL_INSERT = "INSERT INTO Post (text, date_posted, path_files, hasAudio, hasImages, hasVideos, user_id) VALUES  (?, ?, ?, ?, ?, ?, ?)";
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

	private static final String GET_POST = "SELECT * FROM Post WHERE id = ?";

    
    private ConnectionFactory factory;
    
    public PostDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }

	@Override
	public List<Post> list() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Post> posts = new ArrayList<>();

        try {
             connection = factory.getConnection();
             statement = connection.prepareStatement(SQL_LIST);
             resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }

        return posts;
	}

	@Override
	public int create(Post post) 
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		int ret = -1;
		//get values from post entity
		Object[] values = { post.getText(), DAOUtil.toSqlTimestamp(post.getDatePosted()), post.getPathFiles(), post.getHasAudio(), post.getHasImages(), post.getHasVideos(), post.getUser().getId()};

		//connect to DB
		try 
		{
			 connection = factory.getConnection();
			 statement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
				
			int affectedRows = statement.executeUpdate();
			ret = affectedRows;
			if (ret == 0) {
				System.err.println("Creating post failed, no rows affected.");
				return ret;
			}
			try 
			{
				generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					post.setId(generatedKeys.getInt(1));
					return ret;
				} 
				else {
					System.err.println("Creating post failed, no generated key obtained.");
					return -1;
				}
			}
			finally {
	            VariousFunctions.closeResultSet(generatedKeys);
	        }
		} 
		catch (SQLException e) {
			System.err.println("SQLException: Creating post failed, no generated key obtained.");
			return ret;
		}
		finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
        }
	}
	
	@Override
	public int count() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int size = 0;
		
        try {
             connection = factory.getConnection();
             statement = connection.prepareStatement(SQL_COUNT);
             resultSet = statement.executeQuery();
       
            while (resultSet.next()) {
            	size = resultSet.getInt("COUNT(*)");
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }

        return size;
	}
	
	@Override
	public List<Post> findPosts(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Post> posts = new ArrayList<>();
		
		//get id's and friends' posts
        try {
             connection = factory.getConnection();
        	 statement = DAOUtil.prepareStatement(connection, SQL_FIND_POSTS, false, id, id, id, id, id, id, id);
             resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
                posts.add(map(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }
       
        return posts;
	}
	
	@Override
	public Post getPost (int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Post post = null;
		
		try {
			 connection = factory.getConnection();
			 statement = DAOUtil.prepareStatement(connection,GET_POST, false, id);
	         resultSet = statement.executeQuery();
		
	        if (resultSet.next()) 
	        	post = map(resultSet);
		} 
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }
     
        return post;
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
        UserDAO userDao = new UserDAOImpl(true);
        post.setUser(userDao.find(resultSet.getInt("user_id")));
        CommentDAO commentDao = new CommentDAOImpl(true);
        post.setComments(commentDao.findComments(post.getId()));
        //local variables
        post.setNoComments(post.getComments().size());
        post.setDateInterval(VariousFunctions.getDateInterval(post.getDatePosted()));
        if(post.getPathFiles() != null) {
        	String folderPath = AESCrypt.decrypt(post.getPathFiles());
        	post.setPathOfFiles(folderPath);
        	VariousFunctions.setFilePathsFromFolders(folderPath,post);
        }
        
        LikeDAO likeDao = new LikeDAOImpl(true);
        post.setLikes(likeDao.countLikes(post.getId()));
        post.setLikesSet(likeDao.getLikesPost(post.getId()));
	    return post;
	}
}
