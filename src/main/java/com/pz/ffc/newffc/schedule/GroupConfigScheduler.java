package com.pz.ffc.newffc.schedule;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pz.ffc.newffc.Globals;
import com.pz.ffc.newffc.dao.GroupConfigDAO;
import com.pz.ffc.newffc.model.GroupConfig;
import com.pz.ffc.newffc.module.GroupConfigServiceModule;
import com.pz.ffc.newffc.module.SqlServerModule;

import io.activej.inject.Injector;
import io.activej.inject.module.ModuleBuilder;


public final class GroupConfigScheduler implements Runnable {

	private static GroupConfigDAO groupConfigDAO;
	
	private static Logger log = LoggerFactory.getLogger(GroupConfigScheduler.class);
	
	public GroupConfigScheduler() {
		Injector injector = Injector.of(
				ModuleBuilder.create()
					.scan(SqlServerModule.create())
					.scan(new GroupConfigServiceModule())
					.build()
					);
		groupConfigDAO = injector.getInstance(GroupConfigDAO.class);
	}
	
	@Override
	public void run() {
		log.debug("GroupConfig Scheduler Run");
		final List<GroupConfig> groupConfigs = groupConfigDAO.getDevicesgetGroupConfigs();
		log.info("GroupConfig Count : " + groupConfigs.size());
		Globals.groupConfigMap = groupConfigs.stream()
							.collect(Collectors.toMap(
										GroupConfig::getId,
										Function.identity()
										)
								 );

	}

	
}
