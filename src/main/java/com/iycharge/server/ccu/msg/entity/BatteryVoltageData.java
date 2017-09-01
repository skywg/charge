package com.iycharge.server.ccu.msg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 电池电压数据
 *
 * @author bwang
 */
@Entity
@Table(name = "msg_battery_voltage_data")
public class BatteryVoltageData extends BaseEntity {

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
     * 单体动力力蓄电池电压
     */
    @Column(name = "voltage_items", length=2048)
    public String voltageItems;

    public BatteryVoltageData() {

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

    public String getVoltageItems() {
        return voltageItems;
    }

    public void setVoltageItems(String voltageItems) {
        this.voltageItems = voltageItems;
    }

    @Override
    public String toString() {
        return "BatteryVoltageData [chargerNo=" + chargerNo + ", ifName=" + ifName + ", voltageItems="
                + voltageItems + "]";
    }
}