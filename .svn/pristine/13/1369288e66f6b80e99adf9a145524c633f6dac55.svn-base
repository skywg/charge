package com.iycharge.server.domain.entity.charger;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.BaseEntity.Summary;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.dict.CategoryConstant;

/**
 *充电枪
 */
@Entity
@Table(name="charger_gun")
public class ChargerGun extends BaseEntity{
	/*
	 * 枪号
	 */
	@JsonView(Summary.class)
	private String gunNo;
	
	/*
	 *  枪名称
	 */
	@JsonView(Summary.class)
	private String gunName;
	
	/*
	 * 接口类型
	 */
	@JsonView(Summary.class)
	private String chargeIf;
	
	/*
     * 电压
     */
    @JsonView(Summary.class)
    private String voltage;
    
    /*
     * 电流
     */
    @JsonView(Summary.class)
    private String electricity;
    
    /*
     * 功率
     */
    @JsonView(Summary.class)
    private String power;
    /*
     * 备注
     */
    @JsonView(Summary.class)
    private String bak;
    
    @JsonIgnore
    @ManyToOne(cascade={CascadeType.REFRESH,CascadeType.MERGE})
    private Charger charger;
    /*
     * 充电枪状态
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    private ChargerStatus status;
    
	public String getGunNo() {
		return gunNo;
	}

	public void setGunNo(String gunNo) {
		this.gunNo = gunNo;
	}

	public String getGunName() {
		return gunName;
	}

	public void setGunName(String gunName) {
		this.gunName = gunName;
	}

	public String getChargeIf() {
		return chargeIf;
	}

	public void setChargeIf(String chargeIf) {
		this.chargeIf = chargeIf;
	}

	public String getVoltage() {
		return voltage;
	}

	public void setVoltage(String voltage) {
		this.voltage = voltage;
	}

	public String getElectricity() {
		return electricity;
	}

	public void setElectricity(String electricity) {
		this.electricity = electricity;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public Charger getCharger() {
		return charger;
	}

	public void setCharger(Charger charger) {
		this.charger = charger;
	}

	public String getBak() {
		return bak;
	}

	public void setBak(String bak) {
		this.bak = bak;
	}

	public ChargerStatus getStatus() {
		return status;
	}

	public void setStatus(ChargerStatus status) {
		this.status = status;
	}
	
	@JsonView(Summary.class)
    public String getCStatus(){
        return this.getStatus() == null ? ChargerStatus.OFFLINE.getTitle() : this.getStatus().getTitle();
    }
	@JsonView(Summary.class)
	public String getCif() {
		return EntityUtil.getDictTile(CategoryConstant.CONNECTOR_TYPE, this.getChargeIf());
	}
    
}
