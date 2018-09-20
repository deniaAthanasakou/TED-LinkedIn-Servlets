package database.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the jobapplication database table.
 * 
 */
@Embeddable
public class JobapplicationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id", insertable=false, updatable=false)
	private int userId;

	@Column(name="job_id", insertable=false, updatable=false)
	private int jobId;

	public JobapplicationPK() {
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getJobId() {
		return this.jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof JobapplicationPK)) {
			return false;
		}
		JobapplicationPK castOther = (JobapplicationPK)other;
		return 
			(this.userId == castOther.userId)
			&& (this.jobId == castOther.jobId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId;
		hash = hash * prime + this.jobId;
		
		return hash;
	}
}