package com.iycharge.server.domain.entity.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelResources;
import com.iycharge.server.domain.entity.utils.serializer.JSonDateSerializer;
import com.iycharge.server.domain.entity.utils.serializer.JsonChargerSerializer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 充电记录
 * 继承属性说明
 *      createdAt: 订单创建时间
 *      updatedAt: 订单更新时间
 * @author bwang
 */
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    /*
     * 关联注册会员
     */
    @JsonIgnore
    @ManyToOne(cascade={CascadeType.REFRESH, CascadeType.MERGE})
    private Account account = new Account();
    
    /*
     * 关联卡号
     */
    @JsonIgnore
    @ManyToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="card_no")
    private Card card;
    
	/*
     * 订单交易号
     */
    @JsonView(Summary.class)
    @Column(unique=true, length=16)
    private String orderId;
    
    /*
     * 订单金额
     */
    @JsonView(Summary.class)
    @Column(precision=10, scale=2)
    private BigDecimal money = new BigDecimal(0);

    /*
     * 订单状态
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.UNPAID;

    /*
    * 订单支付方式
    */
    @JsonView(Summary.class)
    private String paidFrom;
    
    /*
     * 订单电桩ID
     */
    @JsonView(Summary.class)
    @JsonSerialize(using = JsonChargerSerializer.class)
    @ManyToOne(cascade=CascadeType.REFRESH)
    private Charger charger = new Charger();
    
    /*
     * 充电接口名称
     */
    private String ifName;
    
    /*
     * 充电接口类型
     */
    private String ifType;

    /*
     * 充电开始时间
     */
    @JsonView(Summary.class)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonSerialize(using = JSonDateSerializer.class)
    private Date startAt;

    @Transient
    private String formstartAt;

    /*
     * 充电结束时间
     */
    @JsonView(Summary.class) 
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonSerialize(using = JSonDateSerializer.class)
    private Date endAt;
    
    @Transient
    private String cardNoOrPhoneNum;

    @Transient
    private String formendAt;
    /*
     * 开始充电soc
     */
    private int startSoc;
    
    /*
     * 结束充电soc
     */
    private int endSoc;
    
    /*
     * 充电时长，单位（秒）
     */
    @JsonView(Summary.class)
    private long chargeTime;
       
	/*
     * 充电电量
     */
    @JsonView(Summary.class)
    @Column(precision=12, scale=2)
    private BigDecimal degree = new BigDecimal(0);
    
    /*
     * 本次充电后卡（钱包）内的余额
     */
    @JsonView(Summary.class)
    @Column(precision=12, scale=2)
    private BigDecimal balance = new BigDecimal(0);
    
    /*
     * 分段计费明细
     */
    @JsonView(Detail.class)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();
   
    @Transient
    private String checkboxstationname;
    
    public Order(){
    	
    }
    
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }
    @ExcelResources(title="订单流水号",order=0)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    @ExcelResources(title="金额/元",order=10)
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

	public Charger getCharger() {
        return charger;
    }

    public void setCharger(Charger charger) {
        this.charger = charger;
    }
    public String getIfName() {
        if(ifName == null || "".equals(ifName)) {
            return "";
        }
        return ifName.equals("10") ? "A枪" : "B枪";
    }

    public void setIfName(String ifName) {
        this.ifName = ifName;
    }

    public String getIfType() {
        return ifType;
    }

    public void setIfType(String ifType) {
        this.ifType = ifType;
    }

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

    public int getStartSoc() {
        return startSoc;
    }

    public void setStartSoc(int startSoc) {
        this.startSoc = startSoc;
    }

    public int getEndSoc() {
        return endSoc;
    }

    public void setEndSoc(int endSoc) {
        this.endSoc = endSoc;
    }

    public long getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(long chargeTime) {
        this.chargeTime = chargeTime;
    }
    @ExcelResources(title="电量/Kwh",order=9)
    public BigDecimal getDegree() {
        return degree;
    }

    public void setDegree(BigDecimal degree) {
        this.degree = degree;
    }
    @ExcelResources(title="卡余额/元",order=11)
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    public String formatChargingTime() {
        String format = "";
        String h = (int)(this.chargeTime / 60 / 60)+"";
        String m = (int)((this.chargeTime - Integer.parseInt(h) * 60 * 60) / 60)+"";
        String s = (int)(this.chargeTime % 60)+"";
        
        if(Integer.parseInt(h) >= 0) {
        	if(h.trim().length()<2){
        		h="0"+h;
        	}
            format += h + ":";
        }
        
        if(Integer.parseInt(m) >= 0) {
        	if(m.trim().length()<2){
        		m="0"+m;
        	}
            format += m + ":";
        }
        
        if(Integer.parseInt(s) >= 0) {
        	if(s.trim().length()<2){
        		s="0"+s;
        	}
            format += s;
        }
        
        return format;
    }
    
    @JsonView(Summary.class)
	public String getSstatus() {
		return this.getStatus().getTitle();
	}

	@ExcelResources(title="订单时间",order=1)
    public Date getCreatOrderTime(){
    	return super.getCreatedAt();
    }
	
	public String getChargerName(){
		return this.charger.getName();
	}
	@ExcelResources(title="充电桩/接口",order=3)
	public String getChargerNameAndIfName(){
		if(this.charger==null){
			return "未知";
		}
		return this.charger.getName()!=null?this.charger.getName():"未知"+"/"+this.getIfName()!=null?this.getIfName():"未知";
	}
	
	@ExcelResources(title="充电站名称",order=2)
	public String getStationName(){
		if(this.charger!=null&&this.charger.getStation()!=null){
			return this.charger.getStation().getName();
		}
		return "";
	}
	@ExcelResources(title="会员名称",order=4)
	public String getAccountName(){
		if(this.account==null){
			if(this.getCard()!=null&&this.getCard().getAccount()!=null){
				return this.getCard().getAccount().getRealName();
			}
			return "";
		}
		return this.account.getRealName();
	}
	@ExcelResources(title="支付方式",order=5)
	public String getPaidType(){
		return EntityUtil.getDictTile(CategoryConstant.PAID_FROM, this.getPaidFrom());
	}
	@ExcelResources(title="卡号/手机号",order=6)
	public String getCardNoOrPhone(){
		if("CARD".equals(this.paidFrom)){
			return this.card.getCardNo();
		}
		return this.account.getPhone();
	}
	@ExcelResources(title="时长",order=8)
	public String getChtime(){
		return formatChargingTime();
	}
	@ExcelResources(title="状态",order=12)
	public String getst(){
		return this.status.getTitle();
	}
	@ExcelResources(title="起止SOC/%",order=7)
	public String getsoc(){
		return this.getStartSoc()+"~"+this.getEndSoc();
	}
	
	public String getFormstartAt() {
		return formstartAt;
	}

	public void setFormstartAt(String formstartAt) {
		this.formstartAt = formstartAt;
	}

	public String getFormendAt() {
		return formendAt;
	}

	public void setFormendAt(String formendAt) {
		this.formendAt = formendAt;
	}
	

	public String getCardNoOrPhoneNum() {
		return cardNoOrPhoneNum;
	}

	public void setCardNoOrPhoneNum(String cardNoOrPhoneNum) {
		this.cardNoOrPhoneNum = cardNoOrPhoneNum;
	}

	@JsonView(Summary.class)
	public String getSCharginTime() {
	    String hour = (int)(this.chargeTime / 60 / 60)+"";
	    if(hour.length()<2){
	    	hour = "0"+hour;
	    }
	    String min  = (int)(this.chargeTime / 60 % 60)+"";	 
	    if(min.length()<2){
	    	min = "0"+min;
	    }
	    String seconds = (int)(this.chargeTime % 60)+"";
	    if(seconds.length()<2){
	    	seconds = "0"+seconds;
	    }
	    return hour + ":" + min + ":"+seconds;
	}

	public String getCheckboxstationname() {
		return checkboxstationname;
	}

	public void setCheckboxstationname(String checkboxstationname) {
		this.checkboxstationname = checkboxstationname;
	}
	
}

