package com.iycharge.server.domain.entity.account;

public class OrderForShow {

	//充电流水号
	private String orderId;
	//充电开始时间
	private String startAt;
	//充电站名称
	private String stationName;
	//充电桩名称
	private String chargerName;
	//充电桩接口
	private String chargerIf;
	//充电时长
	private String chargeTime;
	//充电电量
	private String degree;
	//充电金额(元)
	private String money;
	//状态
	private String payStatus;
	//支付方式
	private String payMethod;
	//卡号|手机号
	private String cardNoOrPhoneNum;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStartAt() {
		return startAt;
	}
	public void setStartAt(String startAt) {
		this.startAt = startAt;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getChargerName() {
		return chargerName;
	}
	public void setChargerName(String chargerName) {
		this.chargerName = chargerName;
	}
	public String getChargerIf() {
		return chargerIf;
	}
	public void setChargerIf(String chargerIf) {
		this.chargerIf = chargerIf;
	}
	public String getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(String chargeTime) {
		this.chargeTime = chargeTime;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	
	public OrderForShow(String orderId, String startAt, String stationName, String chargerName, String chargerIf,
			String chargeTime, String degree, String money, String payStatus, String payMethod,String cardNoOrPhoneNum) {
		super();
		this.orderId = orderId;
		this.startAt = startAt;
		this.stationName = stationName;
		this.chargerName = chargerName;
		this.chargerIf = chargerIf;
		this.chargeTime = chargeTime;
		this.degree = degree;
		this.money = money;
		this.payStatus = payStatus;
		this.payMethod = payMethod;
		this.cardNoOrPhoneNum = cardNoOrPhoneNum;
	}
	
	public OrderForShow() {
		super();
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getCardNoOrPhoneNum() {
		return cardNoOrPhoneNum;
	}
	public void setCardNoOrPhoneNum(String cardNoOrPhoneNum) {
		this.cardNoOrPhoneNum = cardNoOrPhoneNum;
	}
	
}
