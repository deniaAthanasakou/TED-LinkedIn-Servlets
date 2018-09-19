package database.dao.conversation;

import java.util.Date;
import java.util.List;

import database.entities.Conversation;


public interface ConversationDAO {
	public List<Conversation> list();

	public int create(int sessionId, int clickedId, Date lastDate);
    
    public int count();
    
    public Conversation findConversation(int sessionId, int clickedId);
    
    public List<Conversation> findAllConversations(int userId);
    
    public int updateLastDate(Date date, int userId1, int userId2);
}
