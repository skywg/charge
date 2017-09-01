package com.iycharge.server.domain.entity.price;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

/**
 * 时段电价定义
 * @author bwang
 */
@Entity
@Table(name="period_price")
public class PeriodPrice extends BaseEntity {
    
    /*
     * 时段开始时间
     */
    @JsonView(Summary.class)
    @Column(name="start_at")
    private String startAt;
    
    /*
     * 时段结束时间
     */
    @JsonView(Summary.class)
    @Column(name="end_at")
    private String endAt;
    
    /*
     *  时段内电价，单位（元/kwh）
     */
    @JsonView(Summary.class)
    private BigDecimal price;
    
    /*
     * 服务费，单位（元） 
     */
    @JsonView(Summary.class)
    private BigDecimal fee;
    
    /*
     * 备注
     */
    @JsonView(Summary.class)
    @Column(length=500)
    private String remark;
    
    /*
     * 所属模板
     */
    @JsonIgnore
    @ManyToOne(cascade=CascadeType.REFRESH)  
    private PriceTemplate template;
    
    public PeriodPrice() {
        
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
    
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public PriceTemplate getTemplate() {
        return template;
    }

    public void setTemplate(PriceTemplate template) {
        this.template = template;
    }
}
