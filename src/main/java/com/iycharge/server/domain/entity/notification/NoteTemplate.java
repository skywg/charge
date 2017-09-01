package com.iycharge.server.domain.entity.notification;

import java.util.Date;

public class NoteTemplate {
	private String templateContent;
	private String templateCreateTime;
	private String templateId;
	private String templateName;
	private Date templateUpdateTime;
	
	
	
	public NoteTemplate() {
		super();
	}
	public NoteTemplate(String templateContent, String templateCreateTime, String templateId, String templateName,
			Date templateUpdateTime) {
		super();
		this.templateContent = templateContent;
		this.templateCreateTime = templateCreateTime;
		this.templateId = templateId;
		this.templateName = templateName;
		this.templateUpdateTime = templateUpdateTime;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public String getTemplateCreateTime() {
		return templateCreateTime;
	}
	public void setTemplateCreateTime(String templateCreateTime) {
		this.templateCreateTime = templateCreateTime;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public Date getTemplateUpdateTime() {
		return templateUpdateTime;
	}
	public void setTemplateUpdateTime(Date templateUpdateTime) {
		this.templateUpdateTime = templateUpdateTime;
	}
    
    
}
