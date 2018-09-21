package database.dao.comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JavaFiles.VariousFunctions;
import database.dao.ConnectionFactory;
import database.dao.DAOUtil;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.Comment;

public class CommentDAOImpl implements CommentDAO 
{
	//prepared Statements
	private static final String SQL_LIST = "SELECT comment_id, text, date_posted, post_id, user_id  FROM Comment";
	private static final String SQL_INSERT = "INSERT INTO Comment (date_posted, text, post_id, user_id) VALUES (?, ?, ?, ?)";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM Comment";
	private static final String SQL_FIND_COMMENTS = "SELECT comment_id, text, date_posted, post_id, user_id FROM Comment WHERE post_id = ? ORDER BY date_posted DESC";
	
    private ConnectionFactory factory;
    
    public CommentDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }

	@Override
	public List<Comment> list() {		//get all comments
		List<Comment> comments = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

        try
        {
            connection = factory.getConnection();
            statement = connection.prepareStatement(SQL_LIST);
            resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
                comments.add(map(resultSet));
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

        return comments;
	}

	@Override
	public int create(Comment comment, int userId, int postId) 	//create comment
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		int ret = -1;
		//get values from comment entity
		Object[] values = {DAOUtil.toSqlTimestamp(comment.getDatePosted()), comment.getText(), postId, userId};

		//connect to DB
		try 
		{
			connection = factory.getConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
				
			int affectedRows = statement.executeUpdate();
			ret = affectedRows;
			if (ret == 0) {
				return ret;
			}
			try 
			{
				generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					comment.setCommentId(generatedKeys.getInt(1));
					return ret;
				} 
				else {
					System.err.println("Creating comment failed, no generated key obtained.");
					return -1;
				}
			}
			finally {
	            VariousFunctions.closeResultSet(generatedKeys);
	        }
		} 
		catch (SQLException e) {
			System.err.println("SQLException: Creating comment failed, no generated key obtained.");
			return ret;
		}
		finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
       }
	}
	
	@Override			//count how many comments exist
	public int count() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		int size = 0;
        try
        {
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
	public List<Comment> findComments(int id) {			//find comments of post
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Comment> comments = new ArrayList<>();

        try
        {
            connection = factory.getConnection();
            statement = DAOUtil.prepareStatement(connection, SQL_FIND_COMMENTS, false, id);
            resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
            	comments.add(map(resultSet));
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

        return comments;
	}
	
	private static Comment map(ResultSet resultSet) throws SQLException {
		Comment comment = new Comment();
        comment.setCommentId(resultSet.getInt("comment_id"));
        comment.setDatePosted(new java.util.Date(resultSet.getTimestamp("date_posted").getTime()));
        comment.setText(resultSet.getString("text"));
        UserDAO userDao = new UserDAOImpl(true);
        comment.setUser(userDao.find(resultSet.getInt("user_id")));
	    return comment;
	}
}
