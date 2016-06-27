package com.xtivia.rest;

import com.liferay.portal.kernel.model.User;

public class LiferayUser {
	
	private String emailAddress;
	private String screenName;
	private long id;
	private boolean active;
	private boolean defaultUser;
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getScreenName() {
		return screenName;
	}
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isDefaultUser() {
		return defaultUser;
	}
	public void setDefaultUser(boolean defaultUser) {
		this.defaultUser = defaultUser;
	}
	
	public LiferayUser(User user) {
		this.emailAddress = user.getEmailAddress();
		this.screenName = user.getScreenName();
		this.id = user.getUserId();
		this.active = user.isActive();
		this.defaultUser = user.isDefaultUser();
	}

}
