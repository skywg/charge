package com.iycharge.server.domain.entity.price;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iycharge.server.domain.entity.BaseEntity;

/**
 * 下发参数的模板
 *      集成属性说明: createdAt : 模板创建时间       updatedAt : 模板更新时间
 * @author bwang
 */
@Entity
public class ParamTemplate extends BaseEntity {
    
    /**
     * 模板名称
     */
    @Column(length=128, nullable=false)
    private String name;
    
    /**
     * 模板类型
     */
    @Column(nullable=false)
    private String type;
    
    /**
     * 模板描述信息
     */
    @Column(length=1024, nullable=false)
    private String description;
    
    /**
     * 创建人
     */
    @Column(nullable=false)
    private String creator;
    
    /**
     * 模板状态
     */
    @Column(nullable=false)
    private String status;
    /*
     * 删除状态del删除，其他未删除
     */
    @JsonIgnore
    private String delStatus;
    
    
    public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="template")
    private List<ParamTemplateAttr> paramList;
    
    public ParamTemplate() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ParamTemplateAttr> getParamList() {
        return paramList;
    }

    public void setParamList(List<ParamTemplateAttr> paramList) {
        this.paramList = paramList;
    }
}
