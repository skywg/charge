package com.iycharge.server.domain.entity.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

/**
 * app版本管理
 * 集成属性说明：
 *      createdAt : 创建时间
 *      updatedAt : 更新时间           
 * @author bwang
 */
@Entity
@Table(name="app_version")
public class AppVersion extends BaseEntity{
    
    /*
     * 标题
     */
    @JsonView(Summary.class)
    private String title;
    
    /*
     * 版本号
     */
    @JsonView(Summary.class)
    private String version;
    
    /*
     * 升级对象
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    @Column(name="app_type")
    private AppType appType;
    
    /*
     * 是否强制升级
     */
    @JsonView(Summary.class)
    @Column(columnDefinition="boolean default 0", name="force_update")
    private boolean forceUpdate = false;
    
    /*
     * 更新内容描述
     */
    @JsonView(Summary.class)
    @Column(name="update_descr", length=2048)
    private String updateDescr;
    
    /*
     * app文件,名称命名规则：uecharger_{version}
     */
    @JsonView(Summary.class)
    @Column(name="app_file")
    private String appFile;
    
    /*
     * 备注
     */
    @JsonView(Summary.class)
    private String remark;
    
    /*
     * 操作员
     */
    @Column(name="admin_name")
    private String adminName;
    
    /*
     * 升级标志 ,对应界面点击升级按钮的状态 
     */
    @Column(name="update_flag")
    private boolean updateFlat = false;
    
    /*
     * 删除标记
     */
    
    /**
     *  开始时间（用户查询时接收表单参数）
     */
    @JsonIgnore
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Transient 
    private Date startAt;
    
    @Column(name="delete_flag")
    private boolean deleteFlag = false;
    
    public AppVersion() {
        
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public AppType getAppType() {
        return appType;
    }

    public void setAppType(AppType appType) {
        this.appType = appType;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    public String getUpdateDescr() {
        return updateDescr;
    }

    public void setUpdateDescr(String updateDescr) {
        this.updateDescr = updateDescr;
    }

    public String getAppFile() {
        return appFile;
    }

    public void setAppFile(String appFile) {
        this.appFile = appFile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public boolean isUpdateFlat() {
        return updateFlat;
    }

    public void setUpdateFlat(boolean updateFlat) {
        this.updateFlat = updateFlat;
    }

    public boolean isDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}
    
}
