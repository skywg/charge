package com.iycharge.server.domain.entity.station;

import java.util.Arrays;
import java.util.List;

public enum StationStatus {
    OFFLINE("离线"), IDLE("有空闲"), CHARGING("充电中"), FAULT("故障");

    private String title;

    StationStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<StationStatus> asList() {
        return Arrays.asList(OFFLINE, IDLE, CHARGING, FAULT);
    }
}
