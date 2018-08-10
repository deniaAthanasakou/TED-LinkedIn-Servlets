package database.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

public abstract class ConnectionFactory {
	
		private static String URL;
	    private static String DRIVER;
	    private static String USERNAME;
	    private static String PASSWORD;
	    
	    /*private static final String URL = "jdbc:mysql://localhost:3306/ted";
	    private static final String DRIVER = "com.mysql.jdbc.Driver";
	    private static final String USERNAME = "ted";
	    private static final String PASSWORD = "ted";
	    */
	    
	    //Singleton pattern: Connection factory instance is created only once
	    private static ConnectionFactory instance = null;
	    
	    protected ConnectionFactory() {
	    	Properties properties = new Properties();
	    	InputStream input = null;
	    	
	    	try {
	    		input=ConnectionFactory.class.getResourceAsStream("dbConfig.properties");
	    		properties.load(input);
	    		
	    		ConnectionFactory.DRIVER= properties.getProperty("jdbc.driver");
	    		ConnectionFactory.URL= properties.getProperty("jdbc.url");
	    		ConnectionFactory.USERNAME= properties.getProperty("jdbc.username");
	    		ConnectionFactory.PASSWORD= properties.getProperty("jdbc.password");
	    		
	    		input.close();
	    		input=null;
	    		properties=null;
	    	}
	    	catch (Exception ex) {
	    		ex.printStackTrace();
	    	}
	    }
	    
	    public static synchronized ConnectionFactory getInstance(boolean pool)  {
	     
	        if (instance == null)
	        {
		        if (!pool) {
		            try {
		                Class.forName(DRIVER);
		            }
		            catch (ClassNotFoundException e) {
		                System.err.println(e.getMessage());
		            }
		            instance = new DriverManagerConnectionFactory(URL, USERNAME, PASSWORD);
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
		            instance = new DataSourceConnectionFactory(dataSource);
		        }
	        }

	        return instance;
	    }

	    public abstract Connection getConnection() throws SQLException;
	}