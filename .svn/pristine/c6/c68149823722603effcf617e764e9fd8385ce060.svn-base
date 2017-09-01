package com.iycharge.server.ccu.msg.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 充电记录上传报文
 * @author bwang
 */
@Entity
@Table(name="msg_rechargingRecord")
public class ChargingRecord extends BaseEntity{
    
    /*
     * 电桩编号
     */
    @Column(name="charger_no", length=16)
    private String chargerNo;
    
    /*
     * 充电接口名
     */
    @Column(name="if_name", length=16)
    private String ifName;
    
    /*
     * 充电接口类型
     */
    @Column(name="if_type", length=4)
    private byte ifType;
    
    /*
     * 充电认证方式
     */
    @Column(name="auth_type")
    private byte authType;
    
    /*
     * 卡号/流水号 
     */
    @Column(name="order_id", length=16)
    private String orderId;
    
    /*
     * 结算标记
     */
    @Column(name="pay_flag")
    private boolean payFlag;
    
    /*
     * 开始充电时间
     */
    @Column(name="start_at")
    private Date startAt;
    
    /*
     * 结束充电时间
     */
    @Column(name="end_at")
    private Date endAt;
    
    /*
     * 开始充电 SOC 
     */
    @Column(name="start_soc")
    private byte startSoc;
    
    /*
     * 结束充电 SOC
     */
    @Column(name="end_soc")
    private byte endSoc;
    
    /*
     * 本次充电电量
     */
    @Column(precision=10, scale=2, columnDefinition="decimal default 0")
    private BigDecimal degree;
    
    /*
     * 本次充电时长 
     */
    private int chargeTime;
    
    /*
     * 时段数 
     */
    private byte itemCount;
    
    /*
     * 消费明细
     */
    @Column(length=1024)
    private String  itemDetail;
    
    @Transient
    private BigDecimal payment;
    
    public ChargingRecord() {
        
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

    public byte getIfType() {
        return ifType;
    }

    public void setIfType(byte ifType) {
        this.ifType = ifType;
    }

    public byte getAuthType() {
        return authType;
    }

    public void setAuthType(byte authType) {
        this.authType = authType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public boolean isPayFlag() {
        return payFlag;
    }

    public void setPayFlag(boolean payFlag) {
        this.payFlag = payFlag;
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

    public byte getStartSoc() {
        return startSoc;
    }

    public void setStartSoc(byte startSoc) {
        this.startSoc = startSoc;
    }

    public byte getEndSoc() {
        return endSoc;
    }

    public void setEndSoc(byte endSoc) {
        this.endSoc = endSoc;
    }

    public BigDecimal getDegree() {
        return degree;
    }

    public void setDegree(BigDecimal degree) {
        this.degree = degree;
    }

    public int getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(int chargeTime) {
        this.chargeTime = chargeTime;
    }

    public byte getItemCount() {
        return itemCount;
    }

    public void setItemCount(byte itemCount) {
        this.itemCount = itemCount;
    }

    public String getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(String itemDetail) {
        this.itemDetail = itemDetail;
    }
    
    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }

    public BigDecimal getPayment() {
        BigDecimal payment = new BigDecimal(0);
        JSONArray array = JSONArray.fromObject(getItemDetail());
        if(!array.isEmpty()) {
            for(int i=0; i<array.size(); i++) {
                JSONObject item = array.getJSONObject(i);
                payment = payment.add(new BigDecimal(item.getString("money")));
            }
            
        }
        setPayment(payment);
        return payment;
    }
}
