package database.dao.jobapplication;
import java.util.List;
import database.entities.Jobapplication;

public interface JobapplicationDAO {

	public List<Jobapplication> list();

    public int create(int jobId, int userId);
    
    public int count();
    
    public List<Jobapplication> findApplications(int jobId);
    
    public int updateJobApplication(int jobId, int userId, byte approved);
    
    public int checkApplied(int jobId, int userId);
    
    public int declineApplicant(int jobId, int userId); 
        
}
