package com.pz.ffc.newffc;

import java.sql.Connection;


import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;

public class QLPLServiceModule extends AbstractModule{
	
	@Provides
	QLPLIncomingDAO dao(final Connection connection) {
		return new QLPLIncomingDAO(connection);
	}
}

