package database.dao.message;

import java.util.List;

import database.entities.Message;

public interface MessageDAO {
	public List<Message> list();

    public int create(Message message);
    
    public int count();
    
    public List<Message> findMessages(Long userId1, Long userId2);
}
