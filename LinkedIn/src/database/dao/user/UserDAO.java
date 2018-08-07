package database.dao.user;

import java.util.List;
import database.entities.User;

public interface UserDAO 
{
	public User find(Long id);

    public List<User> list();

    public int create(User user);

}
