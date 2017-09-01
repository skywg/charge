package com.iycharge.server.domain.entity.admin;

import java.util.Arrays;
import java.util.List;

/**
 * app类型
 * @author bwang
 */
public enum AppType {
    
    ANDROID("Android"), IOS("ios");
    
    private String title;
    
    private AppType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    
    public static List<AppType> asList() {
        return Arrays.asList(ANDROID, IOS);
    }
}
