package com.pz.ffc.newffc.schedule;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pz.ffc.newffc.Globals;
import com.pz.ffc.newffc.dao.DeviceDAO;
import com.pz.ffc.newffc.model.Device;
import com.pz.ffc.newffc.module.DeviceServiceModule;
import com.pz.ffc.newffc.module.SqlServerModule;

import io.activej.inject.Injector;
import io.activej.inject.module.ModuleBuilder;


public final class DeviceScheduler implements Runnable {
	
	private static Logger log = LoggerFactory.getLogger(DeviceScheduler.class);

	private static DeviceDAO deviceDAO;
	
	public DeviceScheduler() {
		Injector injector = Injector.of(
				ModuleBuilder.create()
					.scan(SqlServerModule.create())
					.scan(new DeviceServiceModule())
					.build()
					);
		deviceDAO = injector.getInstance(DeviceDAO.class);
	}
	
	@Override
	public void run() {
		log.debug("Device Scheduler Run");
		final List<Device> devices = deviceDAO.getDevices();
		log.info("Device Count : " + devices.size());
		Globals.deviceMap = devices.stream()
							.collect(Collectors.toMap(
										Device::getDeviceid,
										Function.identity()
										)
								 );

	}

	
}
