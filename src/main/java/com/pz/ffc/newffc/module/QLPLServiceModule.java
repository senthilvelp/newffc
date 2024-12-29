package com.pz.ffc.newffc.module;

import java.sql.Connection;

import com.pz.ffc.newffc.dao.QLPLIncomingDAO;

import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;

public class QLPLServiceModule extends AbstractModule{
	
	@Provides
	QLPLIncomingDAO dao(final Connection connection) {
		return new QLPLIncomingDAO(connection);
	}
}

