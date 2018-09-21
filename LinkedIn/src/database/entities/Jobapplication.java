package database.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the jobapplication database table.
 * 
 */
@Entity
@NamedQuery(name="Jobapplication.findAll", query="SELECT j FROM Jobapplication j")
public class Jobapplication implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private JobapplicationPK id;

	private byte approved;

	//bi-directional many-to-one association to Job
	@ManyToOne
	@JoinColumn(name="job_id")
	private Job job;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public Jobapplication() {
	}

	public JobapplicationPK getId() {
		return this.id;
	}

	public void setId(JobapplicationPK id) {
		this.id = id;
	}

	public byte getApproved() {
		return this.approved;
	}

	public void setApproved(byte approved) {
		this.approved = approved;
	}

	public Job getJob() {
		return this.job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}