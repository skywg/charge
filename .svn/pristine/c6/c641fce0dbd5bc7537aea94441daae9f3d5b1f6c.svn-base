package com.iycharge.server.domain.entity.charger;

import java.util.Arrays;
import java.util.List;

public enum ChargerStatus {
	IDLE("空闲"), OFFLINE("离线"), REPAIR("检修"), CHARGING("充电中"), OCCUPIED("占用");

    private String title;

    ChargerStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<ChargerStatus> asList() {
        return Arrays.asList(IDLE, OFFLINE, REPAIR, CHARGING, OCCUPIED);
    }
}
