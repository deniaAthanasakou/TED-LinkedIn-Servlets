package database.dao.job;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.dao.ConnectionFactory;
import database.dao.DAOUtil;
import database.entities.Job;
import database.entities.JobPK;

public class JobDAOImpl implements JobDAO{
	//prepared Statements
	private static final String SQL_LIST = "SELECT job_id, title, company, location, job_function, job_type, job_company_type, experience, description, skills, experience_from, experience_to, education_level, daily_salary, date_posted, user_id FROM Job";
	private static final String SQL_INSERT = "INSERT INTO Job (title, company, location, job_function, job_type, job_company_type, experience, description, skills, experience_from, experience_to, education_level, daily_salary, date_posted, user_id) VALUES  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,?,?)";
	private static final String SQL_COUNT = "SELECT COUNT(*) FROM Job";
	private static final String SQL_FIND_BY_ID = "SELECT job_id, title, company, location, job_function, job_type, job_company_type, experience, description, skills, experience_from, experience_to, education_level, daily_salary, date_posted, user_id FROM Job WHERE job_id=?";
    private static final String SQL_UPDATE = "UPDATE Job SET title = ?, company = ?, location = ?, job_function = ?, job_type = ?, job_company_type = ?, experience = ?, description = ?, skills = ?, experience_from = ?, experience_to = ?, education_level = ?, daily_salary = ?, date_posted = ? WHERE job_id = ?";
	
    private static final String SQL_FIND_BY_USER_ID = "SELECT job_id, title, company, location, job_function, job_type, job_company_type, experience, description, skills, experience_from, experience_to, education_level, daily_salary, date_posted, user_id FROM Job WHERE user_id=? ORDER BY date_posted DESC";
    private static final String SQL_FIND_JOBS_CONN = "SELECT * FROM Job WHERE Job.user_id IN (SELECT user_id FROM Connection WHERE connectedUser_id = ? UNION SELECT connectedUser_id FROM Connection WHERE user_id = ?) ORDER BY date_posted DESC";
    
    private ConnectionFactory factory;
    
    public JobDAOImpl(boolean pool)
    {
    	factory = ConnectionFactory.getInstance(pool);
    }

	@Override
	public List<Job> list() {
		List<Job> jobs = new ArrayList<>();

        try (
            Connection connection = factory.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_LIST);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
            	jobs.add(map(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return jobs;
	}

	@Override
	public int create(Job job, Long id) 
	{
		int ret = -1;
		//get values from post entity
		Object[] values = {job.getTitle(), job.getCompany(), job.getLocation(), job.getJobFunction(), job.getJobType(), job.getJobCompanyType(), job.getExperience(), job.getDescription(), job.getSkills(), job.getExperienceFrom(), job.getExperienceTo(), job.getEducationLevel(), job.getDailySalary(), DAOUtil.toSqlTimestamp(job.getDatePosted()), id};
		//connect to DB
		try (Connection connection = factory.getConnection();
				PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_INSERT, true, values);) 
		{			
			System.out.println(statement);
			int affectedRows = statement.executeUpdate();
			ret = affectedRows;
			if (ret == 0) {
				System.err.println("Creating post failed, no rows affected.");
				return ret;
			}
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					JobPK jobPk = new JobPK();
					jobPk.setJobId(generatedKeys.getInt(1));
					job.setId(jobPk);
					return ret;
				} 
				else {
					System.err.println("Creating job failed, no generated key obtained.");
					return -1;
				}
			}
		} 
		catch (SQLException e) {
			System.err.println("SQLException: Creating job failed, no generated key obtained.");
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
	public Job findJob(Long id) {
		Job job = null;
		
		try (
			Connection connection = factory.getConnection();
			PreparedStatement statement = DAOUtil.prepareStatement(connection,SQL_FIND_BY_ID, false, id);
	        ResultSet resultSet = statement.executeQuery();)
		{
	        if (resultSet.next()) 
	            job = map(resultSet);
		} 
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
     
        return job;
	}
	
	@Override
	public int updateJob(Job job, Long id) {
		int affectedRows=0;
		try (Connection	connection = factory.getConnection();
			PreparedStatement statement = DAOUtil.prepareStatement(connection, SQL_UPDATE, false,  job.getTitle(), job.getCompany(), job.getLocation(), job.getJobFunction(), job.getJobType(), job.getJobCompanyType(), job.getExperience(), job.getDescription(), job.getSkills(), job.getExperienceFrom(), job.getExperienceTo(), job.getEducationLevel(), job.getDailySalary(), DAOUtil.toSqlTimestamp(job.getDatePosted()), id);)
		
		{
	 		affectedRows = statement.executeUpdate();
			if (affectedRows == 0) {
				System.err.println("Updating job failed, no rows affected.");
				return affectedRows;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("SQLException: Updating job failed.");
			e.printStackTrace();
			return affectedRows;
		}
		return affectedRows;
	}
	
	@Override
	public List<Job> getSessionJobs(Long userId) {
		List<Job> jobs = new ArrayList<>();

        try (
            Connection connection = factory.getConnection();
            PreparedStatement statement = DAOUtil.prepareStatement(connection,SQL_FIND_BY_USER_ID,false,userId);
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
            	jobs.add(map(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return jobs;
	}

	@Override
	public List<Job> getConnectionsJobs(Long userId) {
		List<Job> jobs = new ArrayList<>();

        try (
            Connection connection = factory.getConnection();
            PreparedStatement statement = DAOUtil.prepareStatement(connection,SQL_FIND_JOBS_CONN,false,userId,userId);
        	
            ResultSet resultSet = statement.executeQuery();
        ) {
            while (resultSet.next()) {
            	jobs.add(map(resultSet));
            }
        } 
        catch (SQLException e) {
        	System.err.println(e.getMessage());
        }

        return jobs;
	}
	
	private static Job map(ResultSet resultSet) throws SQLException {
		Job job = new Job();
		JobPK jobPk = new JobPK();
		jobPk.setJobId(resultSet.getInt("job_id"));
		jobPk.setUserId(resultSet.getInt("user_id"));
		job.setId(jobPk);
		job.setTitle(resultSet.getString("title"));
		job.setCompany(resultSet.getString("company"));
		job.setLocation(resultSet.getString("location"));
		job.setJobFunction(resultSet.getString("job_function"));
		job.setJobType(resultSet.getString("job_type"));
		job.setJobCompanyType(resultSet.getString("job_company_type"));
		job.setExperience(resultSet.getString("experience"));
		job.setDescription(resultSet.getString("description"));
		job.setSkills(resultSet.getString("skills"));
		job.setExperienceFrom(resultSet.getInt("experience_from"));
		job.setExperienceTo(resultSet.getInt("experience_to"));
		job.setEducationLevel(resultSet.getString("education_level"));
		job.setDailySalary(resultSet.getDouble("daily_salary"));
		job.setDatePosted(new java.util.Date(resultSet.getTimestamp("date_posted").getTime()));
	    return job;
	}
}
