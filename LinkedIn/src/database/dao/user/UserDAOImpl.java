package database.dao.user;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.dao.ConnectionFactory;
import database.dao.DAOUtil;
import database.entities.Post;
import database.entities.User;

public class UserDAOImpl implements UserDAO 
{
	//prepared Statements
	private static final String SQL_FIND_BY_ID = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected, isPending, sentConnectionRequest FROM User WHERE id = ?";
	private static final String SQL_FIND_BY_EMAIL_PASSWORD = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected, isPending, sentConnectionRequest FROM User WHERE email = ? AND password = ?";
	private static final String SQL_LIST_ORDER_BY_ID = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected, isPending, sentConnectionRequest FROM User ORDER BY id";
	private static final String SQL_INSERT = "INSERT INTO User (isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, prof_exp, education, skills, privateTelephone, privateEmail, privateGender, privateDateOfBirth, privateProfExp, privateSkills, privateEducation, privateCity, privateCountry, workPos, institution, privateWorkPos, privateInstitution) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?,?,?,? )";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM User";
	private static final String SQL_UPDATE_EMAIL_PASSWORD = "UPDATE User SET email = ?, password = ? WHERE id = ?";

	private static final String SQL_FIND_BY_ID_PROFILE = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected, isPending, sentConnectionRequest, prof_exp, education, skills, privateTelephone, privateEmail, privateGender, privateDateOfBirth, privateProfExp, privateSkills, privateEducation, privateCity, privateCountry, workPos, institution, privateWorkPos, privateInstitution FROM User WHERE id = ?";
	private static final String SQL_UPDATE_USER = "UPDATE User SET name=?, surname=?, tel=?, photoURL=?, dateOfBirth=?, gender=?, city=?, country=?, hasImage=?, prof_exp=?, education=?, skills=?, privateTelephone=?, privateEmail=?, privateGender=?, privateDateOfBirth=?, privateProfExp=?, privateSkills=?, privateEducation=?, privateCity=?, privateCountry=?, workPos=?, institution=?, privateWorkPos=?, privateInstitution=? WHERE id=?";
	

		
    private ConnectionFactory factory;
    
    public UserDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }

	@Override
	public User find(int id) {
		User user = null;
		
		try (
			Connection connection = factory.getConnection();
			PreparedStatement statement = DAOUtil.prepareStatement(connection,SQL_FIND_BY_ID, false, id);
	        ResultSet resultSet = statement.executeQuery();)
		{
	        if (resultSet.next()) 
	            user = map(resultSet);
		} 
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
     
        return user;
	}

	@Override
	public List<User> list() {
		List<User> users = new ArrayList<>();

        try (
            Connection connection = factory.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return users;
	}

	@Override
	public int create(User user) 
	{
		System.out.println("in create");
		int ret = -1;
		//get values from user entity
		int isAdmin=0;
		Object[] values = { user.getIsAdmin(), user.getEmail(), user.getPassword(), user.getName(), user.getSurname(), user.getTel(), user.getPhotoURL(),
				DAOUtil.toSqlDate(user.getDateOfBirth()), user.getGender(), user.getCity(), user.getCountry(), user.getHasImage(), user.getProfExp(), user.getEducation(), user.getSkills(), user.getPrivateTelephone(), user.getPrivateEmail(), user.getPrivateGender(), user.getPrivateDateOfBirth(), user.getPrivateProfExp(), user.getPrivateSkills(), user.getPrivateEducation(), user.getPrivateCity(), user.getPrivateCountry(), user.getWorkPos(), user.getInstitution(), user.getPrivateWorkPos(), user.getPrivateInstitution()  };
		
		//connect to DB
		try (Connection connection = factory.getConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);) 
		{
			System.err.println("inside first try");
			
			System.out.println(statement);
			
			int affectedRows = statement.executeUpdate();
			System.err.println("after affectedRows");
			ret = affectedRows;
			if (ret == 0) {
				System.err.println("Creating user failed, no rows affected.");
				return ret;
			}
			System.err.println("before second try");
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				System.err.println("inside second try");
				if (generatedKeys.next()) {
					user.setId(generatedKeys.getInt(1));
					return ret;
				} 
				else {
					System.err.println("Creating user failed, no generated key obtained.");
					return -1;
				}
			}
		} 
		catch (SQLException e) {
			System.err.println("SQLException: Creating user failed, no generated key obtained.");
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
	public User matchUserLogin(String email,String password) {
		User user = null;
		
		try (
			Connection connection = factory.getConnection();
			PreparedStatement statement = DAOUtil.prepareStatement(connection,SQL_FIND_BY_EMAIL_PASSWORD, false, email,password);
	        ResultSet resultSet = statement.executeQuery();)
		{
	        if (resultSet.next()) 
	            user = map(resultSet);
		} 
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
     
        return user;
	}
	

	
	@Override
	public int updateSettings(int user_id, String email, String password) {
		int affectedRows=0;
		try (Connection	connection = factory.getConnection();
			PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_UPDATE_EMAIL_PASSWORD, false, email, password, user_id);)
		
		{
	 		affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				System.err.println("Updating user failed, no rows affected.");
				return affectedRows;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("SQLException: Updating user failed.");
			e.printStackTrace();
			return affectedRows;
		}
		
		return affectedRows;
 		
	}
	
	@Override
	public User getUserProfile(int id) {
		User user = null;
		
		try (
			Connection connection = factory.getConnection();
			PreparedStatement statement = DAOUtil.prepareStatement(connection,SQL_FIND_BY_ID_PROFILE, false, id);
	        ResultSet resultSet = statement.executeQuery();)
		{
	        if (resultSet.next()) 
	            user = mapEverything(resultSet);
		} 
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
     
        return user;
	}
	
	@Override
	public int updateUser(User user, int user_id) {
		int affectedRows=0;
		try (Connection	connection = factory.getConnection();
			PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_UPDATE_USER, false,  user.getName(), user.getSurname(), user.getTel(), user.getPhotoURL(), user.getDateOfBirth(), user.getGender(), user.getCity(), user.getCountry(), user.getHasImage(), user.getProfExp(), user.getEducation(), user.getSkills(), user.getPrivateTelephone(), user.getPrivateEmail(), user.getPrivateGender(), user.getPrivateDateOfBirth(), user.getPrivateProfExp(), user.getPrivateSkills(), user.getPrivateEducation(), user.getPrivateCity(), user.getPrivateCountry(), user.getWorkPos(), user.getInstitution(), user.getPrivateWorkPos(), user.getPrivateInstitution(), user_id);)
		
		{
	 		affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				System.err.println("Updating user failed, no rows affected.");
				return affectedRows;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("SQLException: Updating user failed.");
			e.printStackTrace();
			return affectedRows;
		}
		
		return affectedRows;
	}
	

	
	private static User map(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setIsAdmin(resultSet.getInt("isAdmin"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setTel(resultSet.getString("tel"));
        user.setPhotoURL(resultSet.getString("photoURL"));
        user.setDateOfBirth(resultSet.getDate("dateOfBirth"));
        user.setGender(resultSet.getInt("gender"));
        user.setCity(resultSet.getString("city"));
        user.setCountry(resultSet.getString("country"));
        user.setHasImage(resultSet.getByte("hasImage"));
        user.setIsConnected(resultSet.getByte("isConnected"));
        user.setIsPending(resultSet.getByte("isPending"));
        user.setSentConnectionRequest(resultSet.getByte("sentConnectionRequest"));
        return user;
    }
	
	private static User mapEverything(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setIsAdmin(resultSet.getInt("isAdmin"));
        user.setEmail(resultSet.getString("email"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setTel(resultSet.getString("tel"));
        user.setPhotoURL(resultSet.getString("photoURL"));
        user.setDateOfBirth(resultSet.getDate("dateOfBirth"));
        user.setGender(resultSet.getInt("gender"));
        user.setCity(resultSet.getString("city"));
        user.setCountry(resultSet.getString("country"));
        user.setHasImage(resultSet.getByte("hasImage"));
        user.setIsConnected(resultSet.getByte("isConnected"));
        user.setProfExp(resultSet.getString("prof_exp"));
        user.setEducation(resultSet.getString("education"));
        user.setSkills(resultSet.getString("skills"));
        user.setWorkPos(resultSet.getString("workPos"));
        user.setInstitution(resultSet.getString("institution"));
        user.setIsPending(resultSet.getByte("isPending"));
        user.setSentConnectionRequest(resultSet.getByte("sentConnectionRequest"));
        
        user.setPrivateCity(resultSet.getByte("privateCity"));
        user.setPrivateCountry(resultSet.getByte("privateCountry"));
        user.setPrivateEmail(resultSet.getByte("privateEmail"));
        user.setPrivateTelephone(resultSet.getByte("privateTelephone"));
        user.setPrivateGender(resultSet.getByte("privateGender"));
        user.setPrivateDateOfBirth(resultSet.getByte("privateDateOfBirth"));
        user.setPrivateProfExp(resultSet.getByte("privateProfExp"));
        user.setPrivateEducation(resultSet.getByte("privateEducation"));
        user.setPrivateSkills(resultSet.getByte("privateSkills"));
        user.setPrivateWorkPos(resultSet.getByte("privateWorkPos"));
        user.setPrivateInstitution(resultSet.getByte("privateInstitution"));
        return user;
    }
	

}
