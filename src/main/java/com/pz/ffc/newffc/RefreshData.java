package com.pz.ffc.newffc;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.pz.ffc.newffc.dao.DeviceDAO;
import com.pz.ffc.newffc.model.Device;
import com.pz.ffc.newffc.module.DeviceServiceModule;
import com.pz.ffc.newffc.module.SqlServerModule;

import io.activej.inject.Injector;
import io.activej.inject.module.ModuleBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RefreshData implements Runnable{

	
	private DeviceDAO deviceDAO;
	
	public RefreshData() {
		
		final Injector injector = Injector.of(
				ModuleBuilder.create()
					.scan(SqlServerModule.create())
					.scan(new DeviceServiceModule())
					.build()
				);
	}
	
	@Override
	public void run() {
		final CompletableFuture<List<Device>> deviceCompletableFuture = new CompletableFuture<>();
		deviceCompletableFuture.supplyAsync(() -> {
			return deviceDAO.getDevices();
		});
		 
	}
	
}
