package com.iycharge.server.domain.elastic.document;

import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.station.StationStatus;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.math.BigDecimal;

@Document(indexName = "iycharger", type = "device", shards = 1, replicas = 0)
public class Device {

    /*
     * id 
     */
    @Id
    private String id;
    
    /*
     * 电站Id
     */
    private long stationId;
    
    /*
     * 充电站的电桩数
     */
    private int totalCount;
    
    /*
     * 空闲电桩数
     */
    private int idleCount;
    
    /*
     * 名称
     */
    private String name;
    
    /*
     * 运营商的名称
     */
    private String operatorName;
    
    /*
     * 电站所包含电桩的类型
     */
    private String chargeType;
    
    /*
     * 电站对外开发类型
     */
    private String stationType;

    /*
     * 电站状态.
     */
    private StationStatus status = StationStatus.OFFLINE;
    
    /*
     * 功能区域
     */
    private String area;
    
    /*
     * 计费方式
     */
    private String paymentMethod;

    /*
     * 电力单位价值, 单位为 0/kWh
     */
    private BigDecimal price = BigDecimal.ZERO;

    /*
     * 位置之省份
     */
    private String province;

    /*
     * 位置之城市
     */
    private String city;

    /*
     * 充电站位置之行政区
    */
    private String district;

    /*
     * 位置之详细地址
     */
    private String address;
    
    /*
     * 充电桩位置(纬度,经度)
     */
    @GeoPointField
    private GeoPoint geoPoint;
    
    /*
     * 距离给定坐标点的距离
     */
    private double distance;
    
    public Device() {
        
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getIdleCount() {
        return idleCount;
    }

    public void setIdleCount(int idleCount) {
        this.idleCount = idleCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getStationType() {
        return stationType;
    }

    public void setStationType(String stationType) {
        this.stationType = stationType;
    }

    public StationStatus getStatus() {
        return status;
    }

    public void setStatus(StationStatus status) {
        this.status = status;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Device(Station station) {
        this.setId(station.getSearchId());
        this.setStationId(station.getId());
        this.setName(station.getName());
        this.setOperatorName(station.getOperator().getName());
        this.setTotalCount(station.getChargers().size());
        this.setChargeType(station.getChargerType());
        this.setStationType(station.getStationType());       
        this.setArea(station.getArea());
        this.setProvince(station.getProvince());
        this.setCity(station.getCity());
        this.setDistrict(station.getDistrict());
        this.setAddress(station.getAddress());
        this.setGeoPoint(new GeoPoint(station.getLatitude().doubleValue(), station.getLongitude().doubleValue()));
        this.setPaymentMethod(station.getPaymentMethod());
        this.setPrice(station.getPrice());
        
    }
    
    public String getCType() {
        return EntityUtil.getDictTile(CategoryConstant.CHAEGER_TYPE, this.getChargeType());
    }
    
    public String getSType() {
        return EntityUtil.getDictTile(CategoryConstant.STATION_TYPE, this.getStationType());
    }
    
    public String getSStatus() {
        return this.getStatus().getTitle();
    }
    
    public String getAType() {
        return EntityUtil.getDictTile(CategoryConstant.AREA_TYPE, this.getArea());
    }
    
    public String getPayType() {
        return EntityUtil.getDictTile(CategoryConstant.PAYMENT_TYPE, this.getPaymentMethod());
    }
}
