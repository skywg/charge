package com.iycharge.server.domain.entity.price;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.BaseEntity.Summary;
import com.iycharge.server.domain.entity.charger.Charger;

/**
 * 参数下发结果
 * 
 * 继承属性说明：
 *      createdAt : 首次添加的时间 
 *      updatedAt : 电桩反馈的时间
 * @author bwang
 */
@Entity
@Table(name="param_setting_result")
public class ParamSettingResult extends BaseEntity{
    
    /*
     * 
     */
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
    private ParamSetting paramSetting;
    
    /*
     * 要下发参数的电站
     */
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH}, fetch=FetchType.LAZY)
    private Charger charger;
    
    /*
     * 下发结果
     */
    @Column(nullable = false, columnDefinition="BOOLEAN default 0")
    private boolean result = false;
    
    @JsonView(Summary.class)
    private String delStatus="normal";
    
    @JsonView(Summary.class)
    private String stationName;
    
    @Transient
    private String resultName;
    
    /*
     * 默认构造函数
     */
    public ParamSettingResult() {
        
    }

    public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public ParamSetting getParamSetting() {
        return paramSetting;
    }

    public void setParamSetting(ParamSetting paramSetting) {
        this.paramSetting = paramSetting;
    }

    public Charger getCharger() {
        return charger;
    }

    public void setCharger(Charger charger) {
        this.charger = charger;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}
    
}
