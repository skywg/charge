package com.iycharge.server.domain.entity.charger;

import java.util.Arrays;
import java.util.List;

/**
 * 充电接口
 * @author bwang
 */
public enum ChargerIf {
    DC("国标直流枪"), AC("国标交流枪");

    private String title;

    ChargerIf(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<ChargerIf> asList() {
        return Arrays.asList(DC, AC);
    }
}
