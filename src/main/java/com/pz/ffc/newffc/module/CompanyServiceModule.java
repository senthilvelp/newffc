package com.pz.ffc.newffc.module;

import java.sql.Connection;

import com.pz.ffc.newffc.dao.CompanyDAO;

import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;

public class CompanyServiceModule extends AbstractModule{
	@Provides
	CompanyDAO dao(final Connection connection) {
		return new CompanyDAO(connection);
	}

}
