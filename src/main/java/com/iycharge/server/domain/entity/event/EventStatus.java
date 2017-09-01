package com.iycharge.server.domain.entity.event;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author bwang
 */
public enum EventStatus {
    
    CONFIRM("确认"), SUSPENDING("待处理"),  HANDLING("处理中"), SOLVED("已解决"), CLOSED("已关闭");
    
    private String title;
    
    private EventStatus(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public List<EventStatus> asList() {
        return Arrays.asList(CONFIRM, SUSPENDING,HANDLING,SOLVED,CLOSED);
    }
}
