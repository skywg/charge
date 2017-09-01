package com.iycharge.server.domain.entity.order;

import java.util.Arrays;
import java.util.List;

public enum OrderType {
    PLUS("充值"), SUBTRACT("消费");

    private String title;

    OrderType(String title) {
        this.title = title;
    }

    public static List<OrderType> asList() {
        return Arrays.asList(PLUS, SUBTRACT);
    }

    public String getTitle() {
        return title;
    }
}
