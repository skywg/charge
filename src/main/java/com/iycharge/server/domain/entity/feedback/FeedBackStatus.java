package com.iycharge.server.domain.entity.feedback;

import java.util.Arrays;
import java.util.List;

/**
 * 反馈意见处理过程中的状态
 * @author bwang
 */
public enum FeedBackStatus {
    
    CONFIRM("确认"), SUSPENDING("待处理"),  HANDLING("处理中"), SOLVED("已解决"), CLOSED("已关闭");
    
	private String title;
    
    private FeedBackStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<FeedBackStatus> asList() {
        return Arrays.asList(CONFIRM, SUSPENDING,HANDLING,SOLVED,CLOSED);
    }
}
