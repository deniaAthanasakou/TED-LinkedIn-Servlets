package database.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the job database table.
 * 
 */
@Embeddable
public class JobPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="job_id")
	private int jobId;

	@Column(name="user_id", insertable=false, updatable=false)
	private int userId;

	public JobPK() {
	}
	public int getJobId() {
		return this.jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public int getUserId() {
		return this.userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof JobPK)) {
			return false;
		}
		JobPK castOther = (JobPK)other;
		return 
			(this.jobId == castOther.jobId)
			&& (this.userId == castOther.userId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.jobId;
		hash = hash * prime + this.userId;
		
		return hash;
	}
}