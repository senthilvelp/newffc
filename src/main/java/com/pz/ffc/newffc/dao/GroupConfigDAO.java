package com.pz.ffc.newffc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pz.ffc.newffc.model.Device;
import com.pz.ffc.newffc.model.GroupConfig;
import com.sun.tools.javac.util.Log;

import io.activej.inject.annotation.Inject;

public final class GroupConfigDAO {
	private static Logger log = LoggerFactory.getLogger(GroupConfigDAO.class);
	
	private final Connection connection;
	
	@Inject
	public GroupConfigDAO(final Connection connection) {
		this.connection = connection;
	}
	
	public List<GroupConfig> getDevicesgetGroupConfigs() {
		log.debug("Calling getDevices()");
		
		final String query = "SELECT * FROM GroupConfig";
		final List<GroupConfig> groupConfigs = new ArrayList<>();
		try(final Statement stmt = connection.createStatement()) {
			final ResultSet rs = stmt.executeQuery(query);
		      while (rs.next()) {
		    	  final GroupConfig groupConfig = new GroupConfig();
		    	  groupConfig.setId(rs.getLong("id"));
		    	  groupConfig.setDeviceid(rs.getString("DeviceId"));
		    	  groupConfig.setCompanyid(rs.getLong("CompanyId"));
		    	  groupConfig.setUserid(rs.getLong("UserId"));
		    	  final LocalDateTime timestamping = rs.getTimestamp("TimeStamping").toLocalDateTime();
		    	  groupConfig.setTimestamping(timestamping);
		    	  groupConfigs.add(groupConfig);
		      }
		      return groupConfigs;
		      
		} catch (SQLException ex) {
			log.error(ex.getLocalizedMessage());
			throw new RuntimeException(ex.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
			throw new RuntimeException(ex.getLocalizedMessage());
		}
	}

}
