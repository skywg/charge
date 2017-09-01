package com.iycharge.server.domain.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class IndexBean {
	private String stationId;
	private String stationName;
	private String stationStatus;
	private BigDecimal latitude;
	private BigDecimal longitude;
	private Map<String,Object> map= new HashMap<String,Object>();
	public String getStationId() {
		return stationId;
	}
	public void setStationId(String stationId) {
		this.stationId = stationId;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getStationStatus() {
		return stationStatus;
	}
	public void setStationStatus(String stationStatus) {
		this.stationStatus = stationStatus;
	}
	
	
}
