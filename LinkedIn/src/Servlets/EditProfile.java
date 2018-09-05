package Servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;

import JavaFiles.VariousFunctions;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.Post;
import database.entities.User;

/**
 * Servlet implementation class EditProfile
 */
@WebServlet("/EditProfile")
public class EditProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	 private ServletFileUpload uploader = null;
     @Override
     public void init() throws ServletException{
         DiskFileItemFactory fileFactory = new DiskFileItemFactory();
         File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE_USERS");
         fileFactory.setRepository(filesDir);
         this.uploader = new ServletFileUpload(fileFactory);
         uploader.setHeaderEncoding("UTF-8");
     }
	
    public EditProfile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//cancel was clicked
		String fromAdminId = (String) request.getAttribute("fromAdmin");
		String displayPage = null;
		int user_id = 0;
		if(fromAdminId == null) {
			displayPage="/jsp_files/edit_profile.jsp";
			user_id=Integer.valueOf((String) request.getSession().getAttribute("id"));
		}else {
			displayPage="/jsp_files/edit_profile_admin.jsp";
			user_id=Integer.valueOf((String) fromAdminId);
		}
		System.out.println("in doGet");
		UserDAO dao = new UserDAOImpl(true);
				
		User user=dao.getUserProfile(user_id);
		request.setAttribute("user", user);
		
		int day=1;
		int month=1;
		int year=2018;
		
		if(user.getDateOfBirth()!=null) {
			Calendar cal = Calendar.getInstance(); 
			cal.setTime(user.getDateOfBirth());
			
			day=cal.get(Calendar.DAY_OF_MONTH);
			month=cal.get(Calendar.MONTH)+1;	//zero based
			year=cal.get(Calendar.YEAR);
			
			 
		}
		
		request.setAttribute("day", day);
		request.setAttribute("month",month );
		request.setAttribute("year", year);
		
		RequestDispatcher view = request.getRequestDispatcher(displayPage);
	    view.forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//update database
		System.out.println("in doPost");
		
		FileItem imageItem = null;
		Hashtable<String, String> fields = new Hashtable<String, String>();
		
		//get fields
		if(!ServletFileUpload.isMultipartContent(request)){
			throw new ServletException("Content type is not multipart/form-data");
		}

		try {
			List<FileItem> fileItemsList = uploader.parseRequest(request);
			Iterator<FileItem> fileItemsIterator = fileItemsList.iterator();
			while(fileItemsIterator.hasNext()){
				FileItem fileItem = fileItemsIterator.next();
				if (fileItem.isFormField()) {
	                // Process regular form field (input type="text|radio|checkbox|etc", select, etc).
					fields.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
	            } else {
					imageItem = fileItem;
	            }
			}
		} catch (FileUploadException e) {
			System.out.println("Exception in uploading file.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception in uploading file.");
			e.printStackTrace();
		}
		
		response.setContentType("text/html;charset=UTF-8");
	
		String name = (String) fields.get("name");
		String surname = (String) fields.get("surname");
		String telephone = (String) fields.get("telephone");
		int day=Integer.parseInt((String) fields.get("day"));
		int month=Integer.parseInt((String) fields.get("month"));
		int year=Integer.parseInt((String) fields.get("year"));
		String gender=(String) fields.get("gender");
		String country=(String) fields.get("country");
		String city=(String) fields.get("city");
		String profExp=(String) fields.get("work");
		String education=(String) fields.get("education");
		String skills=(String) fields.get("skills");
		String workPos=(String) fields.get("workPos");
		String institution=(String) fields.get("institution");
		
		String removedImage=(String) fields.get("removedImage");
		
		byte pr_email=0;
		if((String) fields.get("pr_email")!=null)
			pr_email=1;
		byte pr_tel=0;
		if((String) fields.get("pr_telephone")!=null)
			pr_tel=1;
		byte pr_dateOfBirth=0;
		if((String) fields.get("pr_dateOfBirth")!=null)
			pr_dateOfBirth=1;
		byte pr_gender=0;
		if((String) fields.get("pr_gender")!=null)
			pr_gender=1;
		byte pr_country=0;
		if((String) fields.get("pr_country")!=null)
			pr_country=1;
		byte pr_city=0;
		if((String) fields.get("pr_city")!=null)
			pr_city=1;
		byte pr_profExp=0;
		if((String) fields.get("pr_profExp")!=null)
			pr_profExp=1;
		byte pr_education=0;
		if((String) fields.get("pr_education")!=null)
			pr_education=1;
		byte pr_skills=0;
		if((String) fields.get("pr_skills")!=null)
			pr_skills=1;
		byte pr_workPos=0;
		if((String) fields.get("pr_workPos")!=null)
			pr_workPos=1;
		byte pr_institution=0;
		if((String) fields.get("pr_institution")!=null)
			pr_institution=1;
		
		
		
	
		System.out.println(name+" "+surname+" "+telephone+" "+gender+" "+ day+ " "+ month+ " "+ year+ " "+country+" "+city+" "+profExp+" "+education+" "+skills+"!"+pr_email+"!");

		VariousFunctions vf = new VariousFunctions();   
		
		//validators
		//check phone number
		if(telephone!=null && !vf.isBlank(telephone)) {	
			telephone=telephone.replaceAll("[\\D]","");
			System.out.println(telephone);
			if(telephone.length()!=10 ) {
				request.setAttribute("editError", "Invalid phone number was given as input.");
				doGet(request, response);
				return;
			}
		}
		
		int genderNum=0;
		if(gender!=null) {
			if(gender.equals("male"))
				genderNum=1;
			else if(gender.equals("female"))
				genderNum=2;
		}
		
		
		if(vf.isBlank(name)) {
			request.setAttribute("editError", "Invalid name was given as input.");
			doGet(request, response);
			return;
		}
		if(vf.isBlank(surname)) {
			request.setAttribute("editError", "Invalid surname was given as input.");
			doGet(request, response);
			return;
		}
		
		if(vf.isBlank(city)) {
			city=null;
		}
		if(vf.isBlank(profExp)) {
			profExp=null;
		}
		if(vf.isBlank(education)) {
			education=null;
		}
		if(vf.isBlank(skills)) {
			skills=null;
		}
		if(vf.isBlank(workPos)) {
			workPos=null;
		}
		if(vf.isBlank(institution)) {
			institution=null;
		}
		
		Date dateOfBirth = new DateTime(year, month, day, 0, 0).toDate();
		
		UserDAO dao = new UserDAOImpl(true);

		//insert photoUrl code here
		
		//must insert user to database
		String photoURL = null;
		byte hasImage;
		//create folder
		String fromAdminId = request.getParameter("fromAdmin");
		int user_id = 0;
		if(fromAdminId == null) {
			user_id = Integer.valueOf((String) request.getSession().getAttribute("id"));
		}else {
			user_id = Integer.valueOf((String) fromAdminId);
			request.setAttribute("fromAdmin", fromAdminId);
		}
		File idFolder = new File(request.getServletContext().getAttribute("FILES_DIR_USERS") + File.separator + user_id);
    	idFolder.mkdirs();
    	
    	
    	System.out.println("image:" +imageItem+"!");
    	System.out.println("removed:" +removedImage+"!");
    	
    	//save image
    	if(imageItem!= null && imageItem.getName() != null && !imageItem.getName().equals("") ) {

			String fileName = imageItem.getName();
			if (fileName != null) {
				fileName = FilenameUtils.getName(fileName);
			} 
			File file = new File(idFolder + File.separator + fileName);
			try {
				imageItem.write(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			photoURL = file.getAbsolutePath();
			hasImage = 1;
		}else {
			
			if(removedImage==null || removedImage.equals("")) {		//keep same image
				if(fromAdminId == null) {
					photoURL =(String) request.getSession().getAttribute("image");
				}else {
					photoURL =(String) dao.find(user_id).getPhotoURL();
				}

				hasImage = 1;
			}
			else {
			
				photoURL = request.getContextPath() + "/images/default-user.png";
				hasImage = 0;
			}
		}

		
		
		byte isAdmin=0;
		User updatedUser = new User(city, country, dateOfBirth, "", genderNum, isAdmin, name, "", photoURL, surname, telephone, hasImage, null, profExp, skills, education, workPos, institution, pr_city,pr_country,pr_dateOfBirth,pr_education,pr_email,pr_gender,pr_skills,pr_profExp,pr_tel, pr_workPos, pr_institution);
		
		int result = dao.updateUser(updatedUser, user_id);
		if(result==0) {
			request.setAttribute("editError", "Oups! Something went wrong.");
		}
		else {
			if(fromAdminId == null) {
				request.setAttribute("correctUpdate", "done");
				
				//get session
				HttpSession session = request.getSession();
				//update values
				session.setAttribute("name",updatedUser.getName());
				session.setAttribute("surname",updatedUser.getSurname());
				session.setAttribute("image",updatedUser.getPhotoURL());
			}
		}
 
		doGet(request, response);
		
	}

}
