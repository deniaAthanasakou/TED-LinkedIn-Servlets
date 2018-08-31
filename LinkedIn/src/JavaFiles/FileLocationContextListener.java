package JavaFiles;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class FileLocationContextListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    	String rootPath = System.getProperty("catalina.home");
    	ServletContext ctx = servletContextEvent.getServletContext();
    	String relativePathTemp = ctx.getInitParameter("tempfile.dir");
    	String relativePathRoot = ctx.getInitParameter("LinkedIn.dir");
    	String relativePathUsers = ctx.getInitParameter("users.dir");
    	String relativePathPosts = ctx.getInitParameter("posts.dir");
    	//create dir for users
    	String fullUsersPath = rootPath + File.separator + relativePathTemp +  File.separator + relativePathRoot + File.separator + relativePathUsers;
    	File fileUsers = new File(fullUsersPath);
    	if(!fileUsers.exists()) fileUsers.mkdirs();
    	System.out.println("File Directory created to be used for storing files of users: " + fullUsersPath);
    	ctx.setAttribute("FILES_DIR_FILE_USERS", fileUsers);
    	ctx.setAttribute("FILES_DIR_USERS", fullUsersPath);
    	//create dir for posts
    	String fullPostsPath = rootPath + File.separator + relativePathTemp +  File.separator + relativePathRoot + File.separator + relativePathPosts;
    	File filePosts = new File(fullPostsPath);
    	if(!filePosts.exists()) filePosts.mkdirs();
    	System.out.println("File Directory created to be used for storing files of posts: " + fullPostsPath);
    	ctx.setAttribute("FILES_DIR_FILE_POSTS", filePosts);
    	ctx.setAttribute("FILES_DIR_POSTS", fullPostsPath);
    }

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		//do cleanup if needed
	}

}