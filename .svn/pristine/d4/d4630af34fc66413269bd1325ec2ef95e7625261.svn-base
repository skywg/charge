package com.iycharge.server.domain.entity.notification;

import java.util.Arrays;
import java.util.List;

public enum NotificationTemplateStatus{
	EDIT("编辑"),SUBMITED("已提交"),CHECKING("审核中"),CHECKED("已审核");
	private String title;
	private NotificationTemplateStatus(String title){
	    this.title = title;
	}
	public String getTitle() {
	    return this.title;
	}
	    
	public static List<NotificationTemplateStatus> asList() {
	    return Arrays.asList(EDIT, SUBMITED,CHECKING,CHECKED);
	}
}
