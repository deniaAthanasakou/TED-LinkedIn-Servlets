package Servlets;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import JavaFiles.KNNImpl;
import database.dao.job.JobDAO;
import database.dao.job.JobDAOImpl;
import database.dao.jobapplication.JobapplicationDAO;
import database.dao.jobapplication.JobapplicationDAOImpl;
import database.entities.Job;

@WebServlet("/JobHandle")
public class JobHandle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private JobDAO dao = new JobDAOImpl(true);
	private JobapplicationDAO jobAppDao = new JobapplicationDAOImpl(true);
 
    public JobHandle() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher displayPage;
		if(request.getParameter("action")!=null) {
			if(request.getParameter("action").equals("getJobs")) {
				int userId = Integer.valueOf((String) request.getSession().getAttribute("id"));
				//get session's no of applied jobs
				int noApplied = jobAppDao.countAppliedJobs(userId);
				request.setAttribute("noApplied",noApplied);
				//get session's jobs
				List<Job> sessionJobs = dao.getSessionJobs(userId);
				request.setAttribute("sessionJobs", sessionJobs);
				
				//get connections' jobs
				List<Job> connJobs = dao.getConnectionsJobs(userId);
				request.setAttribute("connJobs", connJobs);
				
				//get jobs depending on data
				//get all jobs - testSet
				List<Job> testSet = dao.getTestSet(userId);
				//get applied jobs - trainingSet
				List<Job> trainSet = dao.getTrainSet(userId);
				//calculate KNN jobs
				List<Job> dataJobs = KNNImpl.getDataJobs(testSet,trainSet);
				request.setAttribute("dataJobs", dataJobs);
				
				displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/jobs.jsp");
				displayPage.forward(request, response);
				return;
			}else if(request.getParameter("action").equals("getJob")) {
				
				//get specific job
				int id = Integer.valueOf( (String) request.getParameter("id"));
				Job job = dao.findJob(id);
				request.setAttribute("job", job);
				
				//check if apply already done
				request.setAttribute("applied", jobAppDao.checkApplied(id, Integer.valueOf((String) request.getSession().getAttribute("id"))));
				displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/jobItem.jsp");
				displayPage.forward(request, response);
				return;
			}else if(request.getParameter("action").equals("createJob")) {
				displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/createJob.jsp");
				displayPage.forward(request, response);
				return;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/jobs.jsp");
		
		request.setCharacterEncoding("UTF-8");
		
		//get pressed button
		if(request.getParameter("edit") != null) {
			int jobId = Integer.valueOf( (String) request.getParameter("edit"));
			Job job = dao.findJob(jobId);
			request.setAttribute("job", job);
			displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/editJob.jsp");
			displayPage.forward(request, response);
			return;
		}else if(request.getParameter("apply") != null) {
			return;
		}
		//action get
		if(request.getParameter("action")!=null) {
			if(request.getParameter("action").equals("getJobs")) {
				
				//get session's jobs
				int userId = Integer.valueOf((String) request.getSession().getAttribute("id"));
				List<Job> sessionJobs = dao.getSessionJobs(userId);
				request.setAttribute("sessionJobs", sessionJobs);
				
				//get connections' jobs
				List<Job> connJobs = dao.getConnectionsJobs(userId);
				request.setAttribute("connJobs", connJobs);
				
				//get jobs depending on data
				//get all jobs - testSet
				List<Job> testSet = dao.getTestSet(userId);
				//get applied jobs - trainingSet
				List<Job> trainSet = dao.getTrainSet(userId);
				//calculate KNN jobs
				List<Job> dataJobs = KNNImpl.getDataJobs(testSet,trainSet);
				request.setAttribute("dataJobs", dataJobs);
				
				displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/jobs.jsp");
				displayPage.forward(request, response);
				return;
			}else if(request.getParameter("action").equals("getJob")) {
				
				//get specific job
				int id = Integer.valueOf( (String) request.getParameter("id"));
				Job job = dao.findJob(id);
				request.setAttribute("job", job);
				
				//check if apply already done
				request.setAttribute("applied", jobAppDao.checkApplied(id, Integer.valueOf((String) request.getSession().getAttribute("id"))));
				displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/jobItem.jsp");
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
			request.setAttribute("jobFunctionError", "More than 3 options were chosen."); 
			if(request.getParameter("action") != null) {
				if(request.getParameter("action").equals("editJob")) {
					int id = Integer.valueOf((String) request.getParameter("jobId"));
					Job getJob = dao.findJob(id);
					request.setAttribute("job", getJob);
					displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/editJob.jsp");
				}
			}
			else {
				displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/createJob.jsp");
			}
			displayPage.forward(request, response);
			return;
		}
		String jobType = request.getParameter("employmentType");
		String[] companyTypes = request.getParameterValues("companyIndustry");
		
		//check length of companyTypes
		if(companyTypes.length > 3) {
			request.setAttribute("companyTypesError", "More than 3 options were chosen."); 
			if(request.getParameter("action") != null) {
				if(request.getParameter("action").equals("editJob")) {
					int id = Integer.valueOf((String) request.getParameter("jobId"));
					Job getJob = dao.findJob(id);
					request.setAttribute("job", getJob);
					displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/editJob.jsp");
				}
			}
			else {
				displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/createJob.jsp");
			}
			displayPage.forward(request, response);
			return;
		}
		String experienceLevel = request.getParameter("seniorityLevel");
		String description = request.getParameter("jobDescription");
		String skills = request.getParameter("skills");
		
		//check length of educationLevels
		if(skills.split(",").length > 10) {
			request.setAttribute("skillsError", "More than 10 skills were added.");
			if(request.getParameter("action") != null) {
				if(request.getParameter("action").equals("editJob")) {
					int id = Integer.valueOf((String) request.getParameter("jobId"));
					Job getJob = dao.findJob(id);
					request.setAttribute("job", getJob);
					displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/editJob.jsp");
				}
			}
			else {
				displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/createJob.jsp");
			}
			displayPage.forward(request, response);
			return;
		}
		Integer experienceFrom = Integer.valueOf(request.getParameter("fromYears"));
		Integer experienceTo = Integer.valueOf(request.getParameter("toYears"));
		if(experienceTo - experienceFrom < 0) {
			request.setAttribute("experienceFromToError", "Wrong input in years of experience");
			if(request.getParameter("action") != null) {
				if(request.getParameter("action").equals("editJob")) {
					int id = Integer.valueOf((String) request.getParameter("jobId"));
					Job getJob = dao.findJob(id);
					request.setAttribute("job", getJob);
					displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/editJob.jsp");
				}
			}
			else {
				displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/createJob.jsp");
			}
			displayPage.forward(request, response);
			return;
		}
		String[] educationLevels =  request.getParameterValues("educationLevel");
		
		//check length of educationLevels
		if(educationLevels.length > 5) {
			request.setAttribute("educationLevelError", "More than 5 options were chosen.");
			if(request.getParameter("action") != null) {
				if(request.getParameter("action").equals("editJob")) {
					int id = Integer.valueOf((String) request.getParameter("jobId"));
					Job getJob = dao.findJob(id);
					request.setAttribute("job", getJob);
					displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/editJob.jsp");
				}
			}
			else {
				displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/createJob.jsp");
			}
			displayPage.forward(request, response);
			return;
		}
		
		Double dailySalary = 0.0;
		try{
		  Double.parseDouble(request.getParameter("dailyMoney"));
		  dailySalary =  Double.valueOf(request.getParameter("dailyMoney"));
		}
		catch(NumberFormatException e){
			request.setAttribute("dailyMoneyError", "Budget was not a number.");
			if(request.getParameter("action") != null) {
				if(request.getParameter("action").equals("editJob")) {
					int id = Integer.valueOf((String) request.getParameter("jobId"));
					Job getJob = dao.findJob(id);
					request.setAttribute("job", getJob);
					displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/editJob.jsp");
				}
			}
			else {
				displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/createJob.jsp");
			}
			displayPage.forward(request, response);
			return;
		}
		
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
				int id = Integer.valueOf((String) request.getParameter("jobId"));
				dao.updateJob(job, id);
				
				//find job
				Job jobUpdated = dao.findJob(id);
				
				//go to jobItem
				request.setAttribute("job", jobUpdated);
				displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/jobItem.jsp");
				displayPage.forward(request, response);
				return;
			}
		}else {
			//create job
			dao.create(job,Integer.valueOf((String) request.getSession().getAttribute("id")));
			
			//go to jobs
			List<Job> jobs = dao.list();
			request.setAttribute("jobs", jobs);
			displayPage = getServletContext().getRequestDispatcher("/JobHandle?action=getJobs");
			displayPage.forward(request, response);
		}
	}
	

}
