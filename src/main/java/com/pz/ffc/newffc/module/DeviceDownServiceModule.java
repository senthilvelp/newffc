package com.pz.ffc.newffc.module;

import java.sql.Connection;

import com.pz.ffc.newffc.dao.DeviceDAO;
import com.pz.ffc.newffc.dao.DeviceDownDAO;

import io.activej.inject.annotation.Provides;
import io.activej.inject.module.AbstractModule;

public class DeviceDownServiceModule extends AbstractModule{
	@Provides
	DeviceDownDAO dao(final Connection connection) {
		return new DeviceDownDAO(connection);
	}

}
