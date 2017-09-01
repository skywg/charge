package com.iycharge.server.admin.cache;

import java.io.Serializable;
import java.util.Date;

import com.iycharge.server.domain.entity.charger.ChargerStatus;

/**
 * redis充电接口的数据结构
 * 
 * @author bwang
 */
public class RConnector implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3569214186058496027L;

    /**
     * 编号，保留字段，后续会使用
     */
    private String code;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口类型(DC ： 直流 ， AC ： 交流)
     */
    private String type;

    /**
     * 接口工作状态
     */
    private ChargerStatus status;

    /**
     * 车辆连接状态, true : 已连接 false : 未连接
     */
    private boolean carConnStatus;

    /**
     * 充电开始时间
     */
    private Date chargingStartTime;

    /**
     * 充电结束时间
     */
    private Date charginEndTime;

    /**
     * 实时输出电压
     */
    private int outputVoltage;

    /**
     * 实时输出电流，行业规定，充电时输出电流为负数，做图标展示时需要装换为正数
     */
    private int outputCurrent;

    /**
     * 充电电量
     */
    private double electricity;

    /**
     * 充电时间(单位：秒)
     */
    private long chargingTime;

    /**
     * 充电进度
     */
    private int socVal;

    /**
     * 估算剩余充电时间(单位：分钟)
     */
    private int remainedChargingTime;

    /**
     * 故障码
     */
    private int eventCode;

    /**
     * 故障描述
     */
    private String eventDesc;

    /**
     * 故障发生时间
     */
    private Date eventTime;

    /**
     * 离线时间
     */
    private Date offlineTime;
    
    /**
     * 关联订单号
     */
    private String orderId;
    
    public RConnector() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ChargerStatus getStatus() {
        return status;
    }

    public void setStatus(ChargerStatus status) {
        this.status = status;
    }

    public boolean isCarConnStatus() {
        return carConnStatus;
    }

    public void setCarConnStatus(boolean carConnStatus) {
        this.carConnStatus = carConnStatus;
    }

    public Date getChargingStartTime() {
        return chargingStartTime;
    }

    public void setChargingStartTime(Date chargingStartTime) {
        this.chargingStartTime = chargingStartTime;
    }

    public Date getCharginEndTime() {
        return charginEndTime;
    }

    public void setCharginEndTime(Date charginEndTime) {
        this.charginEndTime = charginEndTime;
    }

    public int getOutputVoltage() {
        return outputVoltage;
    }

    public void setOutputVoltage(int outputVoltage) {
        this.outputVoltage = outputVoltage;
    }

    public int getOutputCurrent() {
        return outputCurrent;
    }

    public void setOutputCurrent(int outputCurrent) {
        this.outputCurrent = outputCurrent;
    }

    public double getElectricity() {
        return electricity;
    }

    public void setElectricity(double electricity) {
        this.electricity = electricity;
    }

    public long getChargingTime() {
        return chargingTime;
    }

    public void setChargingTime(long chargingTime) {
        this.chargingTime = chargingTime;
    }

    public int getSocVal() {
        return socVal;
    }

    public void setSocVal(int socVal) {
        this.socVal = socVal;
    }

    public int getRemainedChargingTime() {
        return remainedChargingTime;
    }

    public void setRemainedChargingTime(int remainedChargingTime) {
        this.remainedChargingTime = remainedChargingTime;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public String getEventDesc() {
        return eventDesc;
    }

    public void setEventDesc(String eventDesc) {
        this.eventDesc = eventDesc;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Date getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(Date offlineTime) {
        this.offlineTime = offlineTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RConnector) {
            RConnector conn = (RConnector) obj;
            return this.code.equals(conn.getCode()) && this.type.equals(conn.getType())
                    && this.name.equals(conn.getName());
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return this.code.hashCode();
    }
}
