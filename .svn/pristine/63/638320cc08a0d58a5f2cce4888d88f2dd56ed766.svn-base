package com.iycharge.server.domain.entity.admin;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

/**
 * 系统用户操作日志
 * @author bwang
 */
@Entity
@Table(name="manager_log")
public class ManagerLog extends BaseEntity{
    
    /**
     * 操作人
     */
    @JsonView(Summary.class)
    private String loginName;
    
    /**
     * 操作人真实姓名
     */
    @JsonView(Summary.class)
    private String realName;
    
    /**
     * 操作人Ip
     */
    @JsonView(Summary.class)
    private String ip;
    
    /**
     * 操作日志产生时间
     */
    @JsonView(Summary.class)
    private Date logTime;
    
    /**
     * 日志所属模块
     */
    private String logModule;
    
    /**
     * 日志操作类型
     */
    private String logType;
    
    /**
     * 操作说明
     */
    private String params;
    
    /**
     * 操作状态  0 : 失败   1 ： 成功
     */
    private boolean status = false;
    
    public ManagerLog() {
        
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    

    public String getLogModule() {
		return logModule;
	}

	public void setLogModule(String logModule) {
		this.logModule = logModule;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
