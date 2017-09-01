package com.iycharge.server.domain.entity.feedback;

import java.util.Arrays;
import java.util.List;

import com.iycharge.server.domain.entity.content.ContentType;

/**
 * 反馈意见的分类
 * @author bwang
 */
public enum FeedBackCategory {
    
    APP         ( "APP相关"),  
    WEBCHAT     ("微信公众号相关"), 
    CHARGER     ("电桩相关"), 
    CHARGING    ("充电相关"), 
    RECHARGE    ( "充值相关"), 
    OTHER       ("其他");
    
	private String title;
    
    private FeedBackCategory(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<FeedBackCategory> asList() {
        return Arrays.asList(APP, WEBCHAT,CHARGER,CHARGING,RECHARGE,OTHER);
    }
}
