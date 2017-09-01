package com.iycharge.server.domain.entity.price;

import java.util.Arrays;
import java.util.List;

public enum PriceLevel {
    ALL(1), CHARGER(2), STATION(3), OPERATOR(4);

    private int value;

    PriceLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static List<PriceLevel> asList() {
        return Arrays.asList(ALL, CHARGER, STATION, OPERATOR);
    }
}
