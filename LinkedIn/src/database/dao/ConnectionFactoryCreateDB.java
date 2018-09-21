package database.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public abstract class ConnectionFactoryCreateDB {
	private static String URL;
    private static String DRIVER;
    private static String USERNAME;
    private static String PASSWORD;
    
    //Singleton pattern: Connection factory instance is created only once
    private static ConnectionFactoryCreateDB instance = null;
    
    protected ConnectionFactoryCreateDB() {
    	Properties properties = new Properties();
    	InputStream input = null;
    	
    	try {
    		input=ConnectionFactoryCreateDB.class.getResourceAsStream("dbConfigInit.properties");
    		properties.load(input);
    		
    		ConnectionFactoryCreateDB.DRIVER= properties.getProperty("jdbc.driver");
    		ConnectionFactoryCreateDB.URL= properties.getProperty("jdbc.url");
    		ConnectionFactoryCreateDB.USERNAME= properties.getProperty("jdbc.username");
    		ConnectionFactoryCreateDB.PASSWORD= properties.getProperty("jdbc.password");
    		
    		input.close();
    		input=null;
    		properties=null;
    	}
    	catch (Exception ex) {
    		ex.printStackTrace();
    	}
    }
    
    public static synchronized ConnectionFactoryCreateDB getInstance(boolean pool)  {
     
        if (instance == null)
        {
	        if (!pool) {
	            try {
	                Class.forName(DRIVER);
	            }
	            catch (ClassNotFoundException e) {
	                System.err.println(e.getMessage());
	            }
	            instance = new DriverManagerConnectionFactoryDB(URL, USERNAME, PASSWORD);
	        }
	
	        // Else lookup datasource in the JNDI.
	        else {
	            DataSource dataSource = null;
	            try {
	                
	            	Context context = new InitialContext();
	            	Context envctx = (Context)context.lookup("java:comp/env");
	            	dataSource = (DataSource) envctx.lookup("jdbc/database");
	            } 
	            catch (NamingException e) {
	            	System.err.println(e.getMessage());
	            }
	            instance = new DataSourceConnectionFactoryDB(dataSource);
	        }
        }

        return instance;
    }
    
    

    public abstract Connection getConnection() throws SQLException;
}

