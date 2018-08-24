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
	private static final String SQL_FIND_BY_ID = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected FROM User WHERE id = ?";
	private static final String SQL_FIND_BY_EMAIL_PASSWORD = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected FROM User WHERE email = ? AND password = ?";
	private static final String SQL_LIST_ORDER_BY_ID = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected FROM User ORDER BY id";
	private static final String SQL_LIST_ORDER_BY_EMAIL = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected FROM User ORDER BY email";
	private static final String SQL_INSERT = "INSERT INTO User (isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM User";
	private static final String SQL_FIND_BY_NAME = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected FROM User WHERE name = ?";
	private static final String SQL_FIND_BY_SURNAME = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected FROM User WHERE surname = ?";
	private static final String SQL_FIND_BY_NAME_AND_SURNAME = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected FROM User WHERE name = ? AND surname = ?";
	private static final String SQL_GET_CONNECTIONS_OF_USER = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected FROM connection, User WHERE user_id = ? AND connectedUser_id = User.id";
	private static final String SQL_GET_USERS_EXCEPT_ONE = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected FROM User WHERE id != ?";
	
	private static final String SQL_UPDATE_USERS_CONNECTED_FIELD = "UPDATE User, connection SET isConnected='1' WHERE connection.user_id=? AND user.id=connection.connectedUser_id";
	private static final String SQL_UPDATE_USERS_DEFAULT_CONNECTED_FIELD = "UPDATE User SET isConnected='0'";
	private static final String SQL_INSERT_INTO_CONNECTION = "INSERT INTO connection (user_id, connectedUser_id) VALUES (?, ?)";

	
	
	
    private ConnectionFactory factory;
    
    public UserDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }

	@Override
	public User find(Long id) {
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
		int ret = -1;
		//get values from user entity
		int isAdmin=0;
		Object[] values = { user.getIsAdmin(), user.getEmail(), user.getPassword(), user.getName(), user.getSurname(), user.getTel(), user.getPhotoURL(),
				DAOUtil.toSqlDate(user.getDateOfBirth()), user.getGender(), user.getCity(), user.getCountry(), user.getHasImage() };

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
	public List<User> searchByName(String name) {

		List<User> users = new ArrayList<>();
        try (
            Connection connection = factory.getConnection();

        	PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_FIND_BY_NAME, false, name);
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
	public List<User> searchBySurname(String surname) {

		List<User> users = new ArrayList<>();
        try (
            Connection connection = factory.getConnection();

        	PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_FIND_BY_SURNAME, false, surname);
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
	public List<User> searchByNameAndSurname(String name, String surname) {

		List<User> users = new ArrayList<>();
        try (
            Connection connection = factory.getConnection();

        	PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_FIND_BY_NAME_AND_SURNAME, false, name, surname);
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
	public List<User> getConnections(int user_id){
		List<User> users = new ArrayList<>();
        try (
            Connection connection = factory.getConnection();

        	PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_GET_CONNECTIONS_OF_USER, false, user_id);
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
	public List<User> listWithConnectedField(int user_id){
		List<User> users = new ArrayList<>();
        try (
            Connection connection = factory.getConnection();

        		PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_CONNECTED_FIELD, false, user_id);
        		PreparedStatement statement3 = connection.prepareStatement(SQL_UPDATE_USERS_DEFAULT_CONNECTED_FIELD);)
		{
			System.err.println("inside first try");
			
			System.out.println(statement);
			
			statement.executeUpdate();		//isConnected=1
			
        	PreparedStatement statement2 = DAOUtil.prepareStatement(connection, SQL_LIST_ORDER_BY_ID, false);
            ResultSet resultSet = statement2.executeQuery();	
            while (resultSet.next()) {
            	users.add(map(resultSet));
            }
            statement3.executeUpdate();		//isConnected=0
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return users;
	}
	
	@Override
	public int connectToUser(int user_id1, int user_id2) {
		int ret=0;
		try (Connection connection = factory.getConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_INSERT_INTO_CONNECTION, true, user_id1, user_id2);) 
		{
			System.out.println(statement);
			
			int affectedRows = statement.executeUpdate();
			ret = affectedRows;
			if (ret == 0) {
				System.err.println("Creating user failed, no rows affected.");
				return ret;
			}
			System.err.println("before second try");
			
		} 
		catch (SQLException e) {
			System.err.println("SQLException: Creating user failed.");
			return ret;
		}
		
		return ret;
		
		
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
        return user;
    }
	

}
