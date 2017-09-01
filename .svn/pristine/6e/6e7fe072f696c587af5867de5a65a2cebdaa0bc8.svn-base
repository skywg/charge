package com.iycharge.server.domain.entity.account;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.iycharge.server.domain.entity.BaseEntity;

/**
 * 车辆认证审核记录
 *      继承属性说明： createdAt: 保留字段     updatedAt：保留字段 
 * @author bwang
 */
@Entity
@Table(name="car_audit_log")
public class CarAuditLog extends BaseEntity {
    
    /**
     * 审核人
     */
    private String auditor;
    
    /**
     * 审核结果, true : 审核通过， false： 审核不通过
     */

    @Column(columnDefinition="boolean default 0")
    private boolean result = false;
    
    @Transient
    private String desc;
    public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		if(result==false){
			this.desc="审核未通过";
		}else if(result==true){
			this.desc="审核通过";
		}
	}

	/**
     * 审核时间
     */
    private Date auditTime;
    
    /**
     * 审核备注 
     */
    private String  remark;
    
    public CarAuditLog() {
        
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
    
    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
