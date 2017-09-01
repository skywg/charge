package com.iycharge.server.domain.entity.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
public class Rating {

    /*
     * 总体评分 (平均值)
     */
    @Column(name = "total_rating")
    @JsonView(BaseEntity.Summary.class)
    private BigDecimal total = BigDecimal.ZERO;

    /*
     * 总体评分 (总和)
     */
    @JsonIgnore
    @Column(name = "total_rating_scores")
    private BigDecimal totalRatingScores = BigDecimal.ZERO;

    /*
     * 总体评分次数
     */
    @JsonIgnore
    @Column(name = "total_rating_times")
    private Integer totalRatingTimes = 0;


    /////////////////////// Device ///////////////////////

    /*
     * 设备评分 (平均值)
     */
    @Column(name = "device_rating")
    @JsonView(BaseEntity.Summary.class)
    private BigDecimal device = BigDecimal.ZERO;

    /*
     * 设备评分 (总和)
     */
    @JsonIgnore
    @Column(name = "device_rating_scores")
    private BigDecimal deviceRatingScores = BigDecimal.ZERO;

    /*
     * 设备评分次数
     */
    @JsonIgnore
    @Column(name = "device_rating_times")
    private Integer deviceRatingTimes = 0;

    /////////////////////// speed ///////////////////////

    /*
     * 充电速度评分 (平均值)
     */
    @Column(name = "speed_rating")
    @JsonView(BaseEntity.Summary.class)
    private BigDecimal speed = BigDecimal.ZERO;

    /*
     * 充电速度评分 (总和)
     */
    @JsonIgnore
    @Column(name = "speed_rating_scores")
    private BigDecimal speedRatingScores = BigDecimal.ZERO;

    /*
     * 充电速度评分次数
     */
    @JsonIgnore
    @Column(name = "speed_rating_times")
    private Integer speedRatingTimes = 0;

    public Rating() {
    }

    public Rating(BigDecimal total, BigDecimal device, BigDecimal speed) {
        this.total = total;
        this.device = device;
        this.speed = speed;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTotalRatingScores() {
        return totalRatingScores;
    }

    public void setTotalRatingScores(BigDecimal totalRatingScores) {
        this.totalRatingScores = totalRatingScores;
    }

    public Integer getTotalRatingTimes() {
        return totalRatingTimes;
    }

    public void setTotalRatingTimes(Integer totalRatingTimes) {
        this.totalRatingTimes = totalRatingTimes;
    }

    public void updateTotal(BigDecimal score) {
        totalRatingScores = totalRatingScores.add(score);
        totalRatingTimes += 1;

        total = totalRatingScores.divide(BigDecimal.valueOf(totalRatingTimes), 1, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getDevice() {
        return device;
    }

    public void setDevice(BigDecimal device) {
        this.device = device;
    }

    public BigDecimal getDeviceRatingScores() {
        return deviceRatingScores;
    }

    public void setDeviceRatingScores(BigDecimal deviceRatingScores) {
        this.deviceRatingScores = deviceRatingScores;
    }

    public Integer getDeviceRatingTimes() {
        return deviceRatingTimes;
    }

    public void setDeviceRatingTimes(Integer deviceRatingTimes) {
        this.deviceRatingTimes = deviceRatingTimes;
    }

    public void updateDevice(BigDecimal score) {
        deviceRatingScores = deviceRatingScores.add(score);
        deviceRatingTimes += 1;

        device = deviceRatingScores.divide(BigDecimal.valueOf(deviceRatingTimes), 1, RoundingMode.HALF_DOWN);
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public BigDecimal getSpeedRatingScores() {
        return speedRatingScores;
    }

    public void setSpeedRatingScores(BigDecimal speedRatingScores) {
        this.speedRatingScores = speedRatingScores;
    }

    public Integer getSpeedRatingTimes() {
        return speedRatingTimes;
    }

    public void setSpeedRatingTimes(Integer speedRatingTimes) {
        this.speedRatingTimes = speedRatingTimes;
    }

    public void updateSpeed(BigDecimal score) {
        speedRatingScores = speedRatingScores.add(score);
        speedRatingTimes += 1;

        speed = speedRatingScores.divide(BigDecimal.valueOf(speedRatingTimes), 1, RoundingMode.HALF_DOWN);
    }
}
