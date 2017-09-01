package com.iycharge.server.domain.entity.order;

import java.util.Arrays;
import java.util.List;

public enum PaidFrom {
    CARD("卡"), WALLET("钱包"), ALIPAY("支付宝"), WEBCHART("微信"),CASH("现金支付"),OFFLINE("线下转账");

    private String title;

    PaidFrom(String title) {
        this.title = title;
    }

    public static List<PaidFrom> asList() {
        return Arrays.asList(CARD, WALLET, ALIPAY, WEBCHART,CASH,OFFLINE);
    }

    public String getTitle() {
        return title;
    }
}
