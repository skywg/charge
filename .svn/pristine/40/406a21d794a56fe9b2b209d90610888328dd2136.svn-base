package com.iycharge.server.domain.entity.notification;

import java.util.Arrays;
import java.util.List;

public enum NotificationTemplateType{
	NOTE("短信");
	private String title;
	private NotificationTemplateType(String title){
		this.title = title;
	}
	public String getTitle() {
        return this.title;
    }
	public static List<NotificationTemplateType> all() {
        return Arrays.asList(NOTE);
    }
}
