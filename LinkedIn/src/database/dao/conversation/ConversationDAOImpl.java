package database.dao.conversation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import JavaFiles.VariousFunctions;
import database.dao.ConnectionFactory;
import database.dao.DAOUtil;
import database.dao.message.MessageDAO;
import database.dao.message.MessageDAOImpl;
import database.entities.Conversation;
import database.entities.ConversationPK;

public class ConversationDAOImpl implements ConversationDAO{
	
	//prepared statements
	private static final String SQL_LIST = "SELECT user_id1, user_id2, lastDate FROM Conversation";
	private static final String SQL_INSERT = "INSERT INTO Conversation (user_id1, user_id2, lastDate) VALUES  (?, ?, ?)";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM Conversation";
	private static final String SQL_FIND_CONVERSATION = "SELECT user_id1, user_id2, lastDate FROM Conversation WHERE (user_id1 = ? AND user_id2 = ?) OR (user_id1 = ? AND user_id2 = ?)";
	private static final String SQL_FIND_CONVERSATIONS = "SELECT DISTINCT id,name,surname,photoURL,Conversation.user_id1,Conversation.user_id2,lastDate FROM Conversation,User,Message WHERE ((Conversation.user_id1 = ? AND Conversation.user_id2 = user.id) OR (Conversation.user_id2 = ? AND Conversation.user_id1 = user.id)) ORDER BY lastDate DESC";	
	private static final String SQL_UPDATE_CONVERSATION = "UPDATE Conversation SET lastDate = ? WHERE (user_id1 = ? AND user_id2 = ?) OR (user_id1 = ? AND user_id2 = ?)";
	private ConnectionFactory factory;
    
    public ConversationDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }
	@Override
	public List<Conversation> list() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Conversation> conversations = new ArrayList<>();

        try 
        {
            connection = factory.getConnection();
            statement = connection.prepareStatement(SQL_LIST);
            resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
            	conversations.add(map(resultSet));
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

        return conversations;
	}

	@Override
	public int create(int sessionId, int clickedId, Date lastDate) {
		Connection connection = null;
		PreparedStatement statement = null;
		int ret = -1;
		Object[] values = {sessionId, clickedId, DAOUtil.toSqlTimestamp(lastDate)};
		//connect to DB
		try 
		{
			connection = factory.getConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
					
			int affectedRows = statement.executeUpdate();
			ret = affectedRows;
			if (ret == 0) {
				System.err.println("Creating conversation failed, no rows affected.");
				return ret;
			}
		} 
		catch (SQLException e) {
			System.err.println("SQLException: Creating conversation failed");
			return ret;
		}
		finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
        }
		return ret;
	}

	@Override
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
	public Conversation findConversation(int sessionId, int clickedId) {
		Conversation conversation = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

        try
        {
            connection = factory.getConnection();
            statement = DAOUtil.prepareStatement(connection, SQL_FIND_CONVERSATION, false, sessionId, clickedId, clickedId, sessionId);
            resultSet = statement.executeQuery();
        
            if (resultSet.next()) {
            	conversation = map(resultSet);
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
        return conversation;
	}
	
	@Override
	public List<Conversation> findAllConversations(int userId) {
		List<Conversation> conversations = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

        try
        {
            connection = factory.getConnection();
            statement = DAOUtil.prepareStatement(connection, SQL_FIND_CONVERSATIONS, false, userId,userId);
            resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
            	conversations.add(mapAll(resultSet));
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
        return conversations;
	}

	@Override
	public int updateLastDate(Date date, int userId1, int userId2) {
		Connection connection = null;
		PreparedStatement statement = null;
		int affectedRows=0;
		try 
		{
			connection = factory.getConnection();
		    statement = DAOUtil.prepareStatement(connection, SQL_UPDATE_CONVERSATION, false, DAOUtil.toSqlTimestamp(date), userId1, userId2, userId2, userId1);
	 		affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				System.err.println("Updating conversation failed, no rows affected.");
				return affectedRows;
			}
			
		} catch (SQLException e) {
			System.err.println("SQLException: Updating conversation failed.");
			e.printStackTrace();
			return affectedRows;
		}
		 finally {
	            VariousFunctions.closeConnection(connection);
	            VariousFunctions.closeStmt(statement);
		 }	
		
		return affectedRows;
	}
	
	private static Conversation map(ResultSet resultSet) throws SQLException {
		Conversation conversation = new Conversation();
		ConversationPK convPK = new ConversationPK();
		convPK.setUserId1(resultSet.getInt("user_id1"));
		convPK.setUserId2(resultSet.getInt("user_id2"));
		conversation.setId(convPK);
		conversation.setLastDate(new java.util.Date(resultSet.getTimestamp("lastDate").getTime()));
		conversation.setDateInterval(VariousFunctions.getDateInterval(conversation.getLastDate()));
		MessageDAO dao = new MessageDAOImpl(true);
		conversation.setMessages(dao.findMessages(Integer.valueOf(convPK.getUserId1()), Integer.valueOf(convPK.getUserId2())));
		return conversation;
	}
	
	private static Conversation mapAll(ResultSet resultSet) throws SQLException {
		Conversation conversation = new Conversation();
		ConversationPK convPK = new ConversationPK();
		convPK.setUserId1(resultSet.getInt("user_id1"));
		convPK.setUserId2(resultSet.getInt("user_id2"));
		conversation.setId(convPK);
		conversation.setLastDate(new java.util.Date(resultSet.getTimestamp("lastDate").getTime()));
		MessageDAO dao = new MessageDAOImpl(true);
		conversation.setMessages(dao.findMessages(Integer.valueOf(convPK.getUserId1()), Integer.valueOf(convPK.getUserId2())));
		//set local fields
		conversation.setDateInterval(VariousFunctions.getDateInterval(conversation.getLastDate()));
		conversation.setConversationId(resultSet.getInt("id"));
		conversation.setName(resultSet.getString("name"));
		conversation.setSurname(resultSet.getString("surname"));
		conversation.setPhotoURL(resultSet.getString("photoURL"));
		return conversation;
	}
	
	
}
