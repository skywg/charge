package com.iycharge.server.ccu.msg.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 电桩停机事件
 *
 * @author bwang
 */
@Entity(name = "msg_charger_stop_event")
public class ChargerStopEvent extends BaseEntity {

    /*
     * 充电桩编号
     */
    @Column(name = "charger_no", length=16)
    public String chargerNo;

    /*
     * 充电接口
     */
    @Column(name = "if_name", length=16)
    public String ifName;

    /*
     * 充电认证方式
     */
    @Column(name = "auth_type")
    public byte authType;

    /*
     * 流水号
     */
    @Column(name = "order_id", length=16)
    public String orderId;

    /*
     * 事件发生时间
     */
    @Column(name = "stop_time")
    public Date stopTime;

    /*
     * 停机原因
     */
    @Column(name = "stop_cause")
    public short stopCause;

    public ChargerStopEvent() {

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

    public byte getAuthType() {
        return authType;
    }

    public void setAuthType(byte authType) {
        this.authType = authType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getStopTime() {
        return stopTime;
    }

    public void setStopTime(Date stopTime) {
        this.stopTime = stopTime;
    }

    public short getStopCause() {
        return stopCause;
    }

    public void setStopCause(short stopCause) {
        this.stopCause = stopCause;
    }

    @Override
    public String toString() {
        return "ChargerStopEvent [chargerNo=" + chargerNo + ", ifName=" + ifName + ", authType=" + authType
                + ", orderId=" + orderId + ", stopTime=" + stopTime + ", stopCause=" + stopCause + "]";
    }
}