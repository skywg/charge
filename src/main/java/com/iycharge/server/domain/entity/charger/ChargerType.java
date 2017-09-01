package com.iycharge.server.domain.entity.charger;

import java.util.Arrays;
import java.util.List;

public enum ChargerType {
	DC("直流充电桩"), AC("交流充电桩");

    private String title;

    ChargerType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<ChargerType> asList() {
        return Arrays.asList(DC, AC);
    }
}
