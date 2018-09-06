package Servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import JavaFiles.AESCrypt;
import JavaFiles.VariousFunctions;
import database.dao.comment.CommentDAO;
import database.dao.comment.CommentDAOImpl;
import database.dao.connection.ConnectionDAO;
import database.dao.connection.ConnectionDAOImpl;
import database.dao.post.PostDAO;
import database.dao.post.PostDAOImpl;
import database.dao.user.UserDAO;
import database.dao.user.UserDAOImpl;
import database.entities.Comment;
import database.entities.Post;
import database.entities.User;

/**
 * Servlet implementation class PostCreation
 */
@WebServlet("/PostHandle")
public class PostHandle extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ServletFileUpload uploader = null;
    @Override
    public void init() throws ServletException{
        DiskFileItemFactory fileFactory = new DiskFileItemFactory();
        File filesDir = (File) getServletContext().getAttribute("FILES_DIR_FILE_POSTS");
        fileFactory.setRepository(filesDir);
        this.uploader = new ServletFileUpload(fileFactory);
        uploader.setHeaderEncoding("UTF-8");
    }
       
    public PostHandle() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PostDAO dao = new PostDAOImpl(true);
		if(request.getParameter("action")!=null) {
			if(request.getParameter("action").equals("getPosts")) {
				HttpSession session = request.getSession();
				long userId = Long.valueOf((String) session.getAttribute("id"));
				List<Post> userPosts = dao.findPosts(userId);
				//get right posts
				request.setAttribute("posts",userPosts);
				request.setAttribute("redirectPosts", "StopLoopPosts");
				//get & set comments & edit post
				CommentDAO commentsDao = new CommentDAOImpl(true);
				for(Post post: userPosts) {
					//set comments
					List<Comment> comments = commentsDao.findComments((long)post.getId());
					//set Date interval in specific format for comments and specific user
					for(Comment comment: comments) {
						comment.setDateInterval(VariousFunctions.getDateInterval(comment.getDatePosted()));
					}
					post.setComments(comments);
					//set no of comments
					post.setNoComments(comments.size());
					//set Date interval in specific format
					post.setDateInterval(VariousFunctions.getDateInterval(post.getDatePosted()));
					//decrypt path and set lists of images,videos,audios
					if(post.getPathFiles() != null) {
						String folderPath = AESCrypt.decrypt(post.getPathFiles());
						VariousFunctions.setFilePathsFromFolders(folderPath,post);
					}
					//set likes
					post.setLikes(dao.countLikes(Long.valueOf(post.getId())));
					//get if liked from user
					post.setLiked(dao.checkLiked(userId, Long.valueOf(post.getId())));
				}	
				//get no of connections
				ConnectionDAO cnxDao = new ConnectionDAOImpl(true);
				int noConnections = cnxDao.countConnections(userId);
				request.setAttribute("noConnections", noConnections);
				//display page
				RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/home.jsp");
				displayPage.forward(request, response);
				return;
			}else if(request.getParameter("action").equals("insertLike")) {
				//insert like
				dao.insertLike(Long.valueOf((String) request.getSession().getAttribute("id")),Long.valueOf(request.getParameter("post_id")));
				//display page
				RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/home.jsp");
				displayPage.forward(request, response);
				return;
			}else if(request.getParameter("action").equals("deleteLike")) {
				//delete like
				dao.deleteLike(Long.valueOf((String) request.getSession().getAttribute("id")),Long.valueOf(request.getParameter("post_id")));
				//display page
				RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/home.jsp");
				displayPage.forward(request, response);
				return;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		PostDAO dao = new PostDAOImpl(true);
		if(request.getParameter("action")!=null) {
			if(request.getParameter("action").equals("getPosts")) {
				HttpSession session = request.getSession();
				long userId = Long.valueOf((String) session.getAttribute("id"));
				List<Post> userPosts = dao.findPosts(userId);
				//get right posts
				request.setAttribute("posts",userPosts);
				request.setAttribute("redirectPosts", "StopLoopPosts");
				//get & set comments & edit post
				CommentDAO commentsDao = new CommentDAOImpl(true);
				for(Post post: userPosts) {
					//set comments
					List<Comment> comments = commentsDao.findComments((long)post.getId());
					//set Date interval in specific format for comments and specific user
					for(Comment comment: comments) {
						comment.setDateInterval(VariousFunctions.getDateInterval(comment.getDatePosted()));
					}
					post.setComments(comments);
					//set no of comments
					post.setNoComments(comments.size());
					//set Date interval in specific format
					post.setDateInterval(VariousFunctions.getDateInterval(post.getDatePosted()));
					//decrypt path and set lists of images,videos,audios
					if(post.getPathFiles() != null) {
						String folderPath = AESCrypt.decrypt(post.getPathFiles());
						VariousFunctions.setFilePathsFromFolders(folderPath,post);
					}
					//set likes
					post.setLikes(dao.countLikes(Long.valueOf(post.getId())));
					//get if liked from user
					post.setLiked(dao.checkLiked(userId, Long.valueOf(post.getId())));
				}	
				//get no of connections
				ConnectionDAO cnxDao = new ConnectionDAOImpl(true);
				int noConnections = cnxDao.countConnections(userId);
				request.setAttribute("noConnections", noConnections);
				//display page
				RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/home.jsp");
				displayPage.forward(request, response);
				return;
			}else if(request.getParameter("action").equals("insertLike")) {
				//insert like
				dao.insertLike(Long.valueOf((String) request.getSession().getAttribute("id")),Long.valueOf(request.getParameter("post_id")));
				//display page
				RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/home.jsp");
				displayPage.forward(request, response);
				return;
			}else if(request.getParameter("action").equals("deleteLike")) {
				//delete like
				dao.deleteLike(Long.valueOf((String) request.getSession().getAttribute("id")),Long.valueOf(request.getParameter("post_id")));
				//display page
				RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/home.jsp");
				displayPage.forward(request, response);
				return;
			}
		}
		//display page
		RequestDispatcher displayPage = getServletContext().getRequestDispatcher("/jsp_files/home.jsp");
		
		ArrayList<FileItem> files = new ArrayList<FileItem>();
		
		Hashtable<String, String> fields = new Hashtable<String, String>();
		
		byte hasImages = 0;
		byte hasVideo = 0;
		byte hasAudio = 0;
		
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
					//images
					if(fileItem.getFieldName().equals("imagesUpload") && !fileItem.getName().equals("") && fileItem.getName() != null) {
						hasImages = 1;
					}else if(fileItem.getFieldName().equals("videoUpload") && !fileItem.getName().equals("") && fileItem.getName() != null){	//videos
						hasVideo = 1;
					}else if(fileItem.getFieldName().equals("audioUpload") && !fileItem.getName().equals("") && fileItem.getName() != null){  //audios
						hasAudio = 1;
					}
					files.add(fileItem);
	            }
			}
		} catch (FileUploadException e) {
			System.out.println("Exception in uploading file.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Exception in uploading file.");
			e.printStackTrace();
		}
		
		//get text
		String text = (String) fields.get("text_post");
		if(text.equals("")) {
			text = null;
		}
				
		//get current time
		Date dNow = new Date();
		
		//insert new post to database
		int nextPost = dao.count() + 1;
		
		//make dirs if needed
		String pathFiles = null;
		String imagesPath = null;
		String videoPath = null;
		String audioPath = null;
		if(hasImages==1 || hasVideo==1 || hasAudio==1) {
			File idFolder = new File(request.getServletContext().getAttribute("FILES_DIR_POSTS") + File.separator + nextPost);
			if(!idFolder.exists()) idFolder.mkdirs();
			imagesPath = request.getServletContext().getAttribute("FILES_DIR_POSTS") + File.separator + nextPost + File.separator + "images";
			videoPath = request.getServletContext().getAttribute("FILES_DIR_POSTS") + File.separator + nextPost + File.separator + "video";
			audioPath = request.getServletContext().getAttribute("FILES_DIR_POSTS") + File.separator + nextPost + File.separator + "audio";
			if(hasImages==1) {
				File imagesFolder = new File(imagesPath);
				if(!imagesFolder.exists()) imagesFolder.mkdirs();
			}
			if(hasVideo==1) {
				File videoFolder = new File(videoPath);
				if(!videoFolder.exists()) videoFolder.mkdirs();
			}
			if(hasAudio==1) {
				File audioFolder = new File(audioPath);
				if(!audioFolder.exists()) audioFolder.mkdirs();
			}
			pathFiles = idFolder.getAbsolutePath();
		}
		
		//empty post
		if((text==null || text.equals("")) && hasImages==0 && hasVideo==0 && hasAudio==0) {
			request.setAttribute("postError", "Nothing to post.");
			displayPage.forward(request, response);
			return;
		}else {
			//get and save files
			for(FileItem file : files) {
				String fileName = file.getName();
				if (fileName != null) {
					fileName = FilenameUtils.getName(fileName);
				}
				
				if(file.getFieldName().equals("imagesUpload") && !file.getName().equals("") && file.getName() != null) {
					File currentFile = new File(imagesPath + File.separator + fileName);
					try {
						file.write(currentFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(file.getFieldName().equals("videoUpload") && !file.getName().equals("") && file.getName() != null){	//videos
					File currentFile = new File(videoPath + File.separator + fileName);
					try {
						file.write(currentFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else if(file.getFieldName().equals("audioUpload") && !file.getName().equals("") && file.getName() != null){		//audios
					File currentFile = new File(audioPath + File.separator + fileName);
					try {
						file.write(currentFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
	    	
			//encrypt path of files
			if(pathFiles != null) {
				pathFiles = AESCrypt.encrypt(pathFiles);
			}
			
			//create new post
			UserDAO userDao = new UserDAOImpl(true);
			User user = userDao.find(Integer.valueOf((String) request.getSession().getAttribute("id")));
			
			Post newPost = new Post(text,dNow,pathFiles,hasAudio, hasImages, hasVideo, 0, user);
			dao.create(newPost);
		
			//go home
			displayPage.forward(request, response);
		}
	}

}
