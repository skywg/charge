package com.iycharge.server.domain.entity.utils;

import java.util.Arrays;
import java.util.List;

public enum Area {
    OTHER("其它"), UPTOWN("住宅小区"), OFFICE("办公场所");

    private String title;

    Area(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<Area> asList() {
        return Arrays.asList(OTHER, UPTOWN, OFFICE);
    }
}
