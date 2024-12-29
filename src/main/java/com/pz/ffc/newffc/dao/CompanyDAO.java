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

import com.pz.ffc.newffc.model.Company;

import io.activej.inject.annotation.Inject;

public final class CompanyDAO {
	private final Connection connection;
	private static Logger log = LoggerFactory.getLogger(CompanyDAO.class);
	
	@Inject
	public CompanyDAO(final Connection connection){
		this.connection = connection;
	}
	
	public List<Company> getCompanies() {
		log.debug("Calling getCompanies()");
		
		final String query = "SELECT * FROM CompanyMaster";
		final List<Company> companies = new ArrayList<>();
		try(final Statement stmt = connection.createStatement()) {
			final ResultSet rs = stmt.executeQuery(query);
		    while (rs.next()) {
		    	final Company company = new Company();
		    	company.setId(rs.getLong("CompanyID"));
		    	company.setName(rs.getString("CompanyName"));
		    	company.setDescription(rs.getString("Description"));
		    	company.setUserid(rs.getLong("UserId"));
		    	final LocalDateTime ldt = rs.getTimestamp("TimeStamping").toLocalDateTime();
		    	company.setTimestamping(ldt);
		    	company.setClienttype(rs.getString("ClientType"));
		    	company.setEmailrequired(rs.getString("EmailRequired"));
		    	company.setEmailid(rs.getString("Emailid"));
		    	company.setEmailcurrentdate(rs.getBoolean("EmailCurrentDate"));
		    	company.setEmailscheduletime(rs.getInt("EmailScheduleTime"));
		    	companies.add(company);
		    }
		    return companies; 
		} catch (SQLException ex) {
			log.error(ex.getLocalizedMessage());
			throw new RuntimeException(ex.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
			throw new RuntimeException(ex.getLocalizedMessage());	
		}
	}
}
