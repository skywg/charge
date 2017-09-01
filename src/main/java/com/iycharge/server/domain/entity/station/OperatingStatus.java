package com.iycharge.server.domain.entity.station;

import java.util.Arrays;
import java.util.List;

/**
 * 电站营业状态
 * @author bwang
 */
public enum OperatingStatus {
    OPERATING("已投运"), CONSTRUCTION("建设中"), PLANING("规划中");
    
    private String title;
    
    private OperatingStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<OperatingStatus> asList() {
        return Arrays.asList(OPERATING, CONSTRUCTION, PLANING);
    }
}
