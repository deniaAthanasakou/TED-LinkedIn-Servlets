package database.dao.jobapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JavaFiles.VariousFunctions;
import database.dao.ConnectionFactory;
import database.dao.DAOUtil;
import database.dao.job.JobDAO;
import database.dao.job.JobDAOImpl;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.Jobapplication;
import database.entities.JobapplicationPK;

public class JobapplicationDAOImpl implements JobapplicationDAO {
	
	//prepared Statements
	private static final String SQL_LIST = "SELECT user_id, job_id, approved FROM JobApplication";
	private static final String SQL_INSERT = "INSERT INTO JobApplication (user_id, job_id, approved) VALUES  (?, ?, ?)";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM JobApplication";
	private static final String SQL_FIND_BY_JOB_ID = "SELECT user_id, job_id, approved FROM JobApplication WHERE job_id=?";
    private static final String SQL_UPDATE_APPROVAL = "UPDATE JobApplication SET approved = ? WHERE job_id = ? AND user_id = ?";
    private static final String SQL_CHECK_APPLY = "SELECT COUNT(*) FROM JobApplication WHERE job_id = ? AND user_id = ?";
    private static final String SQL_DELETE_APPLICANT = "DELETE FROM JobApplication WHERE job_id = ? AND user_id = ?";
	private static final String SQL_COUNT_APPLIED_JOBS = "SELECT COUNT(*) FROM JobApplication WHERE user_id = ?";
	
    private ConnectionFactory factory;
    
    public JobapplicationDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }

	@Override
	public List<Jobapplication> list() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Jobapplication> jobApplications = new ArrayList<>();

        try {
            connection = factory.getConnection();
            statement = connection.prepareStatement(SQL_LIST);
            resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
            	jobApplications.add(map(resultSet));
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

        return jobApplications;
	}

	@Override
	public int create(int jobId, int userId) {
		Connection connection = null;
		PreparedStatement statement = null;
		int ret = -1;
		Object[] values = {userId, jobId, 0};
		//connect to DB
		try 
		{
			 connection = factory.getConnection();
			 statement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);
				
			int affectedRows = statement.executeUpdate();
			ret = affectedRows;
			if (ret == 0) {
				System.err.println("Creating jobApplication failed, no rows affected.");
				return ret;
			}
		} 
		catch (SQLException e) {
			System.err.println("SQLException: Creating jobApplication failed");
			return ret;
		}
		finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
        }

		return ret;
	}

	@Override
	public int count() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		int size = 0;
		
        try {
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
	public List<Jobapplication> findApplications(int jobId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Jobapplication> jobApplications = new ArrayList<>();

        try {
             connection = factory.getConnection();
             statement = DAOUtil.prepareStatement(connection, SQL_FIND_BY_JOB_ID, false, jobId);
             resultSet = statement.executeQuery();
        
            while (resultSet.next()) {
            	jobApplications.add(map(resultSet));
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

        return jobApplications;
	}

	@Override
	public int updateJobApplication(int jobId, int userId, byte approved) {
		Connection connection = null;
		PreparedStatement statement = null;
		int affectedRows=0;
		try {
			connection = factory.getConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_UPDATE_APPROVAL, false, approved, jobId, userId);
		
		
	 		affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				System.err.println("Updating jobApplication failed, no rows affected.");
				return affectedRows;
			}
		} catch (SQLException e) {
			System.err.println("SQLException: Updating jobApplication failed.");
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
	public int checkApplied(int jobId, int userId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		int size=0;
        try {
        	connection = factory.getConnection();
    		statement = DAOUtil.prepareStatement(connection, SQL_CHECK_APPLY, false, jobId, userId);
    		resultSet = statement.executeQuery();
       
            while (resultSet.next()) {
            	size = resultSet.getInt("COUNT(*)");
                }
        	if(size==1) {
        		return 1;
        	}
        	return 0;
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }
        finally {
            VariousFunctions.closeConnection(connection);
            VariousFunctions.closeStmt(statement);
            VariousFunctions.closeResultSet(resultSet);
        }
        return -1;
	}
	
	@Override
	public int declineApplicant(int jobId, int userId) {
		Connection connection = null;
		PreparedStatement statement = null;
		int affectedRows=0;
		try { 
			connection = factory.getConnection();
			statement = DAOUtil.prepareStatement(connection, SQL_DELETE_APPLICANT, false, jobId, userId);
	 		affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				System.err.println("Deleting from jobApplication failed, no rows affected.");
				return affectedRows;
			}
		} catch (SQLException e) {
			System.err.println("SQLException: Deleting from jobApplication failed.");
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
	public int countAppliedJobs(int userId) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		int size = 0;
		
        try {
             connection = factory.getConnection();
             statement = DAOUtil.prepareStatement(connection, SQL_COUNT_APPLIED_JOBS, false, userId);
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
	
	private static Jobapplication map(ResultSet resultSet) throws SQLException {
		Jobapplication jobApplication = new Jobapplication();
		JobapplicationPK jobApplicationPk = new JobapplicationPK();
		jobApplicationPk.setJobId(resultSet.getInt("job_id"));
		jobApplicationPk.setUserId(resultSet.getInt("user_id"));
		jobApplication.setId(jobApplicationPk);
		jobApplication.setApproved(resultSet.getByte("approved"));
		//set objects
		JobDAO jobDao = new JobDAOImpl(true);
		jobApplication.setJob(jobDao.findJob(Integer.valueOf(jobApplicationPk.getJobId())));
		UserDAO userDao = new UserDAOImpl(true);
		jobApplication.setUser(userDao.find(jobApplicationPk.getUserId()));
		
	    return jobApplication;
	}

}
