package com.iycharge.server.domain.entity.admin;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class AppVersionQueryParam {
	/**
     * 查询起始时间
     */
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    
    /**
     * 查询结束时间
     */
	@DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
	
	private String title;
	
	private String version;

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
