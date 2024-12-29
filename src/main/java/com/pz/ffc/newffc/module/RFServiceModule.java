package com.pz.ffc.newffc.module;

import java.sql.Connection;

import com.pz.ffc.newffc.dao.CompanyDAO;
import com.pz.ffc.newffc.dao.RFDAO;

import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;

public class RFServiceModule extends AbstractModule{
	@Provides
	RFDAO dao(final Connection connection) {
		return new RFDAO(connection);
	}

}
