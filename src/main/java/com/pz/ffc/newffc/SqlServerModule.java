package com.pz.ffc.newffc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;

public final class SqlServerModule extends AbstractModule{
	
	private static final Logger logger = LoggerFactory.getLogger(SqlServerModule.class);
	
	private static final String MSSQL_PROPERTIES_FILE = "/database.properties";
	
	private static Connection connection;
	
	private static SqlServerModule sqlServerModule;
	
	private SqlServerModule() {
	}
	
	public static SqlServerModule create() {
		if (sqlServerModule == null) {
			sqlServerModule =  new SqlServerModule();
		}
		return sqlServerModule;
	}
	
	@Provides
	static Connection connection() throws SQLException, IOException {
		if (connection == null) {
			connection = sqlServerModule.dataSourceSqlServer().getConnection();
		}
		return connection;
	}
	
	@Provides
	DataSource dataSourceSqlServer() throws SQLException, IOException {
		final HikariConfig hkConfig = new HikariConfig();
		final Properties properties = new Properties();
		final InputStream stream = getClass().getResourceAsStream(MSSQL_PROPERTIES_FILE);
		if (stream == null) {
			logger.error("The properies file database.properties is not found in the resources directory. Create and add the values");
			throw new RuntimeException("The properies file database.properties is not found in the resources directory. Create and add the values");
		}
		properties.load(stream);
		final SQLServerDataSource sqlServerDataSource = new SQLServerDataSource();
		sqlServerDataSource.setUser(properties.getProperty("user"));
		sqlServerDataSource.setPassword(properties.getProperty("password"));
		sqlServerDataSource.setURL(properties.getProperty("url"));
		hkConfig.setMaximumPoolSize(10);
		hkConfig.setDataSource(sqlServerDataSource);
		hkConfig.setRegisterMbeans(false);
		hkConfig.setPoolName("hikariPool");
		return new HikariDataSource(hkConfig);
	}
	
}
