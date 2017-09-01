package com.iycharge.server.domain.entity.review;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class ReviewRating {
    
    /*
     * 总评分
     */
    @Column(name = "total_rating")
    @JsonView(BaseEntity.Summary.class)
    private BigDecimal total = BigDecimal.ZERO;
    
    /*
     * 环境评分
     */
    @Column(name = "env_rating")
    @JsonView(BaseEntity.Summary.class)
    private BigDecimal env = BigDecimal.ZERO;
    
    /*
     * 设备评分
     */
    @Column(name = "device_rating")
    @JsonView(BaseEntity.Summary.class)
    private BigDecimal device = BigDecimal.ZERO;
    
    /*
     * 充电速度评分
     */
    @Column(name = "speed_rating")
    @JsonView(BaseEntity.Summary.class)
    private BigDecimal speed = BigDecimal.ZERO;

    public ReviewRating() {
    }

    public ReviewRating(BigDecimal total, BigDecimal env, BigDecimal device, BigDecimal speed) {
        this.total = total;
        this.env = env;
        this.device = device;
        this.speed = speed;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getEnv() {
        return env;
    }

    public void setEnv(BigDecimal env) {
        this.env = env;
    }

    public BigDecimal getDevice() {
        return device;
    }

    public void setDevice(BigDecimal device) {
        this.device = device;
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }
}
