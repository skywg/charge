package com.iycharge.server.domain.entity.price;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

/**
 * 电价模板定义类
 * 
 * 继承属性说明:
 *      createdAt : 首次创建的时间
 *      updatedAt ：再次编辑的时间
 * @author bwang
 */
@Entity
@Table(name="price_template")
public class PriceTemplate extends BaseEntity {
    
    /*
     * 模板名称
     */
    @JsonView(Summary.class)
    private String name;
    
    /*
     * 模板状态
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    private TemplateStatus status = TemplateStatus.VALID;
    
    /*
     * 时段定价
     */
    @JsonView(Detail.class)
    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PeriodPrice> periodPrice;
    
    /*
     * 模板描述
     */
    @JsonView(Summary.class)
    @Column(length=500)
    private String description;
    
    /*
     * 模板创建人
     */
    private String creator;
    
    public PriceTemplate() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TemplateStatus getStatus() {
        return status;
    }

    public void setStatus(TemplateStatus status) {
        this.status = status;
    }

    public List<PeriodPrice> getPeriodPrice() {
        return periodPrice;
    }

    public void setPeriodPrice(List<PeriodPrice> periodPrice) {
        this.periodPrice = periodPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

  

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
