package com.iycharge.server.domain.entity.account;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.account.Card;

/**
 * 卡充值记录
 * 集成属性说明：createdAt：订单创建时间  updatedAt: 保留字段
 * @author bwang
 */
@Entity
@Table(name = "card_recharge_record")
public class CardRechargeRecord extends BaseEntity {
    
    /**
     * 订单号
     */
    @Column(length=16, unique=true)
    private String tradeNo;
    
    /**
     * 充值金额
     */
    @Column(precision=10, scale=2)
    private BigDecimal money;
    
    /**
     * 充值时间
     */
    private Date tradeTime;

    /**
     * 充值类型
     */
    @Column(precision=10, scale=2)
    private String rechargeType;

    /**
     * 关联卡
     */
    @ManyToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="card_no")
    private Card card;
    
    public CardRechargeRecord() {
        
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
 
}
