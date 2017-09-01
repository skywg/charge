package com.iycharge.server.domain.entity.account;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.admin.Manager;

/**
 * 卡充值操作日志
 * @author bwang
 */
@Entity
public class CardRechargeLog extends BaseEntity {
    
    /**
     * 登录用户
     */
    @ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
    @JoinColumn(name="login_name")
    private Manager manager;
    
    /**
     * 划账账户
     */
    @ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;
    
    /**
     * 充值卡号
     */
    private String cardNo;
    
    /**
     * 充值金额
     */
    @Column(precision=10, scale=2)
    private BigDecimal money;
    
    /**
     * 日志时间
     */
    private Date logTime;
    
    /**
     * 取值：批量划账、单卡充值
     */
    private String logType;
    
    /**
     * 备注
     */
    @Column(length=1024)
    private String remark;
     
    /**
     * 操作状态: 0：失败， 1 ：成功
     */
    private boolean status;
    
    public CardRechargeLog() {
        
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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
