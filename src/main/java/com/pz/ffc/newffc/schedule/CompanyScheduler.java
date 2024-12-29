package com.pz.ffc.newffc.schedule;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pz.ffc.newffc.Globals;
import com.pz.ffc.newffc.dao.CompanyDAO;
import com.pz.ffc.newffc.model.Company;
import com.pz.ffc.newffc.module.CompanyServiceModule;
import com.pz.ffc.newffc.module.SqlServerModule;

import io.activej.inject.Injector;
import io.activej.inject.module.ModuleBuilder;


public final class CompanyScheduler implements Runnable {
	
	private static Logger log = LoggerFactory.getLogger(CompanyScheduler.class);

	private static CompanyDAO companyDAO;
	
	public CompanyScheduler() {
		Injector injector = Injector.of(
				ModuleBuilder.create()
					.scan(SqlServerModule.create())
					.scan(new CompanyServiceModule())
					.build()
					);
		companyDAO = injector.getInstance(CompanyDAO.class);
	}
	
	@Override
	public void run() {
		log.debug("Company Scheduler Run");
		final List<Company> companies = companyDAO.getCompanies();
		log.info("Company Count : " + companies.size());
		Globals.companyMap = companies.stream()
							.collect(Collectors.toMap(
										Company::getName,
										Function.identity()
										)
								 );

	}

	
}
