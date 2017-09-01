package com.iycharge.server.domain.entity.feedback;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.admin.Manager;

/**
 * 用户反馈意见
 * 集成属性说明: createdAt : 记录创建时间    updatedAt : 记录最后更新时间
 * @author bwang
 */
@Entity
@Table(name="feedback")
public class FeedBack extends BaseEntity {
    
    /**
     * 按系统规则生成的流水号
     */
    private String seqNo;
    
    /**
     * 记录反馈用户的姓名
     */
    private String accountName;
    
    /**
     * 记录反馈用户的手机号
     */
    private String accountPhone;
    
    /**
     * 用户反馈内容
     */
    @JsonView(Summary.class)
    private String content;
    
    /**
     * 反馈问题的来源
     */
   
    private String channel;
    
    /**
     * 反馈问题定位所属分类
     */
    
    private String category;
    
    /**
     * 反馈问题是否为有效问题
     */
    private boolean isValid =  true;
    
    /**
     * 处理过程中的状态
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    private FeedBackStatus status;
    
    /**
     * 处理过程中的日志记录
     */
    @JsonView(Summary.class)
    @OneToMany(cascade=CascadeType.ALL, mappedBy="feedback", fetch=FetchType.LAZY)
    private List<FeedBackAuditLog> auditLogList;
    
    /**
     * 处理人
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "loginName")
    private Manager manager;  
    /**
     *  开始时间（用户查询时接收表单参数）
     */
    @JsonIgnore
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Transient 
    private Date startAt;
    @Transient
    private String pchannel;
    @Transient
    private String pcategory;
    public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	/**
     * 默认构造函数
     */
    public FeedBack() {
        
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(String accountPhone) {
        this.accountPhone = accountPhone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public FeedBackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedBackStatus status) {
        this.status = status;
    }

    public List<FeedBackAuditLog> getAuditLogList() {
        return auditLogList;
    }

    public void setAuditLogList(List<FeedBackAuditLog> auditLogList) {
        this.auditLogList = auditLogList;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

	public String getPchannel() {
		String tmp = this.getChannel();
		switch (tmp) {
		case "APP":return "APP";
		case "WEBCHAT":return "微信公众号";
		case "PHONE":return "电话";
		default:
			break;
		}
		return pchannel;
	}


	public String getPcategory() {
	    String tmp = this.getCategory();
	    switch (tmp) {
		case "APP":return "APP相关";
		case "WEBCHAT":return "微信公众号相关";
		case "CHARGER":return "电桩相关";
		case "CHARGING":return "充电相关";
		case "RECHARGE":return "充值相关";
		case "OTHER":return "其他";
		default:
			break;
		}
		return tmp;
	}


    
}
