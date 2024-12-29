package com.pz.ffc.newffc.module;

import java.sql.Connection;

import com.pz.ffc.newffc.dao.CompanyDAO;
import com.pz.ffc.newffc.dao.RFAgingFeatureDAO;
import com.pz.ffc.newffc.dao.RFDAO;

import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;

public class RFAgingFeatureServiceModule extends AbstractModule{
	@Provides
	RFAgingFeatureDAO dao(final Connection connection) {
		return new RFAgingFeatureDAO(connection);
	}

}
