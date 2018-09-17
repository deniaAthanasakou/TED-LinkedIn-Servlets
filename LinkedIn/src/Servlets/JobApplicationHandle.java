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

/**
 * Servlet implementation class JobApplicationHandle
 */
@WebServlet("/JobApplicationHandle")
public class JobApplicationHandle extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JobApplicationHandle() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher displayPage = null;
		JobapplicationDAO dao = new JobapplicationDAOImpl(true);
		JobDAO jobDao = new JobDAOImpl(true);
		//insert to db
		if(request.getParameter("apply") != null) {
			Long jobId = Long.valueOf((String) request.getParameter("jobId"));
			Long userId = Long.valueOf((String) request.getSession().getAttribute("id"));
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
			displayPage = getServletContext().getRequestDispatcher("/jsp_files/jobItem.jsp");
			displayPage.forward(request,response);
			return;
		}else if(request.getParameter("list") != null) {
			//find all applicants
			Long jobId = Long.valueOf((String) request.getParameter("jobId"));
			UserDAO userDao = new UserDAOImpl(true);
			request.setAttribute("applicants", userDao.getJobApplicants(jobId));
			request.setAttribute("jobId", jobId);
			displayPage = getServletContext().getRequestDispatcher("/jsp_files/jobApplicants.jsp");
			displayPage.forward(request,response);
			return;
		}else if(request.getParameter("accept") != null) {
			//accept applicant
			Long jobId = Long.valueOf((String) request.getParameter("jobId"));
			Long userId = Long.valueOf((String) request.getParameter("userId"));
			byte approved = 1;
			dao.updateJobApplication(jobId, userId, approved);
			UserDAO userDao = new UserDAOImpl(true);
			request.setAttribute("applicants", userDao.getJobApplicants(jobId));
			request.setAttribute("jobId", jobId);
			request.setAttribute("approved", 1);
			displayPage = getServletContext().getRequestDispatcher("/jsp_files/jobApplicants.jsp");
			displayPage.forward(request,response);
			return;
		}else if(request.getParameter("decline") != null) {
			//decline applicant
			Long jobId = Long.valueOf((String) request.getParameter("jobId"));
			Long userId = Long.valueOf((String) request.getParameter("userId"));
			//delete applicant
			dao.declineApplicant(jobId, userId);
			UserDAO userDao = new UserDAOImpl(true);
			request.setAttribute("applicants", userDao.getJobApplicants(jobId));
			request.setAttribute("jobId", jobId);
			displayPage = getServletContext().getRequestDispatcher("/jsp_files/jobApplicants.jsp");
			displayPage.forward(request,response);
			return;
		}else {
	    	Long userId = Long.valueOf((String) request.getParameter("userId"));
	    	Long sessionId = Long.valueOf((String) request.getSession().getAttribute("id"));
	    	//check if isConnected and isPending
	    	ConnectionDAO connDao = new ConnectionDAOImpl(true);
	    	User getUser = connDao.checkConnected(userId, sessionId);
	    	//if isConnected
	    	if(getUser.getIsConnected()==1) {
	    		String str = request.getContextPath() + "/jsp_files/publicProfile.jsp?id=" + userId;
	    		response.sendRedirect(str);
	    		return;
	    	}else { 
	    		String strPending = null;
	    		if(getUser.getIsPending()==1) {
	    			strPending = "yes";
	    		}else {
	    			strPending = "no";
	    		}
	    		String str = request.getContextPath() + "/jsp_files/privateProfile.jsp?id=" + userId + "&pending=" + strPending + "&sentRequest=" + getUser.getSentConnectionRequest();
	    		response.sendRedirect(str);
	    		return;
	    	}
		}
		
		
		
	}

}
