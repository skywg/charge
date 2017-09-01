package com.iycharge.server.domain.entity.utils;

import java.util.Arrays;
import java.util.List;

public enum PaymentMethod {
    APP("APP支付"), CARD("刷卡支付"), ALL("APP支付&刷卡支付"), OTHER("其他支付");

    private String title;

    PaymentMethod(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<PaymentMethod> asList() {
        return Arrays.asList(APP,OTHER, CARD,ALL);
    }
}
