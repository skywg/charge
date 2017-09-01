package com.iycharge.server.domain.entity.price;

import java.math.BigDecimal;

/**
 * 时段电价定义
 *
 */
public class Price  {
    
    
    private String startAt;
    
    
    private String endAt;
    
    
    private String price;
    
    
    private String fee;
    
    
    private String remark;

    private PriceTemplate template;
    
    public Price() {
        
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

   
    
    public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
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

