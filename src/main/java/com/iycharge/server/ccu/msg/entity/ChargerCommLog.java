package com.iycharge.server.ccu.msg.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 电桩通信日志
 * @author bwang
 */
@Entity
public class ChargerCommLog {
    
    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * 电桩编号
     */
    private String chargerCode;
    
    /**
     * 电桩接口
     */
    private String chargerConn;
    
    /**
     * 日志类型
     */
    @Enumerated(EnumType.STRING)
    private LogType logType;
    
    /**
     * 操作参数
     */
    private String params;
    
    /**
     * 日志时间
     */
    private Date logTime;
    
    public ChargerCommLog() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChargerCode() {
        return chargerCode;
    }

    public void setChargerCode(String chargerCode) {
        this.chargerCode = chargerCode;
    }

    public String getChargerConn() {
        return chargerConn;
    }

    public void setChargerConn(String chargerConn) {
        this.chargerConn = chargerConn;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }
}