package com.iycharge.server.domain.entity.dict;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.iycharge.server.domain.entity.BaseEntity;

/**
 * 字典类型
 * 
 * @author bwang
 */
@Entity
@Table(name = "dict_category")
public class DictCategory extends BaseEntity {

    /*
     * 类型名称,值固定不可变
     */
    @Column(unique=true, length=64, nullable=false)
    private String name;
    
    /*
     * 类型中文描述
     */
    @Column(length=64, nullable=false)
    private String descr;
    
    /*
     * 是否允许编辑
     */
    @Column(nullable=false, columnDefinition="BOOLEAN default 0")
    private boolean allowedEdit = true;

    /*
     * 是否允许删除
     */
    @Column(nullable=false, columnDefinition="BOOLEAN default 0")
    private boolean allowedDel  = true;

    /*
     * 备注
     */
    @Column(length=1024)
    private String remark;
    
    /*
     * 关联字典值
     */
    @OneToMany(mappedBy = "dictCategory", cascade = CascadeType.ALL,fetch=FetchType.LAZY)
    private List<DictData> dictDataList = new ArrayList<>();

	/*
     * 父类型
     */
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH}, optional=true)
    private DictCategory parent;
    
    @Transient
    private String parentName;
    public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parent.name;
	}

	/*
     * 子分类
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy="parent", fetch=FetchType.LAZY)
    private List<DictCategory> dictCategoryList = new ArrayList<>();

    public DictCategory() {

    }

    public String getName() {
        return name;   
    }

    public void setName(String name) { 
    	this.name=name;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public boolean isAllowedEdit() {
        return allowedEdit;
    }

    public void setAllowedEdit(boolean allowedEdit) {
        this.allowedEdit = allowedEdit;
    }

    public boolean isAllowedDel() {
        return allowedDel;
    }

    public void setAllowedDel(boolean allowedDel) {
        this.allowedDel = allowedDel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<DictData> getDictDataList() {
        return dictDataList;
    }

    public DictCategory getParent() {
        return parent;
    }

    public void setParent(DictCategory parent) {
        this.parent = parent;
    }

    public void setDictDataList(List<DictData> dictDataList) {
        this.dictDataList = dictDataList;
    }

    public List<DictCategory> getDictCategoryList() {
        return dictCategoryList;
    }

    public void setDictCategoryList(List<DictCategory> dictCategoryList) {
        this.dictCategoryList = dictCategoryList;
    }
}
