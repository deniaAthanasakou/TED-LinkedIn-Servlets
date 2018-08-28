package JavaFiles;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Statement;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import database.dao.ConnectionFactory;

@WebListener
public class HandleDB implements ServletContextListener{
	public void contextInitialized(ServletContextEvent servletContextEvent) {
    	//drop,create database and insert
		/*String s = new String();
        StringBuffer sb = new StringBuffer();
        String relativeWebPath = "/sql";
        String absoluteDiskPath = servletContextEvent.getServletContext().getRealPath(relativeWebPath);
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
            ConnectionFactory factory = ConnectionFactory.getInstance(true);
            Connection c = factory.getConnection();
            Statement st = c.createStatement();
 
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
        }*/
    }

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	}
}
