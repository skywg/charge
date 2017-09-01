package com.iycharge.server.domain.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.utils.serializer.JSonDateSerializer;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值记录
 *
 * @author bwang
 */
@Entity
@Table(name = "recharge_records")
public class RechargeRecord extends BaseEntity {
    
    /*
     * 商家订单号
     */
    @JsonView(Summary.class)
    @Column(unique=true)    
    private String tradeNo;
    
    /*
     * 充值账户
     */
    @JsonIgnore
    @ManyToOne
    private Account account;

    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startAt;
    
    @Transient
    private String formStartAt;
    
    @Transient
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endAt;
    
    @Transient
    private String formEndAt;
    
    public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public Date getEndAt() {
		return endAt;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}
	/*
     * 充值金额
     */
    @JsonView(Summary.class)
    private BigDecimal money;

    /*
     * 充值状态
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.UNPAID;

   /*
    * 充值渠道
    */
    @JsonView(Summary.class)
    @Column(name = "paid_from")
    private String paidFrom;

    /***************支付宝相关******************/ 
    /*
     * 支付宝交易号
     */
    @JsonView(Private.class)
    private String alipayTradeNumber;
    
    /*
     * 买家支付宝用户号
     */
    @JsonView(Private.class)
    private String alipayBuyerId;
    /*
     * 买家支付宝账号
     */
    @JsonView(Private.class)
    private String alipayBuyerEmail;
    
    /*
     * 交易付款时间
     */
    @JsonView(Summary.class)
    @JsonSerialize(using=JSonDateSerializer.class)
    private Date alipayPaymentTime;
    
    
    /****************微信*************************/
    //付款订单ID
    @JsonView(Private.class)
    private String transactionId;
    //微信付款时间
    @JsonView(Summary.class)
    @JsonSerialize(using=JSonDateSerializer.class)
    private Date weixinalipayPaymentTime;
    
    /**
     * 交易凭证
     */
    @Transient
    private String uri;
    
    public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public RechargeRecord() {
        
    }

	public String getTradeNo() {
        return tradeNo;
    }
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public BigDecimal getMoney() {
        return money;
    }
    public void setMoney(BigDecimal money) {
        this.money = money;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getPaidFrom() {
		return paidFrom;
	}

	public void setPaidFrom(String paidFrom) {
		this.paidFrom = paidFrom;
	}

	public String getAlipayTradeNumber() {
        return alipayTradeNumber;
    }
    public void setAlipayTradeNumber(String alipayTradeNo) {
        this.alipayTradeNumber = alipayTradeNo;
    }
    public String getAlipayBuyerId() {
        return alipayBuyerId;
    }
    public void setAlipayBuyerId(String alipayBuyerId) {
        this.alipayBuyerId = alipayBuyerId;
    }
    public String getAlipayBuyerEmail() {
        return alipayBuyerEmail;
    }
    public void setAlipayBuyerEmail(String alipayBuyerEmail) {
        this.alipayBuyerEmail = alipayBuyerEmail;
    }
    public Date getAlipayPaymentTime() {
        return alipayPaymentTime;
    }
    public void setAlipayPaymentTime(Date alipayPaymentTime) {
        this.alipayPaymentTime = alipayPaymentTime;
    }


	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getWeixinalipayPaymentTime() {
		return weixinalipayPaymentTime;
	}

	public void setWeixinalipayPaymentTime(Date weixinalipayPaymentTime) {
		this.weixinalipayPaymentTime = weixinalipayPaymentTime;
	}

	public String getFormStartAt() {
		return formStartAt;
	}

	public void setFormStartAt(String formStartAt) {
		this.formStartAt = formStartAt;
	}

	public String getFormEndAt() {
		return formEndAt;
	}

	public void setFormEndAt(String formEndAt) {
		this.formEndAt = formEndAt;
	}

	
}

