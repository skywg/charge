package com.iycharge.server.domain.entity.account;

import java.util.Arrays;
import java.util.List;

/**
 * Created by godfrey on 16/10/13.
 */
public enum CardStatus {

    NORMAL("正常"),REPORT_OF_LOSS("挂失"),CANCELLED_CARD("销卡"),CHARGERING_CARD("充电中");

    private String title;

    CardStatus(String title) {
            this.title = title;
        }

    public String getTitle() {
        return title;
    }

    public static List<CardStatus> asList() {
        return Arrays.asList(NORMAL,REPORT_OF_LOSS,CANCELLED_CARD,CHARGERING_CARD);
    }


}
