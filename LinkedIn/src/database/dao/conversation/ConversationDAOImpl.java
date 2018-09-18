package database.dao.conversation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import database.dao.ConnectionFactory;
import database.dao.DAOUtil;
import database.dao.message.MessageDAO;
import database.dao.message.MessageDAOImpl;
import database.entities.Conversation;
import database.entities.ConversationPK;

public class ConversationDAOImpl implements ConversationDAO{
	
	//prepared statements
	private static final String SQL_LIST = "SELECT user_id1, user_id2 FROM Conversation";
	private static final String SQL_INSERT = "INSERT INTO Conversation (user_id1, user_id2) VALUES  (?, ?)";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM Conversation";
	private static final String SQL_FIND_CONVERSATION = "SELECT user_id1, user_id2 FROM Conversation WHERE (user_id1 = ? AND user_id2 = ?) OR (user_id1 = ? AND user_id2 = ?)";

	private ConnectionFactory factory;
    
    public ConversationDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }
	@Override
	public List<Conversation> list() {
		List<Conversation> conversations = new ArrayList<>();

        try (
            Connection connection = factory.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
            	conversations.add(map(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return conversations;
	}

	@Override
	public int create(Long sessionId, Long clickedId) {
		int ret = -1;
		Object[] values = {sessionId, clickedId};
		//connect to DB
		try (Connection connection = factory.getConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);) 
		{			
			System.out.println(statement);
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
		return ret;
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
	public Conversation findConversation(Long sessionId, Long clickedId) {
		Conversation conversation = null;

        try (
            Connection connection = factory.getConnection();
            PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_FIND_CONVERSATION, false, sessionId, clickedId, clickedId, sessionId);
            ResultSet resultSet = statement.executeQuery();
        ) {
            if (resultSet.next()) {
            	conversation = map(resultSet);
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        return conversation;
	}

	private static Conversation map(ResultSet resultSet) throws SQLException {
		Conversation conversation = new Conversation();
		ConversationPK convPK = new ConversationPK();
		convPK.setUserId1(resultSet.getInt("user_id1"));
		convPK.setUserId2(resultSet.getInt("user_id2"));
		conversation.setId(convPK);
		MessageDAO dao = new MessageDAOImpl(true);
		conversation.setMessages(dao.findMessages(Long.valueOf(convPK.getUserId1()), Long.valueOf(convPK.getUserId2())));
		return conversation;
	}
}
