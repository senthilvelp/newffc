package com.pz.ffc.newffc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import io.activej.inject.annotation.Inject;

public final class QLPLIncomingDAO {

	private static Logger logger = LoggerFactory.getLogger(QLPLIncomingDAO.class);
	
	private final Connection connection;

	@Inject
	public QLPLIncomingDAO(final Connection connection) {
		this.connection = connection;
	}
	
	public void addQLPL(final String message) throws SQLServerException{
		logger.debug("Calling addQLPL(QLPLIncoming qlplIncoming)");
		final PreparedStatement preparedStmt;
		try {
			preparedStmt = connection.prepareStatement("INSERT INTO Deki_QLPL_incomingdata_newffc (message, recived_time) values(?, getdate())");
			preparedStmt.setString(1, message);
			preparedStmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getLocalizedMessage());
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		
	}

}
