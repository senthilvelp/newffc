package com.pz.ffc.newffc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Comparator;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import io.activej.inject.Injector;
import io.activej.inject.module.ModuleBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileProcess implements Runnable{
	
	private static QLPLIncomingDAO qlplIncomgDAO;
	
	
	public FileProcess() {
		Injector injector = Injector.of(
				ModuleBuilder.create()
					.scan(SqlServerModule.create())
					.scan(new QLPLServiceModule())
					.build()
			);
		qlplIncomgDAO = injector.getInstance(QLPLIncomingDAO.class);
	}

	@Override
	public void run() {
		log.info("Calling run");
		while(true) {
			try {
				final File folder = new File(Globals.FILE_PATH);
				final File[] files = folder.listFiles();
				final Comparator<File> fileComparator = Comparator.comparing(File::lastModified);
				Arrays.sort(files, fileComparator);
				for(int i=0; i<files.length;i++) {
					final File file = files[i];
					final String line = getMessageFromFile(file);
					saveMessage(line);
					file.delete();
				}
				log.debug("Will wait for a second");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				log.error(e.getLocalizedMessage());
			} catch (Exception ex) {
				log.error(ex.getLocalizedMessage());
			}
			
		} // while(true) {
		
	}
	
	public static String getMessageFromFile(final File file) throws Exception {
		log.info("Processing File : " + file.getName());
		final BufferedReader rdr = new BufferedReader(new FileReader(file));
		String temp = "";
		String message = "";
		while((temp = rdr.readLine()) != null)
			message = temp;
		rdr.close();
		log.info(message);
		return message;
	}
	
	public static void saveMessage(final String line) throws Exception  {
		qlplIncomgDAO.addQLPL(line);
		
	}
	


}
