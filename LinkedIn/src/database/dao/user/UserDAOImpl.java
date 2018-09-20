package database.dao.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import JavaFiles.VariousFunctions;
import database.dao.ConnectionFactory;
import database.dao.DAOUtil;
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
	private static final String SQL_SELECTED_USERS = "SELECT * FROM User WHERE id IN ("; 
	private static final String SQL_GET_LIKES_AND_COMMENTS = "SELECT user.id, post.id AS postId, name, surname, photoURL, date_liked AS concatDate, '0' isComment from User, Post, ted.like WHERE (post.user_id=? AND User.id=ted.like.user_id AND User.id!=post.user_id AND ted.like.post_id=post.id AND date_liked >=?) "
			+ " UNION SELECT user.id, post.id AS postId, name, surname, photoURL, comment.date_posted AS concatDate, '1' isComment from User, post, comment WHERE (post.user_id=? AND User.id=comment.user_id AND User.id!=post.user_id AND comment.post_id=post.id AND comment.date_posted >=?)"
			+ " ORDER BY concatDate DESC";
	private static final String SQL_GET_APPLICANTS = "SELECT id, name, surname, photoURL FROM User WHERE id IN (SELECT Jobapplication.user_id FROM Jobapplication WHERE job_id = ?)";
	private static final String SQL_GET_SKILLS = "SELECT skills FROM User WHERE id = ?";
	
    private ConnectionFactory factory;
    
    public UserDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }

	@Override
	public User find(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try
		{
			 connection = factory.getConnection();
			 statement = DAOUtil.prepareStatement(connection,SQL_FIND_BY_ID, false, id);
	         resultSet = statement.executeQuery();
		
	        if (resultSet.next()) 
	            user = map(resultSet);
		} 
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }
     
        return user;
	}

	@Override
	public List<User> list() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<User> users = new ArrayList<>();

        try
        {
             connection = factory.getConnection();
             statement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
             resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }

        return users;
	}

	@Override
	public int create(User user) 
	{
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet generatedKeys = null;
		int ret = -1;
		//get values from user entity
		Object[] values = { user.getIsAdmin(), user.getEmail(), user.getPassword(), user.getName(), user.getSurname(), user.getTel(), user.getPhotoURL(),
				DAOUtil.toSqlDate(user.getDateOfBirth()), user.getGender(), user.getCity(), user.getCountry(), user.getHasImage(), user.getProfExp(), user.getEducation(), user.getSkills(), user.getPrivateTelephone(), user.getPrivateEmail(), user.getPrivateGender(), user.getPrivateDateOfBirth(), user.getPrivateProfExp(), user.getPrivateSkills(), user.getPrivateEducation(), user.getPrivateCity(), user.getPrivateCountry(), user.getWorkPos(), user.getInstitution(), user.getPrivateWorkPos(), user.getPrivateInstitution()  };
		
		//connect to DB
		try
		{
			 connection = factory.getConnection();
			 statement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);

						
			int affectedRows = statement.executeUpdate();
			ret = affectedRows;
			if (ret == 0) {
				System.err.println("Creating user failed, no rows affected.");
				return ret;
			}
			try
			{
				generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					user.setId(generatedKeys.getInt(1));
					return ret;
				} 
				else {
					System.err.println("Creating user failed, no generated key obtained.");
					return -1;
				}
			}
			finally {
	            VariousFunctions.closeResultSet(generatedKeys);
	        }
		} 
		catch (SQLException e) {
			System.err.println("SQLException: Creating user failed, no generated key obtained.");
			return ret;
		}
		finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
        }
	}
	
	
	@Override
	public int count() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int size = 0;
		
        try
        {
             connection = factory.getConnection();
             statement = connection.prepareStatement(SQL_COUNT);
             resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
            	size = resultSet.getInt("COUNT(*)");
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }


        return size;
	}
	

	
	
	@Override
	public User matchUserLogin(String email,String password) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try
		{
			 connection = factory.getConnection();
			 statement = DAOUtil.prepareStatement(connection,SQL_FIND_BY_EMAIL_PASSWORD, false, email,password);
	         resultSet = statement.executeQuery();
		
	        if (resultSet.next()) 
	            user = map(resultSet);
		} 
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }
        return user;
	}
	

	
	@Override
	public int updateSettings(int user_id, String email, String password) {
		Connection connection = null;
		PreparedStatement statement = null;
		int affectedRows=0;
		try
		{
			connection = factory.getConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_UPDATE_EMAIL_PASSWORD, false, email, password, user_id);
		
		
	 		affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				System.err.println("Updating user failed, no rows affected.");
				return affectedRows;
			}
			
		} 
		catch (SQLException e) {
			System.err.println("SQLException: Updating user failed.");
			e.printStackTrace();
			return affectedRows;
		}
		finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
        }
		
		return affectedRows;
 		
	}
	
	@Override
	public User getUserProfile(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;
		
		try
		{
			 connection = factory.getConnection();
			 statement = DAOUtil.prepareStatement(connection,SQL_FIND_BY_ID_PROFILE, false, id);
	         resultSet = statement.executeQuery();
		
	        if (resultSet.next()) 
	            user = mapEverything(resultSet);
		} 
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }
     
        return user;
	}
	
	@Override
	public int updateUser(User user, int user_id) {
		Connection connection = null;
		PreparedStatement statement = null;
		int affectedRows=0;
		try 
		{
			connection = factory.getConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_UPDATE_USER, false,  user.getName(), user.getSurname(), user.getTel(), user.getPhotoURL(), user.getDateOfBirth(), user.getGender(), user.getCity(), user.getCountry(), user.getHasImage(), user.getProfExp(), user.getEducation(), user.getSkills(), user.getPrivateTelephone(), user.getPrivateEmail(), user.getPrivateGender(), user.getPrivateDateOfBirth(), user.getPrivateProfExp(), user.getPrivateSkills(), user.getPrivateEducation(), user.getPrivateCity(), user.getPrivateCountry(), user.getWorkPos(), user.getInstitution(), user.getPrivateWorkPos(), user.getPrivateInstitution(), user_id);
		
		
	 		affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				System.err.println("Updating user failed, no rows affected.");
				return affectedRows;
			}
			
		} catch (SQLException e) {
			System.err.println("SQLException: Updating user failed.");
			e.printStackTrace();
			return affectedRows;
		}
		finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
        }
		
		return affectedRows;
	}
	
	@Override
	public List<User> getSelectedUsers(String[] ids){		//gets selected users from admin page in order to generate xml
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		//build statement
		StringBuilder builder = new StringBuilder();

		for( int i = 0 ; i < ids.length; i++ ) {
		    builder.append("?,");
		}
		builder.setCharAt( builder.length() -1, ')');
		String stmt = SQL_SELECTED_USERS + builder.toString();
		
		List<User> users = new ArrayList<>();

        try
        {
             connection = factory.getConnection();
             statement = DAOUtil.prepareStatement(connection, stmt, false, ids);
             resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
                users.add(map(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }

        return users;
	}
	
	@Override
	public List<User> getLikesAndComments(int user_id){
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<User> users = new ArrayList<>();
		
		//date 3 months ago
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -3);
	
		Date olderDate = cal.getTime();
				
        try
        {
             connection = factory.getConnection();
        	 statement = DAOUtil.prepareStatement(connection,SQL_GET_LIKES_AND_COMMENTS, false, user_id,olderDate, user_id, olderDate);
             resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
                users.add(mapForNotifications(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }

        return users;
	}
	
	@Override
	public List<User> getJobApplicants(int jobId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<User> users = new ArrayList<>();
		
		 try
		 {
             connection = factory.getConnection();
        	 statement = DAOUtil.prepareStatement(connection,SQL_GET_APPLICANTS, false, jobId);
             resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
                users.add(mapForApplicant(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
		 finally {
	            VariousFunctions.closeConnection(connection);
	            VariousFunctions.closeStmt(statement);
	            VariousFunctions.closeResultSet(resultSet);
	     }

        return users;
	}
	
	@Override
	public String getUserSkills(int userId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String str = null;

        try {
             connection = factory.getConnection();
             statement = DAOUtil.prepareStatement(connection,SQL_GET_SKILLS,false, userId);
             resultSet = statement.executeQuery();
        
            if (resultSet.next()) {
                str = resultSet.getString("skills");
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }

        return str;
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
	
	private static User mapForNotifications(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setPostId(resultSet.getInt("postId"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setPhotoURL(resultSet.getString("photoURL"));
        user.setIsComment(resultSet.getInt("isComment"));
        return user;
    }
	
	private static User mapForApplicant(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setPhotoURL(resultSet.getString("photoURL"));
        return user;
    }

	
}
