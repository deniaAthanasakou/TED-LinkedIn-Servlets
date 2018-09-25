package database.dao.job;

import java.util.List;

import database.entities.Job;

public interface JobDAO {
	
	public List<Job> list();

    public int create(Job job, int id);
    
    public int count();
    
    public Job findJob(int id);
    
    public int updateJob(Job job, int id);
    
    public List<Job> getSessionJobs(int userId);
    
    public List<Job> getConnectionsJobs(int userId);
    
    public List<Job> getTestSet(int userId);
    
    public List<Job> getTrainSet(int userId);
    
}
