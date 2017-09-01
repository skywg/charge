package com.iycharge.server.ccu.msg.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BMS错误报文
 *
 * @author bwang
 */
@Entity
@Table(name = "msg_bms_error")
public class BmsErrorMsg extends BaseEntity{

    /*
     * 电桩编号
     */
    @Column(name = "charger_no")
    public String chargerNo;

    /*
     * 充电接口
     */
    @Column(name = "if_name")
    public String ifName;

    /*
     * 接收充电桩SPN2560=0x00 辨识报文超时
     */
    @Column(name = "spn1_timeout_flag")
    public byte spn1TimeoutFlag;

    /*
     * 接收充电桩SPN2560=0xaa 辨识报文超时
     */
    @Column(name = "spn2_timeout_flag")
    public byte spn2TimeoutFlag;

    /*
     * 接收充电桩的时间同步报文和充电桩最大输出能力报文超时
     */
    @Column(name = "syn_timeout_flag")
    public byte synTimeoutFlag;

    /*
     * 接收充电桩完成充电准备报文超时
     */
    @Column(name = "finished_timeout_flag")
    public byte finishedTimeoutFlag;

    /*
     * 接收充电桩充电状态报文超时
     */
    @Column(name = "working_timeout_flag")
    public byte workingTimeoutFlag;

    /*
     * 接收充电桩中止充电报文超时
     */
    @Column(name = "stop_timeout_flag")
    public byte stopTimeoutFlag;

    /*
     * 接收充电桩统计报文超时
     */
    @Column(name = "stat_timeout_flag")
    public byte statTimeoutFlag;

    /*
     * 其他
     */
    public String other;

    public BmsErrorMsg() {

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
    
    public byte getSpn1TimeoutFlag() {
        return spn1TimeoutFlag;
    }

    public void setSpn1TimeoutFlag(byte spn1TimeoutFlag) {
        this.spn1TimeoutFlag = spn1TimeoutFlag;
    }

    public byte getSpn2TimeoutFlag() {
        return spn2TimeoutFlag;
    }

    public void setSpn2TimeoutFlag(byte spn2TimeoutFlag) {
        this.spn2TimeoutFlag = spn2TimeoutFlag;
    }

    public byte getSynTimeoutFlag() {
        return synTimeoutFlag;
    }

    public void setSynTimeoutFlag(byte synTimeoutFlag) {
        this.synTimeoutFlag = synTimeoutFlag;
    }

    public byte getFinishedTimeoutFlag() {
        return finishedTimeoutFlag;
    }

    public void setFinishedTimeoutFlag(byte finishedTimeoutFlag) {
        this.finishedTimeoutFlag = finishedTimeoutFlag;
    }

    public byte getWorkingTimeoutFlag() {
        return workingTimeoutFlag;
    }

    public void setWorkingTimeoutFlag(byte workingTimeoutFlag) {
        this.workingTimeoutFlag = workingTimeoutFlag;
    }

    public byte getStopTimeoutFlag() {
        return stopTimeoutFlag;
    }

    public void setStopTimeoutFlag(byte stopTimeoutFlag) {
        this.stopTimeoutFlag = stopTimeoutFlag;
    }

    public byte getStatTimeoutFlag() {
        return statTimeoutFlag;
    }

    public void setStatTimeoutFlag(byte statTimeoutFlag) {
        this.statTimeoutFlag = statTimeoutFlag;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "BmsErrorMsg [chargerNo=" + chargerNo + ", ifName=" + ifName + ", spn1TimeoutFlag="
                + spn1TimeoutFlag + ", spn2TimeoutFlag=" + spn2TimeoutFlag + ", synTimeoutFlag=" + synTimeoutFlag
                + ", finishedTimeoutFlag=" + finishedTimeoutFlag + ", workingTimeoutFlag=" + workingTimeoutFlag
                + ", stopTimeoutFlag=" + stopTimeoutFlag + ", statTimeoutFlag=" + statTimeoutFlag + ", other=" + other
                + "]";
    }
}