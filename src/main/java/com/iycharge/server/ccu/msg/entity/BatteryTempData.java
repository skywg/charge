package com.iycharge.server.ccu.msg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 电池温度数据
 * 
 * @author bwang
 */
@Entity
@Table(name = "msg_battery_temp_data")
public class BatteryTempData extends BaseEntity {

    /*
     * 充电桩编号
     */
    @Column(name="charger_no")
    public String chargerNo;

    /*
     * 充电接口
     */
    @Column(name="if_name")
    public String ifName;

    /*
     * 动力蓄电温度
     */
    @Column(name="temp_items", length=2048)
    public String tempItems;

    public BatteryTempData() {

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

    public String getTempItems() {
        return tempItems;
    }

    public void setTempItems(String tempItems) {
        this.tempItems = tempItems;
    }

    @Override
    public String toString() {
        return "BatteryTempData [chargerNo=" + chargerNo + ", ifName=" + ifName + ", tempItems=" + tempItems
                + "]";
    }
}