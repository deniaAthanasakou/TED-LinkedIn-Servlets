package database.dao.message;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JavaFiles.VariousFunctions;
import database.dao.ConnectionFactory;
import database.dao.DAOUtil;
import database.entities.Conversation;
import database.entities.ConversationPK;
import database.entities.Message;

public class MessageDAOImpl implements MessageDAO{
	
	//prepared statements
	private static final String SQL_LIST = "SELECT message_id, text, date, user_id1, user_id2, sender FROM Message";
	private static final String SQL_INSERT = "INSERT INTO Message (text, date, user_id1, user_id2, sender) VALUES  (?, ?, ?, ?, ?)";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM Message";
	private static final String SQL_FIND_MESSAGES = "SELECT message_id, text, date, Message.user_id1, Message.user_id2, sender FROM Message, Conversation WHERE (Conversation.user_id1 = ? AND Conversation.user_id2 = ? AND Message.user_id1 = ? AND Message.user_id2 = ?) OR (Conversation.user_id1 = ? AND Conversation.user_id2 = ? AND Message.user_id1 = ? AND Message.user_id2 = ?)";

	private ConnectionFactory factory;
    
    public MessageDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }

	@Override
	public List<Message> list() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Message> messages = new ArrayList<>();

        try
        {
            connection = factory.getConnection();
            statement = connection.prepareStatement(SQL_LIST);
            resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
            	messages.add(map(resultSet));
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

        return messages;
	}

	@Override
	public int create(Message message) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		int ret = -1;
		Object[] values = { message.getText(), DAOUtil.toSqlTimestamp(message.getDate()), message.getConversation().getId().getUserId1(), message.getConversation().getId().getUserId2(), message.getSender()};
		//connect to DB
		try 
		{
			connection = factory.getConnection();
		     statement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
					
			int affectedRows = statement.executeUpdate();
			ret = affectedRows;
			if (ret == 0) {
				System.err.println("Creating message failed, no rows affected.");
				return ret;
			}
			try 
			{
				generatedKeys = statement.getGeneratedKeys();
			
				if (generatedKeys.next()) {
					message.setMessageId(generatedKeys.getInt(1));
					return ret;
				} 
				else {
					System.err.println("Creating message failed, no generated key obtained.");
					return -1;
				}
			}
			finally {
	            VariousFunctions.closeResultSet(generatedKeys);
	        }
		} 
		catch (SQLException e) {
			System.err.println("SQLException: Creating message failed, no generated key obtained.");
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
	public List<Message> findMessages(int userId1, int userId2){
		List<Message> messages = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
        try {
        	
        
            connection = factory.getConnection();
            statement = DAOUtil.prepareStatement(connection, SQL_FIND_MESSAGES, false, userId1, userId2, userId1, userId2,userId2, userId1, userId2, userId1);
            resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
            	messages.add(map(resultSet));
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
        return messages;
	}
	
	private static Message map(ResultSet resultSet) throws SQLException {
		Message message = new Message();
		message.setMessageId(resultSet.getInt("message_id"));
		message.setText(resultSet.getString("text"));
		message.setDate(new java.util.Date(resultSet.getTimestamp("date").getTime()));
		message.setSender(resultSet.getByte("sender"));
		Conversation conversation = new Conversation();
		ConversationPK id = new ConversationPK();
		id.setUserId1(resultSet.getInt("user_id1"));
		id.setUserId2(resultSet.getInt("user_id2"));
		conversation.setId(id);
		message.setConversation(conversation);
		message.setDateInterval(VariousFunctions.getDateInterval(message.getDate()));
		return message;
	}
	
}
