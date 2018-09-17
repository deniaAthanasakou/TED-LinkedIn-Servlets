package database.dao.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import database.dao.ConnectionFactory;
import database.dao.DAOUtil;
import database.dao.user.UserDAO;
import database.entities.User;

public class ConnectionDAOImpl implements ConnectionDAO {
	
	private static final String SQL_FIND_BY_NAME = "SELECT * FROM User WHERE name = ? ORDER BY surname";
	private static final String SQL_FIND_BY_SURNAME = "SELECT * FROM User WHERE surname = ? ORDER BY name";
	private static final String SQL_FIND_BY_NAME_AND_SURNAME = "SELECT * FROM User WHERE name = ? AND surname = ? ORDER BY name, surname";
	private static final String SQL_LIST_ORDER_BY_NAME = "SELECT * FROM User ORDER BY name, surname";

	private static final String SQL_LIST_ORDER_BY_EMAIL = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected, isPending, sentConnectionRequest FROM User ORDER BY email";

	private static final String SQL_GET_CONNECTIONS_OF_USER = "SELECT * FROM connection, User WHERE ((user_id = ? AND connectedUser_id = User.id) OR (connectedUser_id = ? AND user_id = User.id)) AND approved=1 ORDER BY name, surname";
	private static final String SQL_GET_USERS_EXCEPT_ONE = "SELECT * FROM User WHERE id != ?";
	private static final String SQL_INSERT_INTO_CONNECTION = "INSERT INTO connection (user_id, connectedUser_id, dateSent, approved) VALUES (?, ?, ?, 0)";

	private static final String SQL_ARE_USERS_CONNECTED = "SELECT 1 FROM connection WHERE ((connection.user_id=? AND connection.connectedUser_id=?) OR (connection.connectedUser_id=? AND connection.user_id=?)) AND approved=1";
	private static final String SQL_IS_CONNECTION_PENDING = "SELECT 1 FROM connection WHERE ((connection.user_id=? AND connection.connectedUser_id=?) OR (connection.connectedUser_id=? AND connection.user_id=?)) AND approved=0";
	private static final String SQL_SENT_CONNECTION_REQUEST = "SELECT 1 FROM connection WHERE connection.user_id=? AND connection.connectedUser_id=?  AND approved=0";
	
	private static final String SQL_COUNT_CONNECTIONS = "SELECT COUNT(*) FROM connection, User WHERE ((user_id = ? AND connectedUser_id = User.id) OR (connectedUser_id = ? AND user_id = User.id)) AND approved=1";
	private static final String SQL_UPDATE_USERS_CONNECTED_FIELD = "UPDATE User, connection SET isConnected='1' WHERE ((connection.user_id=? AND user.id=connection.connectedUser_id) OR (connection.connectedUser_id=? AND user.id=connection.user_id)) AND connection.approved=1";
	private static final String SQL_UPDATE_USERS_PENDING_FIELD = "UPDATE User, connection SET isPending='1' WHERE ((connection.user_id=? AND user.id=connection.connectedUser_id) OR (connection.connectedUser_id=? AND user.id=connection.user_id)) AND connection.approved=0";
	private static final String SQL_UPDATE_USERS_SENT_CONNECTION_REQUEST_FIELD = "UPDATE User, connection SET sentConnectionRequest='1' WHERE connection.connectedUser_id=? AND user.id=connection.user_id AND connection.approved=0";
	private static final String SQL_UPDATE_USERS_DEFAULT_CONNECTED_PENDING_SENT_CONNECTION_REQUEST_FIELD = "UPDATE User SET isConnected='0', isPending='0', sentConnectionRequest='0'";
	
	private static final String SQL_UPDATE_CONNECTION_APPROVED = "UPDATE connection SET approved='1' WHERE ((connection.connectedUser_id=? AND connection.user_id=?) OR (connection.user_id=? AND connection.connectedUser_id=?)) AND connection.approved=0";
	private static final String SQL_UPDATE_CONNECTION_REJECT = "DELETE FROM connection WHERE ((connection.connectedUser_id=? AND connection.user_id=?) OR (connection.user_id=? AND connection.connectedUser_id=?))";
	private static final String SQL_FIND_CONNECTIONS_PENDING = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected, isPending, sentConnectionRequest, prof_exp, education, skills, privateTelephone, privateEmail, privateGender, privateDateOfBirth, privateProfExp, privateSkills, privateEducation, privateCity, privateCountry, workPos, institution, privateWorkPos, privateInstitution FROM User, connection WHERE connection.connectedUser_id=? AND connection.user_id=user.id AND connection.approved=0 ORDER BY dateSent DESC";
	
	private static final String SQL_CHECK_CONNECTED = "SELECT id, isAdmin, email, password, name, surname, tel, photoURL, dateOfBirth, gender, city, country, hasImage, isConnected, prof_exp, education, skills, privateEmail, privateDateOfBirth, privateTelephone, privateGender, privateCountry, privateCity, privateProfExp, privateEducation, privateSkills, workPos, institution, privateWorkPos, privateInstitution, isPending, sentConnectionRequest FROM User, Connection WHERE (user_id = ? AND User.id = ?) OR (User.id = ? AND connectedUser_id = ? )";
	private ConnectionFactory factory;
	
	 public ConnectionDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }
	
	
	
	@Override
	public List<User> searchByName(String name, int user_id) {

		List<User> users = new ArrayList<>();
        try (
            Connection connection = factory.getConnection();
        		
    		PreparedStatement statement2 = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_CONNECTED_FIELD, false, user_id, user_id);
        	PreparedStatement statement3 = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_PENDING_FIELD, false, user_id, user_id);
            PreparedStatement statement4 = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_SENT_CONNECTION_REQUEST_FIELD, false, user_id);
        		
        		
    		PreparedStatement statement5 = connection.prepareStatement(SQL_UPDATE_USERS_DEFAULT_CONNECTED_PENDING_SENT_CONNECTION_REQUEST_FIELD);	

        	PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_FIND_BY_NAME, false, name);
           
        		
        ) {
        	 statement2.executeUpdate();		//isConnected=1
        	 statement3.executeUpdate();		//isPending=1
        	 statement4.executeUpdate();		//sentConnectionRequest=1
        	 ResultSet resultSet = statement.executeQuery();	
        	
            while (resultSet.next()) {
            	users.add(mapEverything(resultSet));
            }
            statement5.executeUpdate();		//isConnected=0
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return users;
	}
	
	@Override
	public List<User> searchBySurname(String surname, int user_id) {

		List<User> users = new ArrayList<>();
        try (
            Connection connection = factory.getConnection();
        		
        		PreparedStatement statement2 = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_CONNECTED_FIELD, false, user_id, user_id);
            	PreparedStatement statement3 = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_PENDING_FIELD, false, user_id, user_id);
                PreparedStatement statement4 = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_SENT_CONNECTION_REQUEST_FIELD, false, user_id);
            		
            		
        		PreparedStatement statement5 = connection.prepareStatement(SQL_UPDATE_USERS_DEFAULT_CONNECTED_PENDING_SENT_CONNECTION_REQUEST_FIELD);	


        	PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_FIND_BY_SURNAME, false, surname);
           
        		
        ) {
        	statement2.executeUpdate();		//isConnected=1
        	statement3.executeUpdate();		//isPending=1
        	statement4.executeUpdate();		//sentConnectionRequest=1
       	 	ResultSet resultSet = statement.executeQuery();	
       	
           while (resultSet.next()) {
           	users.add(mapEverything(resultSet));
           }
           statement5.executeUpdate();		//isConnected=0
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return users;
	}
	
	@Override
	public List<User> searchByNameAndSurname(String name, String surname, int user_id) {

		List<User> users = new ArrayList<>();
        try (
            Connection connection = factory.getConnection();

    		PreparedStatement statement2 = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_CONNECTED_FIELD, false, user_id, user_id);
        	PreparedStatement statement3 = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_PENDING_FIELD, false, user_id, user_id);
            PreparedStatement statement4 = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_SENT_CONNECTION_REQUEST_FIELD, false, user_id);
        		
        		
    		PreparedStatement statement5 = connection.prepareStatement(SQL_UPDATE_USERS_DEFAULT_CONNECTED_PENDING_SENT_CONNECTION_REQUEST_FIELD);	
        	
        	PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_FIND_BY_NAME_AND_SURNAME, false, name, surname);
           
        		
        ) {
        	statement2.executeUpdate();		//isConnected=1
        	statement3.executeUpdate();		//isPending=1
        	statement4.executeUpdate();		//sentConnectionRequest=1
       	 	ResultSet resultSet = statement.executeQuery();	
       	
           while (resultSet.next()) {
           	users.add(mapEverything(resultSet));
           }
           statement5.executeUpdate();		//isConnected=0
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return users;
	}
	
	@Override
	public List<User> getConnections(int user_id){
		List<User> users = new ArrayList<>();
        try (
            Connection connection = factory.getConnection();

        	PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_GET_CONNECTIONS_OF_USER, false, user_id, user_id);
            ResultSet resultSet = statement.executeQuery();	
        		
        ) {
            while (resultSet.next()) {
            	users.add(mapEverything(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return users;
	}
	
	@Override
	public List<User> listWithConnectedPendingField(int user_id){
		List<User> users = new ArrayList<>();
        try (
            Connection connection = factory.getConnection();

        		PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_CONNECTED_FIELD, false, user_id, user_id);
        		PreparedStatement statement2 = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_PENDING_FIELD, false, user_id, user_id);	
        		PreparedStatement statement4 = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_SENT_CONNECTION_REQUEST_FIELD, false, user_id);
        		PreparedStatement statement5 = connection.prepareStatement(SQL_UPDATE_USERS_DEFAULT_CONNECTED_PENDING_SENT_CONNECTION_REQUEST_FIELD);)
		{
			System.err.println("inside listWithConnectedPendingField");
			
			System.out.println(statement4);
			
			statement.executeUpdate();		//isConnected=1
			statement2.executeUpdate();		//isPending=1
			statement4.executeUpdate();		//sentConnectionRequest=1
			
        	PreparedStatement statement3 = DAOUtil.prepareStatement(connection, SQL_LIST_ORDER_BY_NAME, false);
            ResultSet resultSet = statement3.executeQuery();	
            while (resultSet.next()) {
            	users.add(mapEverything(resultSet));
            }
            statement5.executeUpdate();		//fields=0
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return users;
	}
	
	@Override
	public int connectToUser(int user_id1, int user_id2) {
		int ret=0;
		//get current time
		Date dNow = new Date();
		try (Connection connection = factory.getConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_INSERT_INTO_CONNECTION, true, user_id1, user_id2, dNow);) 
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
	
	
	@Override
	public List<User> existingListWithConnectedField(int user_id, List<User> users){
		byte trueStmt=1;
		try {
			Connection connection = factory.getConnection();
			
			for (User user:users){
				
				PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_ARE_USERS_CONNECTED, false,  user_id, user.getId(), user_id, user.getId());
				ResultSet resultSet = statement.executeQuery();
				
				if (resultSet.next() ) {		//found connection
					user.setIsConnected(trueStmt);
				}
				
				PreparedStatement statement2 = DAOUtil.prepareStatement(connection, SQL_IS_CONNECTION_PENDING, false,  user_id, user.getId(), user_id, user.getId());
				ResultSet resultSet2 = statement2.executeQuery();
				
				if (resultSet2.next() ) {		//found connection
					user.setIsPending(trueStmt);
				}
				
				PreparedStatement statement3 = DAOUtil.prepareStatement(connection, SQL_SENT_CONNECTION_REQUEST, false,   user.getId(), user_id);
				System.out.println(user.getId() + " " +user_id);
				ResultSet resultSet3 = statement3.executeQuery();
				
				if (resultSet3.next() ) {		//found connection
					user.setSentConnectionRequest(trueStmt);
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

        return users;
	}

	@Override
	public int countConnections(long user_id) {

		int size = 0;
		
        try (
            Connection connection = factory.getConnection();
            PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_COUNT_CONNECTIONS, false, user_id, user_id);
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
	public int acceptConnection(int user_id1, int user_id2) {
		 try (
            Connection connection = factory.getConnection();
    		PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_UPDATE_CONNECTION_APPROVED, false, user_id1, user_id2, user_id1, user_id2);
        ) {
        	statement.executeUpdate();		//approved=1
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
		 return 1;
	}
	 
	@Override
	public int rejectConnection(int user_id1, int user_id2) {
		 try (
            Connection connection = factory.getConnection();
    		PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_UPDATE_CONNECTION_REJECT, false, user_id1, user_id2, user_id1, user_id2);
        ) {
        	statement.executeUpdate();		//approved=1
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
		 return 1;
	}
	
	@Override
	public List<User> getConnectionRequestsPending(int user_id){
		
		List<User> users = new ArrayList<>();
        try (
            Connection connection = factory.getConnection();

        	PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_FIND_CONNECTIONS_PENDING, false, user_id);
            ResultSet resultSet = statement.executeQuery();	
        		
        ) {
            while (resultSet.next()) {
            	users.add(mapEverything(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return users;
	}
	
	@Override
	public User checkConnected(Long userId, Long sessionId) {
		User user = null;
        try (
            Connection connection = factory.getConnection();

        		PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_CONNECTED_FIELD, false, sessionId, sessionId);
        		PreparedStatement statement2 = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_PENDING_FIELD, false, sessionId, sessionId);	
        		PreparedStatement statement4 = DAOUtil.prepareStatement(connection, SQL_UPDATE_USERS_SENT_CONNECTION_REQUEST_FIELD, false, sessionId);
        		PreparedStatement statement5 = connection.prepareStatement(SQL_UPDATE_USERS_DEFAULT_CONNECTED_PENDING_SENT_CONNECTION_REQUEST_FIELD);)
		{
			System.err.println("inside listWithConnectedPendingField");
			
			System.out.println(statement4);
			
			statement.executeUpdate();		//isConnected=1
			statement2.executeUpdate();		//isPending=1
			statement4.executeUpdate();		//sentConnectionRequest=1
			
        	PreparedStatement statement3 = DAOUtil.prepareStatement(connection, SQL_CHECK_CONNECTED, false, sessionId, userId, userId, sessionId);
            ResultSet resultSet = statement3.executeQuery();	
            if (resultSet.next()) {
            	user = mapEverything(resultSet);
            }
            statement5.executeUpdate();		//fields=0
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return user;
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
