package Servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import JavaFiles.VariousFunctions;
import database.dao.connection.ConnectionDAO;
import database.dao.connection.ConnectionDAOImpl;
import database.dao.job.JobDAO;
import database.dao.job.JobDAOImpl;
import database.dao.jobapplication.JobapplicationDAO;
import database.dao.jobapplication.JobapplicationDAOImpl;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.Job;
import database.entities.User;

@WebServlet("/JobApplicationHandle")
public class JobApplicationHandle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private JobapplicationDAO dao = new JobapplicationDAOImpl(true);
	private JobDAO jobDao = new JobDAOImpl(true);
	private UserDAO userDao = new UserDAOImpl(true);
	private ConnectionDAO connDao = new ConnectionDAOImpl(true);


    public JobApplicationHandle() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher displayPage = null;
		
		//insert to db
		if(request.getParameter("apply") != null) {
			int jobId = Integer.valueOf((String) request.getParameter("jobId"));
			int userId = Integer.valueOf((String) request.getSession().getAttribute("id"));
			dao.create(jobId, userId);
			
			//populate
			Job job = jobDao.findJob(jobId);
			job.setDateInterval(VariousFunctions.getDateInterval(job.getDatePosted()));
			job.setEducationLevelStr(VariousFunctions.arrayStrToStr(job.getEducationLevel()));
			job.setCompanyTypeStr(VariousFunctions.arrayStrToStr(job.getJobCompanyType()));
			job.setJobFunctionStr(VariousFunctions.arrayStrToStr(job.getJobFunction()));
			job.setSkillsArray(VariousFunctions.strToArray(job.getSkills()));
			request.setAttribute("job", job);
			
			//check if apply already done
			request.setAttribute("applied", dao.checkApplied(jobId, userId));
			displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/jobItem.jsp");
			displayPage.forward(request,response);
			return;
		}else if(request.getParameter("list") != null) {
			
			//find all applicants
			int jobId = Integer.valueOf((String) request.getParameter("jobId"));
			request.setAttribute("applicants", userDao.getJobApplicants(jobId));
			request.setAttribute("jobId", jobId);
			displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/jobApplicants.jsp");
			displayPage.forward(request,response);
			return;
		}else if(request.getParameter("accept") != null) {
			
			//accept applicant
			int jobId = Integer.valueOf((String) request.getParameter("jobId"));
			int userId = Integer.valueOf((String) request.getParameter("userId"));
			byte approved = 1;
			dao.updateJobApplication(jobId, userId, approved);
			request.setAttribute("applicants", userDao.getJobApplicants(jobId));
			request.setAttribute("jobId", jobId);
			request.setAttribute("approved", 1);
			displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/jobApplicants.jsp");
			displayPage.forward(request,response);
			return;
		}else if(request.getParameter("decline") != null) {
			
			//decline applicant
			int jobId = Integer.valueOf((String) request.getParameter("jobId"));
			int userId = Integer.valueOf((String) request.getParameter("userId"));
			
			//delete applicant
			dao.declineApplicant(jobId, userId);
			request.setAttribute("applicants", userDao.getJobApplicants(jobId));
			request.setAttribute("jobId", jobId);
			displayPage = getServletContext().getRequestDispatcher("/WEB-INF/jsp_files/jobApplicants.jsp");
			displayPage.forward(request,response);
			return;
		}else {
	    	int userId = Integer.valueOf((String) request.getParameter("userId"));
	    	int sessionId = Integer.valueOf((String) request.getSession().getAttribute("id"));
	    	
	    	//check if isConnected and isPending
	    	User getUser = connDao.checkConnected(userId, sessionId);
	    	
	    	//if isConnected
	    	if(getUser.getIsConnected()==1) {
	    		String str = request.getContextPath() + "/PublicProfile?id=" + userId;
	    		response.sendRedirect(str);
	    		return;
	    	}else { 
	    		String strPending = null;
	    		if(getUser.getIsPending()==1) {
	    			strPending = "yes";
	    		}else {
	    			strPending = "no";
	    		}
	    		String str = request.getContextPath() + "/PrivateProfile?id=" + userId + "&pending=" + strPending + "&sentRequest=" + getUser.getSentConnectionRequest();
	    		response.sendRedirect(str);
	    		return;
	    	}
		}	
	}

}
