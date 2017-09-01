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
 * 字典值
 * @author bwang
 */
@Entity
@Table(name="dict_data")
public class DictData extends BaseEntity {
    
    /*
     * 所属字典类型
     */
    @ManyToOne(cascade=CascadeType.REFRESH)
    private DictCategory dictCategory;
    
    /*
     * 字典key
     */
    
    @Column(length=64, unique=true)
    private String dictKey;
    
    /*
     * 字典值
     */
    private String dictValue;
    
    /*
     * 中文描述
     */
    @Column(length=64)
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
     * 显示顺序
     */
    
    private int sortNo;

    /*
     * 备注字段
     */
    @Column(length=1024)
    private String remark;
    
    /*
     * 包含的下一级的字典集合
     */
    @OneToMany(cascade=CascadeType.ALL, mappedBy="parent", fetch=FetchType.LAZY)
    private List<DictData> dictDataList = new ArrayList<>();
    
    /*
     * 
     */
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH}, optional=true)
    private DictData parent;
    /**
     * 显示父类型
     */
    
    @Transient
    private String parentName;
    public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parent.descr;
	}
    public DictData() {
        
    }

    public DictCategory getDictCategory() {
        return dictCategory;
    }

    public void setDictCategory(DictCategory dictCategory) {
        this.dictCategory = dictCategory;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
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

    public int getSortNo() {
        return sortNo;
    }

    public void setSortNo(int sortNo) {
        this.sortNo = sortNo;
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

    public void setDictDataList(List<DictData> dictDataList) {
        this.dictDataList = dictDataList;
    }

    public DictData getParent() {
        return parent;
    }

    public void setParent(DictData parent) {
        this.parent = parent;
    }
}
