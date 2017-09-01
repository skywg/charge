package com.iycharge.server.domain.entity.account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.entity.BaseEntity.Private;
import com.iycharge.server.domain.entity.BaseEntity.Summary;
import com.iycharge.server.domain.entity.account.CardRechargeRecord;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelResources;
import com.iycharge.server.domain.entity.utils.serializer.JSonDateSerializer;

/**
 * 充值卡信息
 * 
 * @author bwang
 */
@Entity
@Table(name = "cards")
public class Card {
    /*
     * 物理卡号
     */
    @Id
    @JsonView(Summary.class)
    private String cardNo;

    /*
     * 支付卡号
     */
    @JsonView(Private.class)
    private String payCardNo;
    
    /*
     * 卡类型
     */
    @JsonView(Private.class)
    private String cardType;

    /*
     * 持卡人
     */
    @JsonView(Summary.class)
    private String owner;

    /*
     * 证件类型
     */
    @JsonView(Private.class)
    private String certificateType;

    /*
     * 证件号
     */
    @JsonView(Private.class)
    private String certificateId;

    /*
     * 持卡人联系方式
     */
    @JsonView(Private.class)
    private String phoneNo;

    /*
     * 发卡日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @JsonView(Summary.class)
    private Date sendDate;

    /*
     * 有效日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @JsonView(Summary.class)
    private Date expiredDate;

    /*
     * 卡片余额
     */
    @JsonView(Private.class)
    @Column(precision=10, scale=2)
    private BigDecimal money;

    /*
     * 状态, 有效：1 无效： 0 ，默认值: 有效
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    private CardStatus status = CardStatus.NORMAL;

    /*
     * 状态变更日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @JsonView(Summary.class)
    private Date updatedAt;
    
    /*
     * 备注
     */
    @Column(length = 10000)
    private String remark;
    
    /*
     * 关联的注册会员
     */
    @ManyToOne(cascade=CascadeType.REFRESH)
    private Account account = new Account();

    /*
     * 关联卡充值记录
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="card")
    private List<CardRechargeRecord> cardRechargeRecords = new ArrayList<>();

    /*
     * 关联充电记录
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="card")
    private List<Order> orders = new ArrayList<>();
    
    /*
     * 发卡人  
     */
    @ManyToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="issuer")
    private Manager manager;
    @Transient
    private String flag="2";
    public Card() {

    }
    @ExcelResources(title="物理卡号",order=2)
    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPayCardNo() {
        return payCardNo;
    }

    public void setPayCardNo(String payCardNo) {
        this.payCardNo = payCardNo;
    }
    public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	@ExcelResources(title="持卡人",order=4)
	public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
    public String getCertificateType() {
		return certificateType;
	}

	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	@ExcelResources(title="证件号",order=6)
	public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }
    @ExcelResources(title="联系方式",order=7)
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    @ExcelResources(title="发卡日期",order=8)
    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
    @ExcelResources(title="有效日期",order=9)
    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
    @ExcelResources(title="金额",order=10)
    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<CardRechargeRecord> getCardRechargeRecords() {
        return cardRechargeRecords;
    }

    public void setCardRechargeRecords(List<CardRechargeRecord> cardRechargeRecords) {
        this.cardRechargeRecords = cardRechargeRecords;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }
    @ExcelResources(title="会员名称",order=0)
    public String getAccountName(){
    	return this.account.getNickname();
    }
    @ExcelResources(title="会员号",order=1)
    public String getAccountNo(){
    	return this.account.getPhone();
    }
    @ExcelResources(title="状态",order=11)
    public String getSstatus() {
        return this.status.getTitle();
    }
    @ExcelResources(title="证件类型",order=5)
    public String getCfType() {
		return EntityUtil.getDictTile(CategoryConstant.CERTIFICATE_TYPE, this.certificateType);
	}
    @ExcelResources(title="卡类型",order=3)
    public String getCdType() {
		return EntityUtil.getDictTile(CategoryConstant.CARD_TYPE, this.cardType);
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
    
}
