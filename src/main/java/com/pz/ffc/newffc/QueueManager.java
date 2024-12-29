package com.pz.ffc.newffc;

import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueManager implements Runnable{

	private static ConcurrentLinkedQueue<String> messageQueue;
	private static ConcurrentLinkedQueue<String> logQueue;
	
	private static class SingletonHolder {
		static final QueueManager instance = new QueueManager();
	}
	
	private QueueManager() {
		messageQueue = new ConcurrentLinkedQueue<String>();
	}
	
	public static QueueManager getInstance() {
		return SingletonHolder.instance;
	}
	
	public void addMessage(final String message) {
		messageQueue.add(message);
	}
	
	public String getMessage() {
		return messageQueue.isEmpty() ? null : messageQueue.poll();
	}
	
	public void addLog(final String message) {
		logQueue.add(message);
	}
	
	public String getLog() {
		return logQueue.isEmpty() ? null : logQueue.poll();
	}

	@Override
	public void run() {
		final MainFrame mainFrame = MainFrame.create();
		while(true) {
			if (!messageQueue.isEmpty()) {
				mainFrame.addMessage("");
			}
			
			if (!logQueue.isEmpty()) {
				
			}
		}
		
	}
}
