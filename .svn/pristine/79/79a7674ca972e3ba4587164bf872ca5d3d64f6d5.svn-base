package com.iycharge.server.domain.entity.event;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.iycharge.server.domain.entity.charger.Charger;

public class EventQueryParam {
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
	private EventStatus eventStatus;
    private EventCode eventCode;
    public EventCode getEventCode() {
		return eventCode;
	}
	public void setEventCode(EventCode eventCode) {
		this.eventCode = eventCode;
	}
    /**
     * 充电桩名称
     */
    private Charger charger;
    /**
     * 充电站名称
     */
    private String station;
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
	
	public Charger getCharger() {
		return charger;
	}
	public void setCharger(Charger charger) {
		this.charger = charger;
	}
	public String getStation() {
		return station;
	}
	public void setStation(String station) {
		this.station = station;
	}
	public EventStatus getEventStatus() {
		return eventStatus;
	}
	public void setEventStatus(EventStatus eventStatus) {
		this.eventStatus = eventStatus;
	}
    
}
