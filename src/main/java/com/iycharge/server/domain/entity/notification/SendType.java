package com.iycharge.server.domain.entity.notification;

import java.util.Arrays;
import java.util.List;


public enum SendType {
	MAIL("邮件"),NOTE("短信"),APPMSG("app消息"),APPNOT("app通知");
	private String title;
    
    private SendType(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public static List<SendType> asList() {
        return Arrays.asList(MAIL,NOTE,APPMSG);
    }
}
