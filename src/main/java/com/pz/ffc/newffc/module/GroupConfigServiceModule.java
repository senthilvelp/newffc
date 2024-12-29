package com.pz.ffc.newffc.module;

import java.sql.Connection;

import com.pz.ffc.newffc.dao.GroupConfigDAO;

import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;

public class GroupConfigServiceModule extends AbstractModule{
	@Provides
	GroupConfigDAO dao(final Connection connection) {
		return new GroupConfigDAO(connection);
	}

}
