package com.iycharge.server.ccu.msg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 充电桩实时数据
 *
 * @author bwang
 */
@Entity
@Table(name = "msg_charger_realtime_data")
public class ChargerRealtimeData extends BaseEntity {

    /*
     * 充电桩编码
     */
    @Column(name = "charger_no", length=16)
    public String chargerNo;

    /*
     * 充电接口
     */
    @Column(name = "if_name", length=16)
    public String ifName;

    /*
     * 充电接口类型
     */
    @Column(name = "if_type", length=4)
    public String ifType;

    /*
     * 充电接口工作状态
     */
    @Column(name = "if_work_status", length=4)
    public String ifWorkStatus;

    /*
     * 车辆连接状态
     */
    @Column(name = "car_conn_status")
    public byte carConnStatus;

    /*
     * 输入 A相电压
     */
    @Column(name = "input_av")
    public short inputAv;

    /*
     * 输入 B相电压
     */
    @Column(name = "input_bv")
    public short inputBv;

    /*
     * 输入 C 相电压
     */
    @Column(name = "input_cv")
    public short inputCv;

    /*
     * 输出电压
     */
    @Column(name = "output_voltage")
    public short outputVoltage;

    /*
     * 输出电流
     */
    @Column(name = "output_current")
    public short outputCurrent;

    /*
     * 电表表底
     */
    @Column(name = "init_electricity")
    public int initElectricity;

    /*
     * 生命周期
     */
    public short lifecycle;

    public ChargerRealtimeData() {

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

    public String getIfType() {
        return ifType;
    }

    public void setIfType(String ifType) {
        this.ifType = ifType;
    }

    public String getIfWorkStatus() {
        return ifWorkStatus;
    }

    public void setIfWorkStatus(String ifWorkStatus) {
        this.ifWorkStatus = ifWorkStatus;
    }

    public byte getCarConnStatus() {
        return carConnStatus;
    }

    public void setCarConnStatus(byte carConnStatus) {
        this.carConnStatus = carConnStatus;
    }

    public short getInputAv() {
        return inputAv;
    }

    public void setInputAv(short inputAv) {
        this.inputAv = inputAv;
    }

    public short getInputBv() {
        return inputBv;
    }

    public void setInputBv(short inputBv) {
        this.inputBv = inputBv;
    }

    public short getInputCv() {
        return inputCv;
    }

    public void setInputCv(short inputCv) {
        this.inputCv = inputCv;
    }

    public short getOutputVoltage() {
        return outputVoltage;
    }

    public void setOutputVoltage(short outputVoltage) {
        this.outputVoltage = outputVoltage;
    }

    public short getOutputCurrent() {
        return outputCurrent;
    }

    public void setOutputCurrent(short outputCurrent) {
        this.outputCurrent = outputCurrent;
    }

    public int getInitElectricity() {
        return initElectricity;
    }

    public void setInitElectricity(int initElectricity) {
        this.initElectricity = initElectricity;
    }

    public short getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(short lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public String toString() {
        return "ChargerRealtimeData [charerNo=" + chargerNo + ", ifName=" + ifName + ", ifType=" + ifType
                + ", ifWorkStatus=" + ifWorkStatus + ", carConnStatus=" + carConnStatus + ", inputAv=" + inputAv
                + ", inputBv=" + inputBv + ", inputCv=" + inputCv + ", outputVoltage=" + outputVoltage
                + ", outputCurrent=" + outputCurrent + ", initElectricity=" + initElectricity + ", lifecycle="
                + lifecycle + "]";
    }
    
}