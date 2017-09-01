package com.iycharge.server.domain.entity.account;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.admin.Manager;

/**
 * 充电卡状态变更日志记录
 * @author bwang
 */
@Entity
public class CardAuditLog extends BaseEntity{
    
    /**
     * 操作用户
     */
    @ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
    @JoinColumn(name="login_name")
    private Manager manager;
    
    /**
     * 操作卡号
     */
    private String cardNo;
    
    /**
     * 日志时间
     */
    private Date logTime;
    
    /**
     *  目前只有：启用、挂失 和消卡
     */
    private String logType;
    
    /**
     * 操作说明
     */
    private String remark;
    
    /**
     * 操作状态：0：失败   1：成功
     */
    private boolean status;
    
    public CardAuditLog() {
        
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
