package Servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import JavaFiles.VariousFunctions;
import database.dao.job.JobDAO;
import database.dao.job.JobDAOImpl;
import database.dao.jobapplication.JobapplicationDAO;
import database.dao.jobapplication.JobapplicationDAOImpl;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.Job;

/**
 * Servlet implementation class JobHandle
 */
@WebServlet("/JobHandle")
public class JobHandle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JobHandle() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher displayPage;
		JobDAO dao = new JobDAOImpl(true);
		if(request.getParameter("action")!=null) {
			if(request.getParameter("action").equals("getJobs")) {
				request.setAttribute("redirectJobs", "StopLoopJobs");
				//get session's jobs
				Long userId = Long.valueOf((String) request.getSession().getAttribute("id"));
				List<Job> sessionJobs = dao.getSessionJobs(userId);
				for(Job job: sessionJobs) {
					job.setDateInterval(VariousFunctions.getDateInterval(job.getDatePosted()));
				}
				request.setAttribute("sessionJobs", sessionJobs);
				//get connections' jobs
				List<Job> connJobs = dao.getConnectionsJobs(userId);
				for(Job job: connJobs) {
					job.setDateInterval(VariousFunctions.getDateInterval(job.getDatePosted()));
				}
				request.setAttribute("connJobs", connJobs);
				//get jobs sorted by most skills
				UserDAO userDao = new UserDAOImpl(true);
				String skills = userDao.getUserSkills(userId);
				List<Job> skillJobs = dao.list();
				Map<Integer,Integer> jobsMap = new HashMap<Integer,Integer>();
				if(skills != null) {
					List<String> skillsUser = new ArrayList<String>(VariousFunctions.strToArray(skills));
					for(Job job: skillJobs) {
						job.setDateInterval(VariousFunctions.getDateInterval(job.getDatePosted()));
						job.setSkillsArray(VariousFunctions.strToArray(job.getSkills()));
						//compare lists and count differences
						int differences = 0;
						for (int i = 0; i < job.getSkillsArray().size(); i++) {
							for(int j=0;j < skillsUser.size();j++) {
							    if (!skillsUser.get(j).equalsIgnoreCase(job.getSkillsArray().get(i))) {
							    	differences++;
							    }else {
							    	skillsUser.remove(j);
							    }
							}
						}
						jobsMap.put(job.getId().getJobId(),differences);
					}
					//sort map
					jobsMap = VariousFunctions.sortMap(jobsMap);
					List<Job> skillJobsList = new ArrayList<Job>();
					List<Integer> mapKeys = new ArrayList<Integer>(jobsMap.keySet());
					for(Job job: skillJobs) {
						for(Integer key: mapKeys) {
							if(key == job.getId().getJobId()) {
								
								skillJobsList.add(job);
							}
						}
					}
					request.setAttribute("skillJobs",  skillJobsList);
				}
				displayPage = getServletContext().getRequestDispatcher("/jsp_files/jobs.jsp");
				displayPage.forward(request, response);
				return;
			}else if(request.getParameter("action").equals("getJob")) {
				//get specific job
				Long id = Long.valueOf( (String) request.getParameter("id"));
				Job job = dao.findJob(id);
				job.setDateInterval(VariousFunctions.getDateInterval(job.getDatePosted()));
				job.setEducationLevelStr(VariousFunctions.arrayStrToStr(job.getEducationLevel()));
				job.setCompanyTypeStr(VariousFunctions.arrayStrToStr(job.getJobCompanyType()));
				job.setJobFunctionStr(VariousFunctions.arrayStrToStr(job.getJobFunction()));
				job.setSkillsArray(VariousFunctions.strToArray(job.getSkills()));
				request.setAttribute("job", job);
				//check if apply already done
				JobapplicationDAO jobAppDao = new JobapplicationDAOImpl(true);
				request.setAttribute("applied", jobAppDao.checkApplied(id, Long.valueOf((String) request.getSession().getAttribute("id"))));
				displayPage = getServletContext().getRequestDispatcher("/jsp_files/jobItem.jsp");
				displayPage.forward(request, response);
				return;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/jobs.jsp");
		JobDAO dao = new JobDAOImpl(true);
		request.setCharacterEncoding("UTF-8");
		//get pressed button
		if(request.getParameter("edit") != null) {
			Long jobId = Long.valueOf( (String) request.getParameter("edit"));
			Job job = dao.findJob(jobId);
			request.setAttribute("job", job);
			displayPage = getServletContext().getRequestDispatcher("/jsp_files/editJob.jsp");
			displayPage.forward(request, response);
			return;
		}else if(request.getParameter("apply") != null) {
			return;
		}
		
		//action get
		if(request.getParameter("action")!=null) {
			if(request.getParameter("action").equals("getJobs")) {
				request.setAttribute("redirectJobs", "StopLoopJobs");
				//get session's jobs
				Long userId = Long.valueOf((String) request.getSession().getAttribute("id"));
				List<Job> sessionJobs = dao.getSessionJobs(userId);
				for(Job job: sessionJobs) {
					job.setDateInterval(VariousFunctions.getDateInterval(job.getDatePosted()));
				}
				request.setAttribute("sessionJobs", sessionJobs);
				//get connections' jobs
				List<Job> connJobs = dao.getConnectionsJobs(userId);
				for(Job job: connJobs) {
					job.setDateInterval(VariousFunctions.getDateInterval(job.getDatePosted()));
				}
				request.setAttribute("connJobs", connJobs);
				//get jobs sorted by most skills
				UserDAO userDao = new UserDAOImpl(true);
				String skills = userDao.getUserSkills(userId);
				List<Job> skillJobs = dao.list();
				Map<Integer,Integer> jobsMap = new HashMap<Integer,Integer>();
				if(skills != null) {
					List<String> skillsUser = new ArrayList<String>(VariousFunctions.strToArray(skills));
					for(Job job: skillJobs) {
						job.setDateInterval(VariousFunctions.getDateInterval(job.getDatePosted()));
						job.setSkillsArray(VariousFunctions.strToArray(job.getSkills()));
						//compare lists and count differences
						int differences = 0;
						for (int i = 0; i < job.getSkillsArray().size(); i++) {
							for(int j=0;j < skillsUser.size();j++) {
							    if (!skillsUser.get(j).equalsIgnoreCase(job.getSkillsArray().get(i))) {
							    	differences++;
							    }else {
							    	skillsUser.remove(j);
							    }
							}
						}
						jobsMap.put(job.getId().getJobId(),differences);
					}
					//sort map
					jobsMap = VariousFunctions.sortMap(jobsMap);
					List<Job> skillJobsList = new ArrayList<Job>();
					List<Integer> mapKeys = new ArrayList<Integer>(jobsMap.keySet());
					for(Job job: skillJobs) {
						for(Integer key: mapKeys) {
							if(key == job.getId().getJobId()) {
								skillJobsList.add(job);
							}
						}
					}
					request.setAttribute("skillJobs",  skillJobsList);
				}
				displayPage = getServletContext().getRequestDispatcher("/jsp_files/jobs.jsp");
				displayPage.forward(request, response);
				return;
			}else if(request.getParameter("action").equals("getJob")) {
				//get specific job
				Long id = Long.valueOf( (String) request.getParameter("id"));
				Job job = dao.findJob(id);
				job.setDateInterval(VariousFunctions.getDateInterval(job.getDatePosted()));
				job.setEducationLevelStr(VariousFunctions.arrayStrToStr(job.getEducationLevel()));
				job.setCompanyTypeStr(VariousFunctions.arrayStrToStr(job.getJobCompanyType()));
				job.setJobFunctionStr(VariousFunctions.arrayStrToStr(job.getJobFunction()));
				job.setSkillsArray(VariousFunctions.strToArray(job.getSkills()));
				request.setAttribute("job", job);
				//check if apply already done
				JobapplicationDAO jobAppDao = new JobapplicationDAOImpl(true);
				request.setAttribute("applied", jobAppDao.checkApplied(id, Long.valueOf((String) request.getSession().getAttribute("id"))));
				displayPage = getServletContext().getRequestDispatcher("/jsp_files/jobItem.jsp");
				displayPage.forward(request, response);
				return;
			}
		}
		//get post info
		String jobTitle = request.getParameter("jobTitle");
		String company = request.getParameter("jobIndustry");
		String location = request.getParameter("jobLocation");
		String[] jobFunctions = request.getParameterValues("jobFunction");
		//check length of jobFunctions
		if(jobFunctions.length > 3) {
			request.setAttribute("jobFunctionError", "Επιλέξατε πάνω από 3 στοιχεία.");
			if(request.getParameter("action") != null) {
				if(request.getParameter("action").equals("editJob")) {
					Long id = Long.valueOf((String) request.getParameter("jobId"));
					Job getJob = dao.findJob(id);
					request.setAttribute("job", getJob);
					displayPage = getServletContext().getRequestDispatcher("/jsp_files/editJob.jsp");
				}
			}
			else {
				displayPage = getServletContext().getRequestDispatcher("/jsp_files/createJob.jsp");
			}
			displayPage.forward(request, response);
			return;
		}
		String jobType = request.getParameter("employmentType");
		String[] companyTypes = request.getParameterValues("companyIndustry");
		//check length of companyTypes
		if(companyTypes.length > 3) {
			request.setAttribute("companyTypesError", "Επιλέξατε πάνω από 3 στοιχεία.");
			if(request.getParameter("action") != null) {
				if(request.getParameter("action").equals("editJob")) {
					Long id = Long.valueOf((String) request.getParameter("jobId"));
					Job getJob = dao.findJob(id);
					request.setAttribute("job", getJob);
					displayPage = getServletContext().getRequestDispatcher("/jsp_files/editJob.jsp");
				}
			}
			else {
				displayPage = getServletContext().getRequestDispatcher("/jsp_files/createJob.jsp");
			}
			displayPage.forward(request, response);
			return;
		}
		String experienceLevel = request.getParameter("seniorityLevel");
		String description = request.getParameter("jobDescription");
		String skills = request.getParameter("skills");
		//check length of educationLevels
		if(skills.split(",").length > 10) {
			request.setAttribute("skillsError", "Γράψατε πάνω από 10 στοιχεία.");
			if(request.getParameter("action") != null) {
				if(request.getParameter("action").equals("editJob")) {
					Long id = Long.valueOf((String) request.getParameter("jobId"));
					Job getJob = dao.findJob(id);
					request.setAttribute("job", getJob);
					displayPage = getServletContext().getRequestDispatcher("/jsp_files/editJob.jsp");
				}
			}
			else {
				displayPage = getServletContext().getRequestDispatcher("/jsp_files/createJob.jsp");
			}
			displayPage.forward(request, response);
			return;
		}
		Integer experienceFrom = Integer.valueOf(request.getParameter("fromYears"));
		Integer experienceTo = Integer.valueOf(request.getParameter("toYears"));
		if(experienceTo - experienceFrom < 0) {
			request.setAttribute("experienceFromToError", "Η συνολική εμπειρία είναι αρνητική.");
			if(request.getParameter("action") != null) {
				if(request.getParameter("action").equals("editJob")) {
					Long id = Long.valueOf((String) request.getParameter("jobId"));
					Job getJob = dao.findJob(id);
					request.setAttribute("job", getJob);
					displayPage = getServletContext().getRequestDispatcher("/jsp_files/editJob.jsp");
				}
			}
			else {
				displayPage = getServletContext().getRequestDispatcher("/jsp_files/createJob.jsp");
			}
			displayPage.forward(request, response);
			return;
		}
		String[] educationLevels =  request.getParameterValues("educationLevel");
		//check length of educationLevels
		if(educationLevels.length > 5) {
			request.setAttribute("educationLevelError", "Επιλέξατε πάνω από 5 στοιχεία.");
			if(request.getParameter("action") != null) {
				if(request.getParameter("action").equals("editJob")) {
					Long id = Long.valueOf((String) request.getParameter("jobId"));
					Job getJob = dao.findJob(id);
					request.setAttribute("job", getJob);
					displayPage = getServletContext().getRequestDispatcher("/jsp_files/editJob.jsp");
				}
			}
			else {
				displayPage = getServletContext().getRequestDispatcher("/jsp_files/createJob.jsp");
			}
			displayPage.forward(request, response);
			return;
		}
		Double dailySalary =  Double.valueOf(request.getParameter("dailyMoney"));
		//get current time
		Date dNow = new Date();
		
		//set info to object
		Job job = new Job();
		job.setTitle(jobTitle);
		job.setCompany(company);
		job.setLocation(location);
		job.setJobFunction(Arrays.toString(jobFunctions));
		job.setJobType(jobType);
		job.setJobCompanyType(Arrays.toString(companyTypes));
		job.setExperience(experienceLevel);
		job.setDescription(description);
		job.setSkills(skills);
		job.setExperienceFrom(experienceFrom);
		job.setExperienceTo(experienceTo);
		job.setEducationLevel(Arrays.toString(educationLevels));
		job.setDailySalary(dailySalary);
		job.setDatePosted(dNow);

		if(request.getParameter("action") != null) {
			if(request.getParameter("action").equals("editJob")) {
				//update job
				Long id = Long.valueOf((String) request.getParameter("jobId"));
				dao.updateJob(job, id);
				//find job
				Job jobUpdated = dao.findJob(id);
				jobUpdated.setDateInterval(VariousFunctions.getDateInterval(jobUpdated.getDatePosted()));
				jobUpdated.setEducationLevelStr(VariousFunctions.arrayStrToStr(jobUpdated.getEducationLevel()));
				jobUpdated.setCompanyTypeStr(VariousFunctions.arrayStrToStr(jobUpdated.getJobCompanyType()));
				jobUpdated.setJobFunctionStr(VariousFunctions.arrayStrToStr(jobUpdated.getJobFunction()));
				jobUpdated.setSkillsArray(VariousFunctions.strToArray(jobUpdated.getSkills()));
				//go to jobItem
				request.setAttribute("job", jobUpdated);
				displayPage = getServletContext().getRequestDispatcher("/jsp_files/jobItem.jsp");
				displayPage.forward(request, response);
				return;
			}
		}else {
			//create job
			dao.create(job,Long.valueOf((String) request.getSession().getAttribute("id")));
			//go to jobs
			List<Job> jobs = dao.list();
			request.setAttribute("jobs", jobs);
			displayPage = getServletContext().getRequestDispatcher("/jsp_files/jobs.jsp");
			displayPage.forward(request, response);
		}
	}
	

}
