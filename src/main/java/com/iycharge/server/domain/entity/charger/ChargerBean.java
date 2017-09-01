package com.iycharge.server.domain.entity.charger;

/**
 * @author zw
 */
public class ChargerBean {
	
	private long id;
    private String code;
    private String name;
    private String chargerType;
    private String stationName;
    private String operatorName;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getChargerType() {
		return chargerType;
	}
	public void setChargerType(String chargerType) {
		this.chargerType = chargerType;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
    

}
