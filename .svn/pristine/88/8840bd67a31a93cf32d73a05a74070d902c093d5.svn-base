package com.iycharge.server.domain.entity.notification;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

@Entity
public class NotificationTemplate extends BaseEntity{
	
	/**
	 * 模板名称
	 */
	@JsonView
	private String name;
	/**
	 * 模板内容
	 */
	@JsonView(Summary.class)
	private String content;
	/**
	 * 模板状态
	 */
    @Enumerated(EnumType.STRING)
    @JsonView(BaseEntity.Summary.class)
	private NotificationTemplateStatus notificationTemplateStatus = NotificationTemplateStatus.EDIT;

	/**
	 * 签名位置0-前置;1-后置
	 */
    @JsonView(Summary.class)
    @Column(columnDefinition="boolean default 0", name="sign_place")
    private boolean signPlace = false;
    
    /**
	 * 短信类型0-触发;1-批量
	 */
    @JsonView(Summary.class)
    @Column(columnDefinition="boolean default 0", name="note_type")
    private boolean noteType = false;
    @JsonView(Summary.class)
    private String sign;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public NotificationTemplateStatus getNotificationTemplateStatus() {
		return notificationTemplateStatus;
	}
	public void setNotificationTemplateStatus(NotificationTemplateStatus notificationTemplateStatus) {
		this.notificationTemplateStatus = notificationTemplateStatus;
	}
	public boolean isSignPlace() {
		return signPlace;
	}
	public void setSignPlace(boolean signPlace) {
		this.signPlace = signPlace;
	}
	public boolean isNoteType() {
		return noteType;
	}
	public void setNoteType(boolean noteType) {
		this.noteType = noteType;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
    
    
}
