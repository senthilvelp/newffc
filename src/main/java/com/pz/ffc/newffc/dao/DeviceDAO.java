package com.pz.ffc.newffc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pz.ffc.newffc.model.Device;

import io.activej.inject.annotation.Inject;

public final class DeviceDAO {
	private static Logger log = LoggerFactory.getLogger(DeviceDAO.class);
	
	private final Connection connection;
	
	@Inject
	public DeviceDAO(final Connection connection) {
		this.connection = connection;
	}
	
	public List<Device> getDevices() {
		log.debug("Calling getDevices()");
		
		final String query = "SELECT * FROM DeviceMaster";
		final List<Device> devices = new ArrayList<>();
		try(final Statement stmt = connection.createStatement()) {
			final ResultSet rs = stmt.executeQuery(query);
		      while (rs.next()) {
		    	  final Device device = new Device();
		    	  device.setId(rs.getLong("id"));
		    	  device.setDeviceid(rs.getString("deviceid"));
		    	  device.setDevicename(rs.getString("devicename"));
		    	  device.setOpentime(rs.getString("openTime"));
		    	  device.setOpentime(rs.getString("closeTime"));
		    	  device.setIsregistered(rs.getBoolean("isRegistered"));
		    	  device.setState(rs.getString("status"));
		    	  device.setAdjustmentfactor(rs.getString("AdjustmentFactor"));
		    	  device.setCity(rs.getString("city"));
		    	  device.setState(rs.getString("state"));
		    	  device.setStorecode(rs.getString("storecode"));
		    	  device.setStorecode(rs.getString("storename"));
		    	  device.setCustomername(rs.getString("custmerName"));
		    	  device.setStorelocation(rs.getString("StoreLocation"));
		    	  devices.add(device);
		      }
		      return devices;
		      
		} catch (SQLException ex) {
			log.error(ex.getLocalizedMessage());
			throw new RuntimeException(ex.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
			throw new RuntimeException(ex.getLocalizedMessage());
		}
	}

}
