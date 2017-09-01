package com.iycharge.server.domain.entity.notification;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class NotificationQueryParam {
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date startAt;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date endAt;
	
	private SendType sendType;
	
	private NotificationStatus nstatus;
	
	private String phone;

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public SendType getSendType() {
		return sendType;
	}

	public void setSendType(SendType sendType) {
		this.sendType = sendType;
	}

	public NotificationStatus getNstatus() {
		return nstatus;
	}

	public void setNstatus(NotificationStatus nstatus) {
		this.nstatus = nstatus;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
