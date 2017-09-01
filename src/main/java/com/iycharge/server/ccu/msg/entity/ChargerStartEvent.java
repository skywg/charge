package com.iycharge.server.ccu.msg.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 充电桩启动事件
 *
 * @author bwang
 */
@Entity(name = "msg_charger_start_event")
public class ChargerStartEvent extends BaseEntity {

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
     * 充电认证方式
     */
    @Column(name = "auth_type")
    public byte authType;

    /*
     * 流水号
     */
    @Column(name = "order_id")
    public String orderId;

    /*
     * 事件发生时间
     */
    @Column(name = "start_time")
    public Date startTime;

    public ChargerStartEvent() {

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "ChargerStartEvent [chargerNo=" + chargerNo + ", ifName=" + ifName + ", authType=" + authType
                + ", orderId=" + orderId + ", startTime=" + startTime + "]";
    }
}