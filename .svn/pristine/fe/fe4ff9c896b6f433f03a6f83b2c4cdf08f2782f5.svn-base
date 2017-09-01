package com.iycharge.server.ccu.msg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BMS终止充电报文
 *
 * @author bwang
 */
@Entity
@Table(name="msg_bms_stop_charging")
public class BmsStopChargingMsg extends BaseEntity {

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
     * BMS 中止充电报文
     */
    @Column(name = "stop_msg")
    public String stopMsg;

    /*
     * BMS 中止充电故障原因
     */
    @Column(name = "stop_fault_msg")
    public String stopFaultMsg;

    /*
     * BMS 中止充电错误原因
     */
    @Column(name = "stop_error_msg")
    public String stopErrorMsg;

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

    public String getStopMsg() {
        return stopMsg;
    }

    public void setStopMsg(String stopMsg) {
        this.stopMsg = stopMsg;
    }

    public String getStopFaultMsg() {
        return stopFaultMsg;
    }

    public void setStopFaultMsg(String stopFaultMsg) {
        this.stopFaultMsg = stopFaultMsg;
    }

    public String getStopErrorMsg() {
        return stopErrorMsg;
    }

    public void setStopErrorMsg(String stopErrorMsg) {
        this.stopErrorMsg = stopErrorMsg;
    }

    @Override
    public String toString() {
        return "BmsStopChargingMsg [chargerNo=" + chargerNo + ", ifName=" + ifName + ", stopMsg=" + stopMsg
                + ", stopFaultMsg=" + stopFaultMsg + ", stopErrorMsg=" + stopErrorMsg + "]";
    }

}