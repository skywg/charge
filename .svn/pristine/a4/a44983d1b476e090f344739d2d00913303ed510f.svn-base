package com.iycharge.server.domain.entity.order;

import java.util.Arrays;
import java.util.List;

public enum OrderStatus{
    PAID("已支付"), UNPAID("未支付"), DONE("已完成");

    private String title;

    OrderStatus(String title) {
        this.title = title;
    }

    public static List<OrderStatus> asList() {
        return Arrays.asList(PAID, UNPAID, DONE);
    }

    public String getTitle() {
        return title;
    }
}
