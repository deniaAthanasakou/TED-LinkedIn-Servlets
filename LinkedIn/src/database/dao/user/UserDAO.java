package database.dao.user;

import java.util.List;
import database.entities.User;

public interface UserDAO 
{
	public User find(Long id);

    public List<User> list();

    public int create(User user);
    
    public int count();
    
    public List<User> searchByName(String name);
    
    public List<User> searchBySurname(String surname);
    
    public List<User> searchByNameAndSurname(String name, String surname);

}
