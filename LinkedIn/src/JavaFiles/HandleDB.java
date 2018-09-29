package JavaFiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class HandleDB implements ServletContextListener{
	// JDBC driver name and database URL
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	private static final String DB_URL = "jdbc:mysql://localhost:3306/";
	
	//  Database credentials
	private static final String USER = "ted";
	private static final String PASS = "ted";
	
	public void contextInitialized(ServletContextEvent servletContextEvent) {
    	//drop,create database and insert
		String s = new String();
        StringBuffer sb = new StringBuffer();
        String relativeWebPath = "/sql";
        String absoluteDiskPath = servletContextEvent.getServletContext().getRealPath(relativeWebPath);
        Connection c = null;
        Statement st = null;
        try{
            FileReader fr = new FileReader(new File(absoluteDiskPath+"/db_handle.sql")); 
            BufferedReader br = new BufferedReader(fr);
 
            while((s = br.readLine()) != null){
                sb.append(s);
            }
            br.close();
 
            // here is our splitter ! We use ";" as a delimiter for each request
            // then we are sure to have well formed statements
            String[] inst = sb.toString().split(";");
            //connect
            Class.forName(JDBC_DRIVER);
            c = DriverManager.getConnection(DB_URL, USER, PASS);
            st = c.createStatement();
 
            for(int i = 0; i<inst.length; i++){
                // we ensure that there is no spaces before or after the request string
                // in order to not execute empty statements
                if(!inst[i].trim().equals("")){
                    st.executeUpdate(inst[i]);
                }
            }
   
        }
        catch(Exception e){
            e.printStackTrace();
        }finally {
        	//finally block used to close resources
            try{
               if(st!=null)
                  c.close();
            }catch(SQLException se){
            }// do nothing
            try{
               if(c!=null)
                  c.close();
            }catch(SQLException se){
               se.printStackTrace();
            }
        }
    }

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {		
	}

}
