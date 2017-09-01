package com.iycharge.server.domain.entity.operator;

import java.util.Arrays;
import java.util.List;

/**
 * 运营商状态定义
 *
 * @author bwang
 */
public enum  OperatorStatus {
    NORMAL("正常"), FORBIDDEN("违规"),OTHER("其他");
	
    private String title;

    OperatorStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<OperatorStatus> asList() {
        return Arrays.asList(NORMAL, FORBIDDEN,OTHER);
    }
}
