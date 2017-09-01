package com.iycharge.server.domain.entity.account;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.admin.Manager;

/**
 * 企业用户充值操作日志记录
 * @author bwang
 */
@Entity
public class AccountRechargeLog extends BaseEntity{
    
    /**
     * 登录用户
     */
    @ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
    @JoinColumn(name="login_name")
    private Manager manager;
    
    /**
     * 被操作的会员
     */
    @ManyToOne(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;
    
    /**
     * 日志时间
     */
    private Date logTime;
    
    /**
     * 交易凭证
     */
    private String voucher;
    
    /**
     * 充值方式：
     */
    @Enumerated(EnumType.STRING)
    private AccountRechargeMethod rechargeMethod;
    
    /**
     * 充值金额
     */
    @Column(precision=13, scale=2)
    private BigDecimal money;
    
    /**
     * 备注
     */
    @Column(length=1024)
    private String remark;
    
    /**
     * 操作状态：0：失败   1：成功
     */
    private boolean status;
    
    public AccountRechargeLog() {
        
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

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public AccountRechargeMethod getRechargeMethod() {
        return rechargeMethod;
    }

    public void setRechargeMethod(AccountRechargeMethod rechargeMethod) {
        this.rechargeMethod = rechargeMethod;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
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
