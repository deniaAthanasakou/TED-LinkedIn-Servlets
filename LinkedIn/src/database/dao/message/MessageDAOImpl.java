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
	private static final String SQL_FIND_MESSAGES = "SELECT message_id, text, date, Message.user_id1, Message.user_id2, sender FROM Message, Conversation WHERE (Conversation.user_id1 = ? AND Conversation.user_id2 = ?) OR (Conversation.user_id1 = ? AND Conversation.user_id2 = ?)";

	private ConnectionFactory factory;
    
    public MessageDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }

	@Override
	public List<Message> list() {
		List<Message> messages = new ArrayList<>();

        try (
            Connection connection = factory.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
            	messages.add(map(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return messages;
	}

	@Override
	public int create(Message message) {
		int ret = -1;
		Object[] values = { message.getText(), DAOUtil.toSqlTimestamp(message.getDate()), message.getConversation().getId().getUserId1(), message.getConversation().getId().getUserId2(), message.getSender()};
		//connect to DB
		try (Connection connection = factory.getConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);) 
		{			
			System.out.println(statement);
			int affectedRows = statement.executeUpdate();
			ret = affectedRows;
			if (ret == 0) {
				System.err.println("Creating message failed, no rows affected.");
				return ret;
			}
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					message.setMessageId(generatedKeys.getInt(1));
					return ret;
				} 
				else {
					System.err.println("Creating message failed, no generated key obtained.");
					return -1;
				}
			}
		} 
		catch (SQLException e) {
			System.err.println("SQLException: Creating message failed, no generated key obtained.");
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
	public List<Message> findMessages(Long userId1, Long userId2){
		List<Message> messages = new ArrayList<>();

        try (
            Connection connection = factory.getConnection();
            PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_FIND_MESSAGES, false, userId1, userId2, userId2, userId1);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
            	messages.add(map(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
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
