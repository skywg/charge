package com.iycharge.server.domain.entity.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.account.Account;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {

    /**
     * 消息类型
     */
    @Enumerated(EnumType.STRING)
    @JsonView(BaseEntity.Summary.class)
    private NotificationType type;
    /**
     * 标题-通知和邮件需用
     */
    @Column(name = "title")
    @JsonView(BaseEntity.Summary.class)
    private String title;
    
    /**
     * 通知栏提示文字-通知和邮件需用
     */
    @Column(name = "ticker")
    @JsonView(BaseEntity.Summary.class)
    private String ticker;
    
    /**
     * 消息内容
     */
    @Lob  
    @Column(columnDefinition="TEXT")  
    @JsonView(BaseEntity.Summary.class)
    private String content;

    /**短信使用的模板id
     * 邮件保存的是模板调用name
     */
    @Column(length = 100)
    @JsonView(BaseEntity.Summary.class)
    private String tempName;

    /**短信使用的模板id
     * 邮件保存的是模板调用name
     */
    @Column(length = 100)
    @JsonView(BaseEntity.Summary.class)
    private String tempid;
    /**
     * 流水号
     */
    @Column(name="tag_number")
    private String tagNumber;
    /**
     * 接收者集合
     */
    @JsonView(BaseEntity.Summary.class)
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "notification_sender",
            joinColumns = {@JoinColumn(name = "notification_id")},
            inverseJoinColumns = {@JoinColumn(name = "account_id")})
    private List<Account> senders;
    /**
     * 发送类型
     */
    @JsonView(BaseEntity.Summary.class)
    @Enumerated(EnumType.STRING)
    @Column(name="send_type")
    private SendType sendType;
    
    /**
     * 发送目标
     */
    @JsonView(BaseEntity.Summary.class)
    @Column(name="tagets")
    private String tagets;
    /**
     * 消息状态
     */
    
    @JsonView(BaseEntity.Summary.class)
    @Enumerated(EnumType.STRING)
    @Column(name="notification_status")
    private NotificationStatus notificationStatus;
    
    public Notification() {
    }

	/*===================APP通知使用携带参数===========================*/
    //通知到达手机后续行为:openApp,openUrl
    private String action;
    //打开的链接
    private String url;
    //通知提醒的方式：声音或者震动
    private String voice;
    /*==============================================*/
    /**
     *  开始时间（用户查询时接收表单参数）
     */
    @JsonIgnore
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Transient 
    private Date startAt;

    /**
     *  发送时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name = "send_at")
    @JsonView(Summary.class)
    private Date sendAt;
    
    @Column
    @JsonView(BaseEntity.Summary.class)
    private String editor;

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

 

    public List<Account> getSenders() {
        return senders;
    }

    public void setSenders(List<Account> senders) {
        this.senders = senders;
    }

    @JsonView(BaseEntity.Summary.class)
    public Integer getSendersCount() {
        return senders.size();
    }


	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}


	public SendType getSendType() {
		return sendType;
	}

	public void setSendType(SendType sendType) {
		this.sendType = sendType;
	}

	public NotificationStatus getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(NotificationStatus notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public Date getSendAt() {
		return sendAt;
	}

	public void setSendAt(Date sendAt) {
		this.sendAt = sendAt;
	}

	public String getEditor() {
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public String getTagNumber() {
		return tagNumber;
	}

	public void setTagNumber(String tagNumber) {
		this.tagNumber = tagNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTagets() {
		return tagets;
	}

	public void setTagets(String tagets) {
		this.tagets = tagets;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getTempid() {
		return tempid;
	}

	public void setTempid(String tempid) {
		this.tempid = tempid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	
}
