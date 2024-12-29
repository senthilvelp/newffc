package com.pz.ffc.newffc.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public final class DeviceDown {
	private long id;
	private String deviceid;
	private String company;
	private String storecode;
	private String storename;
	private LocalDateTime rdatetime;
	private LocalDate reportdate;
	private LocalDateTime timestamping;
	private LocalDateTime previousdatetime;
	private String frameno;
	private boolean processed;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getStorecode() {
		return storecode;
	}
	public void setStorecode(String storecode) {
		this.storecode = storecode;
	}
	public String getStorename() {
		return storename;
	}
	public void setStorename(String storename) {
		this.storename = storename;
	}
	public LocalDateTime getRdatetime() {
		return rdatetime;
	}
	public void setRdatetime(LocalDateTime rdatetime) {
		this.rdatetime = rdatetime;
	}
	public LocalDate getReportdate() {
		return reportdate;
	}
	public void setReportdate(LocalDate reportdate) {
		this.reportdate = reportdate;
	}
	public LocalDateTime getTimestamping() {
		return timestamping;
	}
	public void setTimestamping(LocalDateTime timestamping) {
		this.timestamping = timestamping;
	}
	public LocalDateTime getPreviousdatetime() {
		return previousdatetime;
	}
	public void setPreviousdatetime(LocalDateTime previousdatetime) {
		this.previousdatetime = previousdatetime;
	}
	public String getFrameno() {
		return frameno;
	}
	public void setFrameno(String frameno) {
		this.frameno = frameno;
	}
	public boolean isProcessed() {
		return processed;
	}
	public void setProcessed(boolean processed) {
		this.processed = processed;
	}
	
	

}
