package com.iycharge.server.ccu.msg.entity;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 告警事件
 *
 * @author bwang
 */
@Entity
@Table(name="msg_charger_event")
public class ChargerEvent extends BaseEntity{
    
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
    * 故障代码
    */
   @Column(name="event_code")
   public short eventCode;
   
   /*
    * 故障产生时间
    */
   @Column(name="even_tTime")
   public Date eventTime;
   
   public ChargerEvent() {
       
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

    public short getEventCode() {
        return eventCode;
    }

    public void setEventCode(short eventCode) {
        this.eventCode = eventCode;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

}