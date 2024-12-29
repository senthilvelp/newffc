package com.pz.ffc.newffc.model;

import java.time.LocalDateTime;

public final class Company {

	private long id;
	private String name;
	private String description;
	private Long userid;
	private LocalDateTime timestamping;
	private String clienttype;
	private String emailrequired;
	private String emailid;
	private Integer emailscheduletime;
	private Boolean emailcurrentdate;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public LocalDateTime getTimestamping() {
		return timestamping;
	}
	public void setTimestamping(LocalDateTime timestamping) {
		this.timestamping = timestamping;
	}
	public String getClienttype() {
		return clienttype;
	}
	public void setClienttype(String clienttype) {
		this.clienttype = clienttype;
	}
	public String getEemailrequired() {
		return emailrequired;
	}
	public void setEmailrequired(String emailrequired) {
		this.emailrequired = emailrequired;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	
	public Integer getEmailscheduletime() {
		return emailscheduletime;
	}
	public void setEmailscheduletime(Integer emailscheduletime) {
		this.emailscheduletime = emailscheduletime;
	}
	public String getEmailrequired() {
		return emailrequired;
	}
	public Boolean getEmailcurrentdate() {
		return emailcurrentdate;
	}
	public void setEmailcurrentdate(Boolean emailcurrentdate) {
		this.emailcurrentdate = emailcurrentdate;
	}
	
	
}
