package database.dao.jobapplication;
import java.util.List;
import database.entities.Jobapplication;

public interface JobapplicationDAO {

	public List<Jobapplication> list();

    public int create(Long jobId, Long userId);
    
    public int count();
    
    public List<Jobapplication> findApplications(Long jobId);
    
    public int updateJobApplication(Long jobId, Long userId, byte approved);
    
    public int checkApplied(Long jobId, Long userId);
    
    public int declineApplicant(Long jobId, Long userId); 
        
}
