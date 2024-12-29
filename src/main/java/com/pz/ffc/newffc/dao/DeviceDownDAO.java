package com.pz.ffc.newffc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pz.ffc.newffc.model.DeviceDown;

import io.activej.inject.annotation.Inject;

public final class DeviceDownDAO {
	private static Logger log = LoggerFactory.getLogger(DeviceDownDAO.class);
	
	private final Connection connection;
	
	@Inject
	public DeviceDownDAO(final Connection connection) {
		this.connection = connection;
	}
	
	public void add(final DeviceDown deviceDown) {
		log.debug("Calling add");
		final String sql = "INSERT  INTO devicedown(deviceid, company, storecode, storename, rdatetime, reportdate, timestamping, previousdatetime, frameno, processed) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try(final PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, deviceDown.getDeviceid());
			final String company = deviceDown.getCompany() != null ? deviceDown.getCompany().toUpperCase() : null;
			stmt.setString(2, company);
			stmt.setString(3, deviceDown.getStorecode());
			stmt.setString(4, deviceDown.getStorename());
			final Timestamp rdatetime = Timestamp.valueOf(deviceDown.getRdatetime());
			stmt.setTimestamp(5, rdatetime);
			final Date reportdate = Date.valueOf(LocalDate.now());
			stmt.setDate(6, reportdate);
			stmt.setTimestamp(7, Timestamp.valueOf(LocalDateTime.now()));
			final Timestamp previousdatetime = Timestamp.valueOf(deviceDown.getPreviousdatetime());
			stmt.setTimestamp(5, previousdatetime);
			stmt.setString(9, deviceDown.getFrameno());
			stmt.setBoolean(10, Boolean.FALSE);
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			log.error(e.getLocalizedMessage());
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
	}

}
