package com.iycharge.server.domain.entity.event;

public class EventCodeQueryParam {
	/**
	 * 
     * 事件级别
	 */
	private String eventLevel;
	/**
	 * 事件类型
	 */
	private String eventType;
	
	
	public String getEventLevel() {
		return eventLevel;
	}
	public void setEventLevel(String eventLevel) {
		this.eventLevel = eventLevel;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	
	
	
}
