package com.iycharge.server.domain.entity.notification;

import java.util.Arrays;
import java.util.List;

public enum NotificationTargetType {

    /*
     * 表示此消息应跳转到一个用户个人主页。
     */
    ACCOUNT("账户"),

    /*
     * 表示此消息的执行与单品有关，如单品的评论被回复等。
     */
    ITEM("单品"),

    /*
     * 表示此消息是来自系统的通知，一般在订单被修改如发货通知、退款提醒等。
     */
    ORDER("订单"),

    /*
     * 此类消息比较特殊，当客户端暂时无相应的支持时可使用此类型，或系统向客 户端推送运营内容或广告时使用。
     * 目标执行的数据即是网页链接，客户端需要 在本地弹出嵌入式网页窗口。
     */
    URL("链接");

    private String title;

    NotificationTargetType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public static List<NotificationTargetType> all() {
        return Arrays.asList(ACCOUNT, ITEM, ORDER, URL);
    }
}
