package com.iycharge.server.domain.entity.content;

import java.util.Arrays;
import java.util.List;
public enum Classification {
	START("APP启动广告"),REGISTER("APP注册协议"),ACTIVITY("APP活动"),QUESTION("常见问题");
	private String title;
    
    private Classification(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<Classification> asList() {
        return Arrays.asList(START, REGISTER,ACTIVITY,QUESTION);
    }
}
