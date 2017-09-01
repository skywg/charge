package com.iycharge.server.domain.entity.station;

import java.util.Arrays;
import java.util.List;

public enum StationType {
    PUBLIC("公共站"), FRIEND("驻地站"),PRIVATE("专用站");

    private String title;

    private StationType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<StationType> asList() {
        return Arrays.asList(PUBLIC,FRIEND, PRIVATE);
    }
}
