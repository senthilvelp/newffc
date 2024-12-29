package com.pz.ffc.newffc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pz.ffc.newffc.file.FileProcess;
import com.pz.ffc.newffc.function.DeviceIdFunction;
import com.pz.ffc.newffc.module.SqlServerModule;
import com.pz.ffc.newffc.schedule.CompanyScheduler;
import com.pz.ffc.newffc.schedule.DeviceScheduler;
import com.pz.ffc.newffc.schedule.GroupConfigScheduler;

import io.activej.bytebuf.ByteBufStrings;
import io.activej.csp.ChannelSupplier;
import io.activej.csp.binary.BinaryChannelSupplier;
import io.activej.csp.binary.ByteBufsDecoder;
import io.activej.eventloop.Eventloop;
import io.activej.net.SimpleServer;
import io.activej.net.socket.tcp.AsyncTcpSocket;


public class Main  {
	
	private static Logger log = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args) throws Exception {
		
		final Eventloop eventloop = Eventloop.create().withCurrentThread();
		
		if (args.length != 2) {
			System.out.println("java -jar app.jar <port> <File Folder>");
			System.exit(1);
		}
	
		final int port = Integer.parseInt(args[0]);
		
		System.out.println(port);
		
		final File folder = new File(args[1]);
		if (!folder.isDirectory()) {
			System.out.println("The folder is not a directory");
			log.error("The folder is not a directory");
			System.exit(1);
		}
		Globals.FILE_PATH = args[1];
		
		final Main main = new Main();
		main.setProperties();
			
		final ByteBufsDecoder<String> qlplDecoder = buf -> {	
			final String message =  buf.takeRemaining().asString(StandardCharsets.UTF_8);
			if ((message.charAt(0) != '$')) {
				throw new RuntimeException("Invalid message");
			}
			if (message.length() < 41) {
				throw new RuntimeException("Invalid Message");
			}
			return message;
		};
		
		scheduleCache();
		
		final Runnable fileProcessRunnable = new FileProcess();
		final Thread fileProcessThread = new Thread(fileProcessRunnable);
		fileProcessThread.start();
		
		
		final MainFrame mainFrame = MainFrame.create();
		mainFrame.setVisible(true);
		mainFrame.setSize(800, 600);
		mainFrame.setTitle("SureCount Communication Server" );
		
		final SimpleServer tcpServer = SimpleServer.create(socket -> 
			BinaryChannelSupplier.of(ChannelSupplier.ofSocket(socket))
				.decode(qlplDecoder)
				.whenResult(buf -> processMessage(socket, buf))
				.whenException(ex -> socket.write(ByteBufStrings.wrapUtf8(ex.getLocalizedMessage())))	
				.whenComplete(socket::close)
		)
		.withListenPort(port);
		
		tcpServer.listen();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				log.debug("Closing the Server");
				if (tcpServer.isRunning()) {
					tcpServer.close();
				}
				final SqlServerModule sqlServerModule = SqlServerModule.create();
				log.debug("Closing the SQLServer Connection");
				try {
					try {
						sqlServerModule.close();
					} catch (IOException e) {
						throw new RuntimeException(e.getMessage());
					}
				} catch (SQLException e) {
					log.debug(e.getLocalizedMessage());
				}
			}
		});
		
		eventloop.run();
		
	}
	
	public void setProperties() {
		log.debug("Calling setProperties");
		final Properties properties = new Properties();
		
		final InputStream stream = this.getClass().getResourceAsStream("/settings.conf");
		System.out.println(stream.toString());
		try {
			properties.load(stream);
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
			throw new RuntimeException(e.getLocalizedMessage());
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new RuntimeException(e.getLocalizedMessage());
		}
		
		final String agingDaysStr = properties.getProperty("ffc_aging_days");
		Globals.agingDays = Integer.parseInt(agingDaysStr == null ? "30" : agingDaysStr);
		
		final String schedulerFrequencyStr = properties.getProperty("cache_frequency");
		Globals.schedulerFrequency = Integer.parseInt(schedulerFrequencyStr == null ? "120" : schedulerFrequencyStr);
		
		final String deviceOffMinutesStr = properties.getProperty("evice_off_minutes");
		Globals.deviceOffMinutes = Integer.parseInt(deviceOffMinutesStr == null ? "15" : deviceOffMinutesStr);
		
		final String bataAgingDaysStr = properties.getProperty("bata_agin_days");
		Globals.bataAgingDays = Integer.parseInt(bataAgingDaysStr == null ? "15" : bataAgingDaysStr);
	}
	
	
	public static void processMessage(final AsyncTcpSocket socket, final String message) {
		log.debug("Received Message : " + message);
		socket.write(ByteBufStrings.wrapUtf8("$002@@"));
		saveMessage(message);
	}
	
	public static void saveMessage(final String message) {
		
		final DeviceIdFunction deviceidFn = new DeviceIdFunction();
		final String deviceid = deviceidFn.apply(message);
		
		final String filePrefix = System.nanoTime() + "";
		final String filename = Globals.FILE_PATH + FileSystems.getDefault().getSeparator() + deviceid + "_" + filePrefix + ".txt";
		try {
			final FileOutputStream fos = new FileOutputStream(filename);
			fos.write(message.getBytes());
			fos.flush();
			fos.close();
		} catch(Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
	}
	
	public static void scheduleCache() {
		log.debug("Calling scheduled Cache");
		final int threads = 3;
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(threads);

		final Map<String, Runnable> runnableMap = createRunnableMap();
		
		runnableMap.entrySet().parallelStream().forEach(e -> {
			log.info("Scheduling " + e.getKey());
			scheduler.scheduleWithFixedDelay(e.getValue(), 30, Globals.schedulerFrequency, TimeUnit.SECONDS);
		});
	}
	
	private static <K,V> Map<String, Runnable> createRunnableMap() {
		
		log.debug("Calling createRunnableMap");
		
		final Map<String, Runnable> runnableMap = new HashMap<>();
		runnableMap.put("device", new DeviceScheduler());
		runnableMap.put("company", new CompanyScheduler());
		runnableMap.put("groupconfig", new GroupConfigScheduler());
		
		return runnableMap;
	}
	
}
