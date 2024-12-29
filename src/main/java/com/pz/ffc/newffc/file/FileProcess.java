package com.pz.ffc.newffc.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pz.ffc.newffc.Globals;
import com.pz.ffc.newffc.MainFrame;
import com.pz.ffc.newffc.dao.DeviceDownDAO;
import com.pz.ffc.newffc.dao.QLPLIncomingDAO;
import com.pz.ffc.newffc.dao.RFAgingDAO;
import com.pz.ffc.newffc.dao.RFAgingFeatureDAO;
import com.pz.ffc.newffc.dao.RFDAO;
import com.pz.ffc.newffc.function.AdultInFunction;
import com.pz.ffc.newffc.function.AdultOutFunction;
import com.pz.ffc.newffc.function.ChildInFunction;
import com.pz.ffc.newffc.function.ChildOutfunction;
import com.pz.ffc.newffc.function.DateTimeFunction;
import com.pz.ffc.newffc.function.DeviceIdFunction;
import com.pz.ffc.newffc.function.GroupInFunction;
import com.pz.ffc.newffc.function.GroupOutFunction;
import com.pz.ffc.newffc.function.PacketCodeFunction;
import com.pz.ffc.newffc.function.StaffInFunction;
import com.pz.ffc.newffc.function.StaffOutFunction;
import com.pz.ffc.newffc.function.TotalInFunction;
import com.pz.ffc.newffc.function.TotalOutfunction;
import com.pz.ffc.newffc.model.Device;
import com.pz.ffc.newffc.model.DeviceDown;
import com.pz.ffc.newffc.model.RF;
import com.pz.ffc.newffc.model.RFAgingFeature;
import com.pz.ffc.newffc.module.DeviceDownServiceModule;
import com.pz.ffc.newffc.module.QLPLServiceModule;
import com.pz.ffc.newffc.module.RFAgingFeatureServiceModule;
import com.pz.ffc.newffc.module.RFServiceModule;
import com.pz.ffc.newffc.module.SqlServerModule;

import io.activej.inject.Injector;
import io.activej.inject.module.ModuleBuilder;
import lombok.extern.slf4j.Slf4j;

public class FileProcess implements Runnable{
	
	private static RFDAO rfDAO;
	private static DeviceDownDAO deviceDownDAO;
	private static RFAgingFeatureDAO rfAgingFeatureDAO;
	private static Logger log = LoggerFactory.getLogger(FileProcess.class);
	
	public FileProcess() {
		Injector injector = Injector.of(
				ModuleBuilder.create()
					.scan(SqlServerModule.create())
					.scan(new RFServiceModule())
					.scan(new DeviceDownServiceModule())
					.scan(new RFAgingFeatureServiceModule())
					.build()
			);
		rfDAO = injector.getInstance(RFDAO.class);
		deviceDownDAO = injector.getInstance(DeviceDownDAO.class);
		rfAgingFeatureDAO = injector.getInstance(RFAgingFeatureDAO.class);
	}

	@Override
	public void run() {
		log.debug("Calling FileProcess run");
		final MainFrame mainFrame = MainFrame.create();
		while(true) {
			try {
				final File folder = new File(Globals.FILE_PATH);
				final File[] files = folder.listFiles();
				final Comparator<File> fileComparator = Comparator.comparing(File::lastModified);
				Arrays.sort(files, fileComparator);
				for(int i=0; i<files.length;i++) {
					final File file = files[i];
					final String line = getMessageFromFile(file);
					mainFrame.addMessage(line);
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
		log.debug("Processing File : " + file.getName());
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
		log.debug(line);
		
		final DeviceIdFunction deviceidFn = new DeviceIdFunction();
		final String deviceid = deviceidFn.apply(line);
		
		final DateTimeFunction dtFn = new DateTimeFunction();
		final LocalDateTime rdatetime = dtFn.apply(line);
		
		final PacketCodeFunction pcFn = new PacketCodeFunction();
		final String frameno = pcFn.apply(line);
		
		final LocalDate rDate = rdatetime.toLocalDate();
		final long days = ChronoUnit.DAYS.between(rDate, rDate);
		
		if (days >= Globals.agingDays) {
			log.error("Very old data " + line);
			final RFAgingFeature rf = convertMessageToAgingFeature(line);
			rfAgingFeatureDAO.add(rf);
			return;
		} 
		
		if (days <= Globals.bataAgingDays){
			final Device device = Globals.deviceMap.get(deviceid);
			if (device != null && device.getCustomername().equals("BATA")) {
				if (checkDaysBeforeForBata(rdatetime.toLocalDate())) {
					log.error("Very old data for Bata" + line);
					final RFAgingFeature rf = convertMessageToAgingFeature(line);
					rfAgingFeatureDAO.add(rf);
					return;
				}
			}
			
		}
		
		final LocalDateTime previousReportTime = rfDAO.getLatestDeviceTime(deviceid);
		
		final DeviceDown deviceDown = createDeviceDown(deviceid, rdatetime, previousReportTime, frameno);
		if (deviceDown != null) {
			deviceDownDAO.add(deviceDown);
		}
		
		final RF rf = convertMessageToRF(line);
		if (line.length() < 64) {
			rfDAO.add(rf);
		} else {
			rfDAO.addFeature(rf);
		}
		
	}
	
	private static boolean checkDaysBeforeForBata(final LocalDate rdate) {
		log.debug("Calling checkDaysBeforeForBata");
		final LocalDate ldt = LocalDate.now();
		if (rdate.isBefore(ldt)) {
			final long days = ChronoUnit.DAYS.between(rdate, ldt);
			if (days >= 15) {
				return true;
			} else {
				if (days <= 7) {
					final LocalDate lastWeek = ldt.minusWeeks(1L);
					LocalDate lastWeekFirstDate = lastWeek;
					LocalDate lastWeekEndDate = lastWeek;
					while(lastWeek.getDayOfWeek() == DayOfWeek.MONDAY) {
						lastWeekFirstDate = lastWeek.minusDays(1L);
					}
					
					while(lastWeek.getDayOfWeek() == DayOfWeek.SUNDAY) {
						lastWeekEndDate = lastWeek.plusDays(1L);
					}
					
					if ((rdate.equals(lastWeekFirstDate) || rdate.isAfter(lastWeekFirstDate)) && (rdate.equals(lastWeekEndDate) || rdate.isBefore(lastWeekEndDate))) {
						if (rdate.getDayOfWeek() == DayOfWeek.MONDAY) {
							return false;
						} else {
							return true;
						}
					} else {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private static RFAgingFeature convertMessageToAgingFeature(final String message) {
		log.debug("Calling convertMessageToAgingFeature");
		
		final RFAgingFeature rf = new RFAgingFeature();
		
		final int messageLength = message.length();
		
		final PacketCodeFunction pcFunction = new PacketCodeFunction();
		final String packetcode = pcFunction.apply(message);
		rf.setFrameno(packetcode);
		
		final DeviceIdFunction deviceidFn = new DeviceIdFunction();
		final String deviceid = deviceidFn.apply(message);
		rf.setDeviceid(deviceid);
		
		final Device device = Globals.deviceMap.get(deviceid);
		
		final String devicename = (device == null) ? deviceid : device.getDevicename();
		rf.setDevicename(devicename);
		
		final DateTimeFunction dtFn = new DateTimeFunction();
		final LocalDateTime rdatetime = dtFn.apply(message);
		rf.setRdatetime(rdatetime);
		
		rf.setTimestamping(LocalDateTime.now());
		
		final TotalInFunction inFn = new TotalInFunction();
		final String totalin = inFn.apply(message);
		rf.setIncounter(totalin);
		
		final TotalOutfunction outFn = new TotalOutfunction();
		final String totalout = outFn.apply(message);
		rf.setOutcounter(totalout);
		
		if(messageLength > 63) {
			final AdultInFunction adultInFn = new AdultInFunction();
			final String audltin = adultInFn.apply(message);
			rf.setAdultin(audltin);
			
			final AdultOutFunction adultOutFn = new AdultOutFunction();
			final String audltout = adultOutFn.apply(message);
			rf.setAdultout(audltout);
			
			final ChildInFunction childInFn = new ChildInFunction();
			final String childin = childInFn.apply(message);
			rf.setChildin(childin);
			
			final ChildOutfunction childOutFn = new ChildOutfunction();
			final String childOut = childOutFn.apply(message);
			rf.setChildout(childOut);
			
			final GroupInFunction groupInFn = new GroupInFunction();
			final String groupIn = groupInFn.apply(message);
			rf.setGroupin(groupIn);
			
			final GroupOutFunction groupOutFn = new GroupOutFunction();
			final String groupout = groupOutFn.apply(message);
			rf.setGroupout(groupout);
			
			final StaffInFunction staffInFn = new StaffInFunction();
			final String staffin = staffInFn.apply(message);
			rf.setStaffin(staffin);
			
			final StaffOutFunction staffOutFn = new StaffOutFunction();
			final String staffOut = staffOutFn.apply(message);
			rf.setStaffout(staffOut);
		}
	
		
		return rf;
		
	}
	
	private static RF convertMessageToRF(final String message) {
		log.debug("Calling convertMessageToRF");
		
		final RF rf = new RF();
		
		final int messageLength = message.length();
		
		final PacketCodeFunction pcFunction = new PacketCodeFunction();
		final String packetcode = pcFunction.apply(message);
		rf.setFrameno(packetcode);
		
		final DeviceIdFunction deviceidFn = new DeviceIdFunction();
		final String deviceid = deviceidFn.apply(message);
		rf.setDeviceid(deviceid);
		
		final Device device = Globals.deviceMap.get(deviceid);
		
		final String devicename = (device == null) ? deviceid : device.getDevicename();
		rf.setDevicename(devicename);
		
		final DateTimeFunction dtFn = new DateTimeFunction();
		final LocalDateTime rdatetime = dtFn.apply(message);
		rf.setRdatetime(rdatetime);
		
		rf.setTimestamping(LocalDateTime.now());
		
		final TotalInFunction inFn = new TotalInFunction();
		final String totalin = inFn.apply(message);
		rf.setIncounter(totalin);
		
		final TotalOutfunction outFn = new TotalOutfunction();
		final String totalout = outFn.apply(message);
		rf.setOutcounter(totalout);
		
		if(messageLength > 63) {
			final AdultInFunction adultInFn = new AdultInFunction();
			final String audltin = adultInFn.apply(message);
			rf.setAdultin(audltin);
			
			final AdultOutFunction adultOutFn = new AdultOutFunction();
			final String audltout = adultOutFn.apply(message);
			rf.setAdultout(audltout);
			
			final ChildInFunction childInFn = new ChildInFunction();
			final String childin = childInFn.apply(message);
			rf.setChildin(childin);
			
			final ChildOutfunction childOutFn = new ChildOutfunction();
			final String childOut = childOutFn.apply(message);
			rf.setChildout(childOut);
			
			final GroupInFunction groupInFn = new GroupInFunction();
			final String groupIn = groupInFn.apply(message);
			rf.setGroupin(groupIn);
			
			final GroupOutFunction groupOutFn = new GroupOutFunction();
			final String groupout = groupOutFn.apply(message);
			rf.setGroupout(groupout);
			
			final StaffInFunction staffInFn = new StaffInFunction();
			final String staffin = staffInFn.apply(message);
			rf.setStaffin(staffin);
			
			final StaffOutFunction staffOutFn = new StaffOutFunction();
			final String staffOut = staffOutFn.apply(message);
			rf.setStaffout(staffOut);
		}
		
		return rf;
	}
	
	public static DeviceDown createDeviceDown(final String deviceid, final LocalDateTime rdatetime, final LocalDateTime previousDateTime, final String frameno) {
		log.debug("Calling createDeviceDown");
		final DeviceDown deviceDown = new DeviceDown();
		final String openTime;
		final String closetime;
		final Device device = Globals.deviceMap.get(deviceid);
		if (device != null && previousDateTime != null) {
			if (isDeviceDown(device, rdatetime, previousDateTime)) {
				deviceDown.setDeviceid(deviceid);
				deviceDown.setRdatetime(rdatetime);
				deviceDown.setTimestamping(LocalDateTime.now());
				deviceDown.setFrameno(frameno);
				deviceDown.setCompany(device.getCustomername());
				deviceDown.setPreviousdatetime(previousDateTime);
				deviceDown.setStorecode(device.getStorecode());
				deviceDown.setStorename(device.getStorename());
			} else {
				return null;
			}
		} else {
			return null;
		}
		return deviceDown;
	}
	
	public static boolean isDeviceDown(final Device device, final LocalDateTime rdatetime, final LocalDateTime previousdatetime) {
		log.debug("Calling isDeviceDown");
		final String storeOpenTime = device.getOpentime();
		final String storeCloseTime = device.getClosetime();
		
		final LocalTime openTime = LocalTime.parse(storeOpenTime);
		final LocalTime closeTime = LocalTime.parse(storeCloseTime);
		
		final LocalTime rTime = LocalTime.from(rdatetime);
		final LocalTime pTime = LocalTime.from(previousdatetime);
		
		if ((rTime.isAfter(openTime) || rTime.equals(openTime)) && (rTime.isBefore(closeTime) || rTime.equals(closeTime))) {
			final long difference = ChronoUnit.MINUTES.between(previousdatetime, rdatetime);
			if (difference > Globals.deviceOffMinutes) {
				return true;
			}
		}
		
		return false;
	}


}
