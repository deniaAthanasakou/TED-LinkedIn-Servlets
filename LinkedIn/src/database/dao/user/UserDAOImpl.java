package database.dao.user;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.dao.ConnectionFactory;
import database.entities.User;

public class UserDAOImpl implements UserDAO 
{
	
	//prepared Statements
	private static final String SQL_FIND_BY_ID = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country FROM User WHERE id = ?";
	private static final String SQL_FIND_BY_EMAIL = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country FROM User WHERE email = ?";
	private static final String SQL_LIST_ORDER_BY_ID = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country FROM User ORDER BY id";
	//private static final String SQL_INSERT = "INSERT INTO User (email, password, firstname, lastname, birthdate) VALUES (?, MD5(?), ?, ?, ?)";
	private static final String SQL_INSERT = "INSERT INTO User (isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
    
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
		Object[] values = { user.getIsAdmin(), user.getEmail(), user.getPassword(), user.getName(), user.getSurname(), user.getTel(), user.getPhotoURL(),
				DAOUtil.toSqlDate(user.getDateOfBirth()), user.getGender(), user.getCity(), user.getCountry() };

		//connect to DB
		try (Connection connection = factory.getConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);) 
		{
			int affectedRows = statement.executeUpdate();
			ret = affectedRows;
			if (ret == 0) {
				System.err.println("Creating user failed, no rows affected.");
				return ret;
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
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
			System.err.println("Creating user failed, no generated key obtained.");
			return ret;
		}
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
        return user;
    }
	

}
