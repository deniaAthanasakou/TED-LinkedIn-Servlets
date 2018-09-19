package database.dao.conversation;

import java.util.Date;
import java.util.List;

import database.entities.Conversation;


public interface ConversationDAO {
	public List<Conversation> list();

	public int create(Long sessionId, Long clickedId, Date lastDate);
    
    public int count();
    
    public Conversation findConversation(Long sessionId, Long clickedId);
    
    public List<Conversation> findAllConversations(Long userId);
    
    public int updateLastDate(Date date, Long userId1, Long userId2);
}
