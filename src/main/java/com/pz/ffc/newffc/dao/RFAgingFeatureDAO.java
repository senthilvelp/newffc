package com.pz.ffc.newffc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pz.ffc.newffc.model.Company;
import com.pz.ffc.newffc.model.RF;
import com.pz.ffc.newffc.model.RFAgingFeature;

import io.activej.inject.annotation.Inject;

public final class RFAgingFeatureDAO {
	private final Connection connection;
	private static Logger log = LoggerFactory.getLogger(RFAgingFeatureDAO.class);
	
	@Inject
	public RFAgingFeatureDAO(final Connection connection){
		this.connection = connection;
	}
	
	public void add(final RFAgingFeature rf) {
		log.debug("Calling Add");
		final String sql = "INSERT INTO RF_AGING_FEATURE(FrameNo,DeviceId,rDateTime,inCounter,outCounter,adultIn, adultOut, childIn, ChildOut, Femalein, Femaleout, Groupin, Groupout, Malein, Maleout, Staffin, Staffout, TimeStamping) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			final PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, rf.getFrameno());
			stmt.setString(2,  rf.getDeviceid());
			final Timestamp rdatetime = Timestamp.valueOf(rf.getRdatetime());
			stmt.setTimestamp(3, rdatetime);
			stmt.setString(4, rf.getIncounter());
			stmt.setString(5,  rf.getOutcounter());
			stmt.setString(6, rf.getAdultin());
			stmt.setString(7, rf.getAdultout());
			stmt.setString(8, rf.getChildin());
			stmt.setString(9, rf.getChildout());
			stmt.setString(10, rf.getFemalein());
			stmt.setString(11, rf.getFemaleout());
			stmt.setString(12, rf.getGroupin());
			stmt.setString(13,rf.getGroupout());
			stmt.setString(14, rf.getMalein());
			stmt.setString(15, rf.getMaleout());
			stmt.setString(16, rf.getStaffin());
			stmt.setString(17, rf.getStaffout());
			stmt.setTimestamp(18, Timestamp.valueOf(rf.getTimestamping()));
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e.getLocalizedMessage());
		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		}
		
	}
	
}
