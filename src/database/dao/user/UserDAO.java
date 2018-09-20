package database.dao.user;

import java.util.List;
import database.entities.User;

public interface UserDAO 
{
	public User find(int id);

    public List<User> list();

    public int create(User user);
    
    public int count();
        
    public User matchUserLogin(String email,String password);
    
    public int updateSettings(int user_id, String email, String password);
    
    public User getUserProfile(int id);
    
    public int updateUser(User user, int user_id);

    public List<User> getSelectedUsers(String[] ids);						//gets selected users from admin page in order to generate xml
    
    public List<User> getLikesAndComments(int user_id);
    
    public List<User> getJobApplicants(int jobId);
    
    public String getUserSkills(int userId);
}
