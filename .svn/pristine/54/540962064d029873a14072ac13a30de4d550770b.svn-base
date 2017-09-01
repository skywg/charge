package com.iycharge.server.domain.entity.order;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

/**
 *  分段计费明细实体定义类
 * @author bwang
 */
@Entity
@Table(name = "order_items")
public class OrderItem  extends BaseEntity {
    
    /*
     * 时段起始时间
     */
    @JsonView(Summary.class)
    private Date startAt;
    
    /*
     *  时间结束时间 
     */
    @JsonView(Summary.class)
    private Date endAt;
    
    /*
     * 时段内充电量
     */
    @JsonView(Summary.class)
    @Column(precision=12, scale=2)
    private BigDecimal degree;
    
    /*
     * 时段内单价，单位（元/kwh）
     */
    @JsonView(Summary.class)
    @Column(precision=10, scale=2)
    private BigDecimal price = BigDecimal.ZERO;
    
    /*
     * 时段内服务费单价，单位（元）
     */
    @JsonView(Summary.class)
    @Column(precision=10, scale=2)
    private BigDecimal fee = BigDecimal.ZERO;
    
    /*
     * 时段内消费金额，单位（元）
     */
    @JsonView(Summary.class)
    @Column(precision=10, scale=2)
    private BigDecimal money = BigDecimal.ZERO;
    
    @Transient
    private long time;
    
    public long getTime() {
        if(startAt == null || endAt == null) {
            return 0;
        }
		return (endAt.getTime()-startAt.getTime())/1000;
	}

	public void setTime(long time) {
		this.time = time;
	}

	/*
     * 关联订单
     */
    @JsonIgnore
    @ManyToOne(cascade=CascadeType.REFRESH)
    private Order order;
    
    public OrderItem() {
        
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

    public BigDecimal getDegree() {
        return degree;
    }

    public void setDegree(BigDecimal degree) {
        this.degree = degree;
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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }    
    
    public String formatChargingTime() {
        String format = "";
        int h = (int)(this.getTime() / 60 / 60);
        int m = (int)((this.getTime() - h * 60 * 60) / 60);
        int s = (int)(this.getTime() % 60);
        
        if(h > 0) {
            format += h + "小时";
        }
        
        if(m > 0) {
            format += m + "分钟";
        }
        
        if(s > 0) {
            format += s + "秒";
        }
        
        return format;
    }
}
