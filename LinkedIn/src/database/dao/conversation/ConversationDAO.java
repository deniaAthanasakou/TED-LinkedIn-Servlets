package database.dao.conversation;

import java.util.List;

import database.entities.Conversation;


public interface ConversationDAO {
	public List<Conversation> list();

    public int create(Long sessionId, Long clickedId);
    
    public int count();
    
    public Conversation findConversation(Long sessionId, Long clickedId);
    
    public List<Conversation> findAllConversations(Long userId);
}
