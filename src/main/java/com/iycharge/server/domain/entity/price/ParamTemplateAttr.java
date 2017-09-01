package com.iycharge.server.domain.entity.price;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

/**
 * 模板包含参数
 * @author bwang
 */
@Entity
public class ParamTemplateAttr extends BaseEntity {
    
    /**
     * 属性名
     */
    @Column(nullable=false)
    @JsonView(Summary.class)
    private String attrName;
    
    /**
     * 属性值
     */
    @Column(nullable=false)
    @JsonView(Summary.class)
    private String attrVal;
    
    @JsonIgnore
    @ManyToOne(cascade=CascadeType.REFRESH)
    private ParamTemplate template;

	public ParamTemplateAttr() {
        
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public String getAttrVal() {
        return attrVal;
    }

    public void setAttrVal(String attrVal) {
        this.attrVal = attrVal;
    }

    public ParamTemplate getTemplate() {
        return template;
    }

    public void setTemplate(ParamTemplate template) {
        this.template = template;
    }
}
