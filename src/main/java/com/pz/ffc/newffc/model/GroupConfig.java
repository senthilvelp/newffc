package com.pz.ffc.newffc.model;

import java.time.LocalDateTime;

public class GroupConfig {
	private long id;
	private long companyid;
	private String deviceid;
	private long groupid;
	private long userid;
	private LocalDateTime timestamping;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(long companyid) {
		this.companyid = companyid;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public long getGroupid() {
		return groupid;
	}
	public void setGroupid(long groupid) {
		this.groupid = groupid;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}
	public LocalDateTime getTimestamping() {
		return timestamping;
	}
	public void setTimestamping(LocalDateTime timestamping) {
		this.timestamping = timestamping;
	}
	
	
	
}
