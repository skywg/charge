package com.iycharge.server.ccu.msg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BMS统计数据
 * @author bwang
 */
@Entity
@Table(name="msg_bms_statistic_data")
public class BmsStatisticData extends BaseEntity {

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
     * 终止荷电状态SOC(%)
     */
    @Column(name = "soc_status")
    public byte socStatus;

    /*
     * 动力蓄电池单体最低电压
     */
    @Column(name = "min_voltage")
    public short minVoltage;

    /*
     * 动力蓄电池单体最高电压
     */
    @Column(name = "max_voltage")
    public short maxVoltage;

    /*
     * 动力蓄电池最低温度
     */
    @Column(name = "min_temperature")
    public short minTemperature;

    /*
     * 动力蓄电池最高温度
     */
    @Column(name = "max_temperature")
    public short maxTemperature;

    public BmsStatisticData() {

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

    public byte getSocStatus() {
        return socStatus;
    }

    public void setSocStatus(byte socStatus) {
        this.socStatus = socStatus;
    }

    public short getMinVoltage() {
        return minVoltage;
    }

    public void setMinVoltage(short minVoltage) {
        this.minVoltage = minVoltage;
    }

    public short getMaxVoltage() {
        return maxVoltage;
    }

    public void setMaxVoltage(short maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    public short getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(short minTemperature) {
        this.minTemperature = minTemperature;
    }

    public short getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(short maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    @Override
    public String toString() {
        return "BmsStatisticData [chargerNo=" + chargerNo + ", ifName=" + ifName + ", socStatus=" + socStatus
                + ", minVoltage=" + minVoltage + ", maxVoltage=" + maxVoltage + ", minTemperature=" + minTemperature
                + ", maxTemperature=" + maxTemperature + "]";
    }
}