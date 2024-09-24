package com.pz.ffc.newffc;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.activej.bytebuf.ByteBufStrings;
import io.activej.csp.ChannelSupplier;
import io.activej.csp.binary.BinaryChannelSupplier;
import io.activej.csp.binary.ByteBufsDecoder;
import io.activej.eventloop.Eventloop;
import io.activej.net.SimpleServer;
import io.activej.net.socket.tcp.AsyncTcpSocket;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
	
	private static final ExecutorService executor = Executors.newSingleThreadExecutor();
	
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
			System.out.println("The folder s not a directory");
			System.exit(1);
		}
		Globals.FILE_PATH = args[1];
			
		final ByteBufsDecoder<String> qlplDecoder = buf -> {
			
			final String message =  buf.takeRemaining().asString(StandardCharsets.UTF_8);
			if ((message.charAt(0) != '$')) {
				throw new RuntimeException("Invalid message");
			}
			return message;
		};
		
		new Thread(new FileProcess()).start();
		
		final SimpleServer tcpServer = SimpleServer.create(socket -> 
			BinaryChannelSupplier.of(ChannelSupplier.ofSocket(socket))
				.decode(qlplDecoder)
				.whenResult(buf -> processMessage(socket, buf))
				.whenException(ex -> socket.write(ByteBufStrings.wrapUtf8(ex.getLocalizedMessage())))	
				.whenComplete(socket::close)
		)
		.withListenPort(port);
		
		tcpServer.listen();
		
		eventloop.run();
		
		
	}
	
	public static void processMessage(final AsyncTcpSocket socket, final String message) {
		log.info("Received Message : " + message);
		socket.write(ByteBufStrings.wrapUtf8("$002@@"));
		saveMessage(message);
		
	}
	
	public static void saveMessage(final String message) {
		final String filePrefix = System.nanoTime() + "";
		final String filename = Globals.FILE_PATH + FileSystems.getDefault().getSeparator() + filePrefix + ".txt";
		try {
			final FileOutputStream fos = new FileOutputStream(filename);
			fos.write(message.getBytes());
			fos.flush();
			fos.close();
		} catch(Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
	}
	
}
