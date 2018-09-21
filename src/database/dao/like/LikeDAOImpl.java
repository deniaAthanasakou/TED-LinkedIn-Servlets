package database.dao.like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import JavaFiles.VariousFunctions;
import database.dao.utils.ConnectionFactory;
import database.dao.utils.DAOUtil;

public class LikeDAOImpl implements LikeDAO{
	
	//prepared statements
	private static final String SQL_INSERT_LIKE = "INSERT INTO ted.like (user_id,post_id, date_liked) VALUES (?,?, ?)";
	private static final String SQL_DELETE_LIKE = "DELETE FROM ted.like WHERE user_id = ? AND post_id = ?";
	private static final String SQL_CHECK_LIKE = "SELECT COUNT(*) FROM ted.like WHERE user_id = ? AND post_id = ?";
	private static final String SQL_COUNT_LIKES = "SELECT COUNT(*) FROM ted.like WHERE post_id = ?";	

	private ConnectionFactory factory;
    
    public LikeDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }
    
    @Override
	public void insertLike(int userId, int postId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		//get current time
		Date dNow = new Date();
        try {
             connection = factory.getConnection();
        	 statement = DAOUtil.prepareStatement(connection, SQL_INSERT_LIKE, false, userId, postId, dNow);
        
        	int rowsChanged = statement.executeUpdate();
        	if(rowsChanged==0) {
        		System.err.println("Error in liking a post.");
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
	}
	
	@Override
	public void deleteLike(int userId, int postId) {
		Connection connection = null;
		PreparedStatement statement = null;
        try {
             connection = factory.getConnection();
        	 statement = DAOUtil.prepareStatement(connection, SQL_DELETE_LIKE, false, userId, postId);
        
        	int rowsChanged = statement.executeUpdate();
        	if(rowsChanged==0) {
        		System.err.println("Error in deleting like of post.");
        	}
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
		finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
        }
	}
	
	@Override
	public int checkLiked(int userId, int postId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int size=0;
        try {
    		 connection = factory.getConnection();
    		 statement = DAOUtil.prepareStatement(connection, SQL_CHECK_LIKE, false, userId, postId);
    		 resultSet = statement.executeQuery();
        
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
        finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }
        return -1;
	}
	
	@Override
	public int countLikes(int postId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int size = 0;
		
        try {
             connection = factory.getConnection();
        	 statement = DAOUtil.prepareStatement(connection, SQL_COUNT_LIKES, false, postId);
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

}
