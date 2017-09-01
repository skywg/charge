package com.iycharge.server.ccu.msg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 电池参数信息
 * @author bwang
 */
@Entity
@Table(name="msg_battery_param_data")
public class BatteryParamData extends BaseEntity{

    /*
     * 充电桩编号
     */
    @Column(name = "charger_no")
    public String chargerNo;

    /*
     * 充电接口
     */
    @Column(name = "if_name")
    public String ifName;

    /*
     * 
     * 单体动力电池最高充电电压
     */
    @Column(name = "allowed_max_voltage")
    public short allowedMaxVoltage;

    /*
     * 最高允许充电电流
     */
    @Column(name = "allowed_max_current")
    public short allowedMaxCurrent;

    /*
     * 动力蓄电池标称总能量
     */
    @Column(name = "total_capacity")
    public short totalCapacity;

    /*
     * 最高允许充电总电压
     */
    @Column(name = "allowed_total_voltage")
    public short allowedTotalVoltage;

    /*
     * 最高允许温度
     */
    @Column(name = "allowed_max_temperature")
    public short allowedMaxTemperature;

    /*
     * 整车动力蓄电池荷电状态
     */
    @Column(name = "soc_status")
    public short socStatus;

    /*
     * 整车动力蓄电当前电池电压
     */
    @Column(name = "realtime_voltage")
    public short realtimeVoltage;

    public BatteryParamData() {

    }

    public String getChargerNo() {
        return chargerNo;
    }

    public void setChargerNo(String chargerNo) {
        this.chargerNo = chargerNo;
    }

    public String getIfName() {
        return ifName;
    }

    public void setIfName(String ifName) {
        this.ifName = ifName;
    }

    public short getAllowedMaxVoltage() {
        return allowedMaxVoltage;
    }

    public void setAllowedMaxVoltage(short allowedMaxVoltage) {
        this.allowedMaxVoltage = allowedMaxVoltage;
    }

    public short getAllowedMaxCurrent() {
        return allowedMaxCurrent;
    }

    public void setAllowedMaxCurrent(short allowedMaxCurrent) {
        this.allowedMaxCurrent = allowedMaxCurrent;
    }

    public short getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(short totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public short getAllowedTotalVoltage() {
        return allowedTotalVoltage;
    }

    public void setAllowedTotalVoltage(short allowedTotalVoltage) {
        this.allowedTotalVoltage = allowedTotalVoltage;
    }

    public short getAllowedMaxTemperature() {
        return allowedMaxTemperature;
    }

    public void setAllowedMaxTemperature(short allowedMaxTemperature) {
        this.allowedMaxTemperature = allowedMaxTemperature;
    }

    public short getSocStatus() {
        return socStatus;
    }

    public void setSocStatus(short socStatus) {
        this.socStatus = socStatus;
    }

    public short getRealtimeVoltage() {
        return realtimeVoltage;
    }

    public void setRealtimeVoltage(short realtimeVoltage) {
        this.realtimeVoltage = realtimeVoltage;
    }

    @Override
    public String toString() {
        return "BatteryParamData [chargerNo=" + chargerNo + ", ifName=" + ifName + ", allowedMaxVoltage="
                + allowedMaxVoltage + ", allowedMaxCurrent=" + allowedMaxCurrent + ", totalCapacity=" + totalCapacity
                + ", allowedTotalVoltage=" + allowedTotalVoltage + ", allowedMaxTemperature=" + allowedMaxTemperature
                + ", socStatus=" + socStatus + ", realtimeVoltage=" + realtimeVoltage + "]";
    }
}