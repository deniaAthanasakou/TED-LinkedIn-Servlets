package database.dao.connection;

import java.util.List;
import database.entities.User;

public interface ConnectionDAO {
	
	public List<User> searchByName(String name, int user_id);
	    
	public List<User> searchBySurname(String surname, int user_id);
	    
	public List<User> searchByNameAndSurname(String name, String surname, int user_id);

	public List<User> getConnections(int user_id);
    
    public List<User> listWithConnectedPendingField(int user_id);
    
    public int connectToUser(int user_id1, int user_id2);
	
    public List<User> existingListWithConnectedField(int user_id, List<User> users);
    
    public int countConnections(long user_id);
    
    public int acceptConnection(int user_id1, int user_id2);
    
    public int rejectConnection(int user_id1, int user_id2);
    
    public List<User> getConnectionRequestsPending(int user_id);
}
