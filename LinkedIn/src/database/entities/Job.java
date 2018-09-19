package database.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the job database table.
 * 
 */
@Entity
@NamedQuery(name="Job.findAll", query="SELECT j FROM Job j")
public class Job implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private JobPK id;

	private String company;

	@Column(name="daily_salary")
	private double dailySalary;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_posted")
	private Date datePosted;

	private String description;

	@Column(name="education_level")
	private String educationLevel;

	private String experience;

	@Column(name="experience_from")
	private int experienceFrom;

	@Column(name="experience_to")
	private int experienceTo;

	@Column(name="job_company_type")
	private String jobCompanyType;

	@Column(name="job_function")
	private String jobFunction;

	@Column(name="job_type")
	private String jobType;

	private String location;

	private String skills;

	private String title;

	//bi-directional many-to-one association to User
	@ManyToOne
	private User user;

	public Job() {
	}

	public JobPK getId() {
		return this.id;
	}

	public void setId(JobPK id) {
		this.id = id;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public double getDailySalary() {
		return this.dailySalary;
	}

	public void setDailySalary(double dailySalary) {
		this.dailySalary = dailySalary;
	}

	public Date getDatePosted() {
		return this.datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEducationLevel() {
		return this.educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getExperience() {
		return this.experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public int getExperienceFrom() {
		return this.experienceFrom;
	}

	public void setExperienceFrom(int experienceFrom) {
		this.experienceFrom = experienceFrom;
	}

	public int getExperienceTo() {
		return this.experienceTo;
	}

	public void setExperienceTo(int experienceTo) {
		this.experienceTo = experienceTo;
	}

	public String getJobCompanyType() {
		return this.jobCompanyType;
	}

	public void setJobCompanyType(String jobCompanyType) {
		this.jobCompanyType = jobCompanyType;
	}

	public String getJobFunction() {
		return this.jobFunction;
	}

	public void setJobFunction(String jobFunction) {
		this.jobFunction = jobFunction;
	}

	public String getJobType() {
		return this.jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSkills() {
		return this.skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Transient
	private String dateInterval;

	public String getDateInterval() {
		return dateInterval;
	}

	public void setDateInterval(String dateInterval) {
		this.dateInterval = dateInterval;
	}

	@Transient
	private String educationLevelStr;

	@Transient
	private String companyTypeStr;

	@Transient
	private String jobFunctionStr;

	public String getEducationLevelStr() {
		return educationLevelStr;
	}

	public String getCompanyTypeStr() {
		return companyTypeStr;
	}

	public String getJobFunctionStr() {
		return jobFunctionStr;
	}

	public void setEducationLevelStr(String educationLevelStr) {
		this.educationLevelStr = educationLevelStr;
	}

	public void setCompanyTypeStr(String companyTypeStr) {
		this.companyTypeStr = companyTypeStr;
	}

	public void setJobFunctionStr(String jobFunctionStr) {
		this.jobFunctionStr = jobFunctionStr;
	}

	@Transient
	private List<String> skillsArray;

	public List<String> getSkillsArray() {
		return skillsArray;
	}

	public void setSkillsArray(List<String> skillsArray) {
		this.skillsArray = skillsArray;
	}	

}