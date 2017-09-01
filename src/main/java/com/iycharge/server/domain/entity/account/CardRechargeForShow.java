package com.iycharge.server.domain.entity.account;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class CardRechargeForShow {
	//流水号
	private String tradeNo;
	
	private String chargerTime;

	private String cardNo;
	
	private String type;
	
	private String money;

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}


	public String getChargerTime() {
		return chargerTime;
	}

	public void setChargerTime(String chargerTime) {
		this.chargerTime = chargerTime;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	
}
