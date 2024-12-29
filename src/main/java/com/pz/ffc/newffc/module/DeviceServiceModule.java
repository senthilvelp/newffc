package com.pz.ffc.newffc.module;

import java.sql.Connection;

import com.pz.ffc.newffc.dao.DeviceDAO;

import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;

public class DeviceServiceModule extends AbstractModule{
	@Provides
	DeviceDAO dao(final Connection connection) {
		return new DeviceDAO(connection);
	}

}
