package database.dao;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class DataSourceConnectionFactoryDB extends ConnectionFactoryCreateDB {

	private DataSource dataSource;

	DataSourceConnectionFactoryDB(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
	public
    Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
