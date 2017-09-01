package com.iycharge.server.domain.entity.charger;

import java.util.Arrays;
import java.util.List;

/**
 * 电桩型号
 * @author uwayxs
 *
 */
public enum ChargerModel {
	DD("双枪双充"), DQ("双枪切换"),DF("双枪分配"),DS("单枪");

    private String title;

    ChargerModel(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<ChargerModel> asList() {
        return Arrays.asList(DD, DQ,DF,DS);
    }
}
