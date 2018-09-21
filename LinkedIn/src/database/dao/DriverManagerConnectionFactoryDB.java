package database.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverManagerConnectionFactoryDB extends ConnectionFactoryCreateDB {

	private String url;
    private String username;
    private String password;
    
	public DriverManagerConnectionFactoryDB(String url, String username, String password) {
		this.url = url;
		this.password = password;
		this.username = username;
	}

	@Override
	public
	Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
		
}
