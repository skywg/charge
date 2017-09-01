package com.iycharge.server.ccu.msg.entity;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * BMS信息
 * @author bwang
 */
@Entity
@Table(name = "msg_bms_base_data")
public class BmsBaseData extends BaseEntity {

    /*
     * 充电桩编号
     */
    @Column(name = "charger_no")
    public String chargerNo;

    /*
     * 充电接口
     */
    @Column(name = "charger_if")
    public String ifName;

    /*
     * BMS 协议版本号
     */
    @Column(name = "protocal_version")
    public String protocalVersion;

    /*
     * 电池类型
     */
    @Column(name = "bettery_type")
    public String betteryType;

    /*
     * 整车动力蓄电池系统额定容量
     */
    @Column(name = "max_capacity")
    public int maxCapacity;

    /*
     * 整车动力蓄电池额定总电压
     */
    @Column(name = "max_voltage")
    public int maxVoltage;

    /*
     * 电池生产厂商名称
     */
    public String manufacturer;

    /*
     * 电池组序号
     */
    @Column(name = "serial_code")
    public String serialCode;

    /*
     * 电池组生产日期
     */
    @Column(name = "production_date")
    public Date productionDate;

    /*
     * 电池组充电次数
     */
    @Column(name = "charging_times")
    public int chargingTimes;

    /*
     * 电池组产权标识
     */
    public String property;

    /*
     * 保留字段
     */
    @Column(name = "reserved_field")
    public String reservedField;

    /*
     * 车辆识别码(VIN)
     */
    @Column(name = "vin_code")
    public String vinCode;

    /*
     * BMS 软件版本号
     */
    @Column(name = "software_version")
    public String softwareVersion;

    public BmsBaseData() {

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

    public String getProtocalVersion() {
        return protocalVersion;
    }

    public void setProtocalVersion(String protocalVersion) {
        this.protocalVersion = protocalVersion;
    }

    public String getBetteryType() {
        return betteryType;
    }

    public void setBetteryType(String betteryType) {
        this.betteryType = betteryType;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getMaxVoltage() {
        return maxVoltage;
    }

    public void setMaxVoltage(int maxVoltage) {
        this.maxVoltage = maxVoltage;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getSerialCode() {
        return serialCode;
    }

    public void setSerialCode(String serialCode) {
        this.serialCode = serialCode;
    }

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    public int getChargingTimes() {
        return chargingTimes;
    }

    public void setChargingTimes(int chargingTimes) {
        this.chargingTimes = chargingTimes;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getReservedField() {
        return reservedField;
    }

    public void setReservedField(String reservedField) {
        this.reservedField = reservedField;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    @Override
    public String toString() {
        return "BmsBaseData [chargerNo=" + chargerNo + ", ifName=" + ifName + ", protocalVersion="
                + protocalVersion + ", betteryType=" + betteryType + ", maxCapacity=" + maxCapacity + ", maxVoltage="
                + maxVoltage + ", manufacturer=" + manufacturer + ", serialCode=" + serialCode + ", productionDate="
                + productionDate + ", chargingTimes=" + chargingTimes + ", property=" + property + ", reservedField="
                + reservedField + ", vinCode=" + vinCode + ", softwareVersion=" + softwareVersion + "]";
    }
}