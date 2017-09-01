package com.iycharge.server.domain.entity.charger;

import java.util.Arrays;
import java.util.List;

/**
 * 联网方式
 * @author uwayxs
 *
 */
public enum NetingWorkModel {
	WIFI("WIFI"), THREEG("3G"),FOURG("4G"), WIRED("有线");

    private String title;

    NetingWorkModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<NetingWorkModel> asList() {
        return Arrays.asList(WIFI,THREEG,FOURG,WIRED);
    }
}
