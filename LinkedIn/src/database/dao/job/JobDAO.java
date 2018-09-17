package database.dao.job;

import java.util.List;

import database.entities.Job;

public interface JobDAO {
	
	public List<Job> list();

    public int create(Job job, Long id);
    
    public int count();
    
    public Job findJob(Long id);
    
    public int updateJob(Job job, Long id);
    
    public List<Job> getSessionJobs(Long userId);
    
    public List<Job> getConnectionsJobs(Long userId);
}
