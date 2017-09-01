package com.iycharge.server.ccu.msg.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 充电桩充电实时数据
 *
 * @author bwang
 */
@Entity(name = "msg_charging_realtime_data")
public class ChargingRealtimeData extends BaseEntity {

    /*
     * 充电桩编号
     */
    @Column(name = "charge_no", length=16)
    public String chargeNo;

    /*
     * 充电接口
     */
    @Column(name = "if_name", length=16)
    public String ifName;

    /*
     * 电压需求
     */
    @Column(name = "required_voltage")
    public short requiredVoltage;

    /*
     * 电流需求
     */
    @Column(name = "required_current")
    public short requiredCurrent;

    /*
     * 充电模式
     */
    @Column(name = "charging_model")
    public byte chargingModel;

    /*
     * 电池侧充电电压测量值
     */
    @Column(name = "realtime_charging_voltage")
    public short realtimeChargingVoltage;

    /*
     * 电池侧充电电流测量值
     */
    @Column(name = "realtime_charging_current")
    public short realtimeChargingCurrent;

    /*
     * 最高单体动力蓄电池电压及其组号
     */
    @Column(name = "max_voltage")
    public short maxVoltage;

    /*
     * 当前电荷状态 SOC%
     */
    @Column(name = "realtime_soc_status")
    public byte realtimeSocStatus;
    /*
     * 估算剩余充电时间(单位：min )
     */
    @Column(name = "remained_charging_time")
    public short remainedChargingTime;

    /*
     * 充电电量
     */
    @Column(name = "charging_num", precision=8, scale=2)
    public BigDecimal chargingNum;

    /*
     * 充电时长
     */
    @Column(name = "charging_time")
    public int chargingTime;

    /*
     * 最高单体动力蓄电池电压所在编号
     */
    @Column(name = "max_voltage_checkpoint_no")
    public short maxVoltageCheckpointNo;

    /*
     * 最高动力电池温度
     */
    @Column(name = "max_temperature")
    public short maxTemperature;

    /*
     * 最高温度检测点编号
     */
    @Column(name = "max_temperature_checkpoint_no")
    public short maxTemperatureCheckpointNo;

    /*
     * 最低动力蓄电池温度
     */
    @Column(name = "min_temperature")
    public short minTemperature;

    /*
     * 最低动力蓄电池温度检测点编号
     */
    @Column(name = "min_temperature_checkpoint_no")
    public short minTemperatureCheckpointNo;

    /*
     * 单体动力蓄电池电压过高/过低
     */
    @Column(name = "voltage_status")
    public byte voltageStatus;

    /*
     * 整车动力蓄电池荷电状态SOC 过高/过低
     */
    @Column(name = "soc_status")
    public byte socStatus;

    /*
     * 动力蓄电池充电电流过高/过低
     */
    @Column(name = "current_status")
    public byte currentStatus;

    /*
     * 动力蓄电池温度过高/过低
     */
    @Column(name = "temperature_status")
    public byte temperatureStatus;

    /*
     * 动力蓄电池绝缘状态
     */
    @Column(name = "insulation_status")
    public byte insulationStatus;

    /*
     * 动力蓄电池组输出连接器连接状态
     */
    @Column(name = "connector_status")
    public byte connectorStatus;

    /*
     * BMS 是否充电允许
     */
    @Column(name = "is_allowed_charging")
    public byte isAllowedCharging;

    public ChargingRealtimeData() {

    }

    public String getChargeNo() {
        return chargeNo;
    }

    public void setChargeNo(String chargeNo) {
        this.chargeNo = chargeNo;
    }

    public String getIfName() {
        return ifName;
    }

    public void setIfName(String ifName) {
        this.ifName = ifName;
    }

    public short getRequiredVoltage() {
        return requiredVoltage;
    }

    public void setRequiredVoltage(short requiredVoltage) {
        this.requiredVoltage = requiredVoltage;
    }

    public short getRequiredCurrent() {
        return requiredCurrent;
    }

    public void setRequiredCurrent(short requiredCurrent) {
        this.requiredCurrent = requiredCurrent;
    }

    public byte getChargingModel() {
        return chargingModel;
    }

    public void setChargingModel(byte chargingModel) {
        this.chargingModel = chargingModel;
    }

    public short getRealtimeChargingVoltage() {
        return realtimeChargingVoltage;
    }

    public void setRealtimeChargingVoltage(short realtimeChargingVoltage) {
        this.realtimeChargingVoltage = realtimeChargingVoltage;
    }

    public short getRealtimeChargingCurrent() {
        return realtimeChargingCurrent;
    }

    public void setRealtimeChargingCurrent(short realtimeChargingCurrent) {
        this.realtimeChargingCurrent = realtimeChargingCurrent;
    }

    public short getMaxVoltage() {
        return maxVoltage;
    }

    public void setMaxVoltage(short maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    public byte getRealtimeSocStatus() {
        return realtimeSocStatus;
    }

    public void setRealtimeSocStatus(byte realtimeSocStatus) {
        this.realtimeSocStatus = realtimeSocStatus;
    }

    public short getRemainedChargingTime() {
        return remainedChargingTime;
    }

    public void setRemainedChargingTime(short remainedChargingTime) {
        this.remainedChargingTime = remainedChargingTime;
    }

    public BigDecimal getChargingNum() {
        return chargingNum;
    }

    public void setChargingNum(BigDecimal chargingNum) {
        this.chargingNum = chargingNum;
    }

    public int getChargingTime() {
        return chargingTime;
    }

    public void setChargingTime(int chargingTime) {
        this.chargingTime = chargingTime;
    }

    public short getMaxVoltageCheckpointNo() {
        return maxVoltageCheckpointNo;
    }

    public void setMaxVoltageCheckpointNo(short maxVoltageCheckpointNo) {
        this.maxVoltageCheckpointNo = maxVoltageCheckpointNo;
    }

    public short getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(short maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public short getMaxTemperatureCheckpointNo() {
        return maxTemperatureCheckpointNo;
    }

    public void setMaxTemperatureCheckpointNo(byte maxTemperatureCheckpointNo) {
        this.maxTemperatureCheckpointNo = maxTemperatureCheckpointNo;
    }

    public short getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(short minTemperature) {
        this.minTemperature = minTemperature;
    }

    public short getMinTemperatureCheckpointNo() {
        return minTemperatureCheckpointNo;
    }

    public void setMinTemperatureCheckpointNo(byte minTemperatureCheckpointNo) {
        this.minTemperatureCheckpointNo = minTemperatureCheckpointNo;
    }

    public byte getVoltageStatus() {
        return voltageStatus;
    }

    public void setVoltageStatus(byte voltageStatus) {
        this.voltageStatus = voltageStatus;
    }

    public byte getSocStatus() {
        return socStatus;
    }

    public void setSocStatus(byte socStatus) {
        this.socStatus = socStatus;
    }

    public byte getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(byte currentStatus) {
        this.currentStatus = currentStatus;
    }

    public byte getTemperatureStatus() {
        return temperatureStatus;
    }

    public void setTemperatureStatus(byte temperatureStatus) {
        this.temperatureStatus = temperatureStatus;
    }

    public byte getInsulationStatus() {
        return insulationStatus;
    }

    public void setInsulationStatus(byte insulationStatus) {
        this.insulationStatus = insulationStatus;
    }

    public byte getConnectorStatus() {
        return connectorStatus;
    }

    public void setConnectorStatus(byte connectorStatus) {
        this.connectorStatus = connectorStatus;
    }

    public byte getIsAllowedCharging() {
        return isAllowedCharging;
    }

    public void setIsAllowedCharging(byte isAllowedCharging) {
        this.isAllowedCharging = isAllowedCharging;
    }

    @Override
    public String toString() {
        return "ChargingRealtimeData [chargeNo=" + chargeNo + ", ifName=" + ifName + ", requiredVoltage="
                + requiredVoltage + ", requiredCurrent=" + requiredCurrent + ", chargingModel=" + chargingModel
                + ", realtimeChargingVoltage=" + realtimeChargingVoltage + ", realtimeChargingCurrent="
                + realtimeChargingCurrent + ", maxVoltage=" + maxVoltage + ", realtimeSocStatus=" + realtimeSocStatus
                + ", remainedChargingTime=" + remainedChargingTime + ", chargingNum=" + chargingNum + ", chargingTime="
                + chargingTime + ", maxVoltageCheckpointNo=" + maxVoltageCheckpointNo + ", maxTemperature="
                + maxTemperature + ", maxTemperatureCheckpointNo=" + maxTemperatureCheckpointNo + ", minTemperature="
                + minTemperature + ", minTemperatureCheckpointNo=" + minTemperatureCheckpointNo + ", voltageStatus="
                + voltageStatus + ", socStatus=" + socStatus + ", currentStatus=" + currentStatus
                + ", temperatureStatus=" + temperatureStatus + ", insulationStatus=" + insulationStatus
                + ", connectorStatus=" + connectorStatus + ", isAllowedCharging=" + isAllowedCharging + "]";
    }
}