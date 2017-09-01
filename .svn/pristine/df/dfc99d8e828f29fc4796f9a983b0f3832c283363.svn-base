package com.iycharge.server.domain.entity.feedback;

import java.util.Arrays;
import java.util.List;

import com.iycharge.server.domain.entity.content.ContentType;

/**
 * 反馈意见的来源
 * @author bwang
 */
public enum FeedBackChannel {
    
    APP("APP"),  WEBCHAT("微信公众号"), PHONE("电话");
    
	private String title;
    
    private FeedBackChannel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<FeedBackChannel> asList() {
        return Arrays.asList(APP, WEBCHAT,PHONE);
    }
}
