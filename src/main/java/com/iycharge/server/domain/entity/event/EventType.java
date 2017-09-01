package com.iycharge.server.domain.entity.event;

import java.util.Arrays;
import java.util.List;

/**
 * 事件类型
 * @author bwang
 */
public enum EventType {
    SYSTEM("系统故障"), BATTERY("电池故障");
    
    private String title;
    
    private EventType(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public static List<EventType> asList() {
        return Arrays.asList(SYSTEM, BATTERY);
    }
}
