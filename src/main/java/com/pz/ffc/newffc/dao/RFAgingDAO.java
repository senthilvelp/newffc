package com.pz.ffc.newffc.dao;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.activej.inject.annotation.Inject;

public final class RFAgingDAO {
	private static final Logger log = LoggerFactory.getLogger(RFAgingDAO.class);
	private final Connection connection;
	
	@Inject
	public RFAgingDAO(final Connection connection) {
		this.connection = connection;
	}
}
