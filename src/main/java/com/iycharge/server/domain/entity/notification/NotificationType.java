package com.iycharge.server.domain.entity.notification;

import java.util.Arrays;
import java.util.List;

public enum NotificationType {

    ITEM_PUBLISHED("新单品发布"),
    ITEM_REVIEW_REPLIED("单品发布的评论被回复"),
    ITEM_REVIEW_LIKED("单品发布的评论被赞");

    private String title;

    NotificationType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public static List<NotificationType> all() {
        return Arrays.asList(ITEM_PUBLISHED, ITEM_REVIEW_REPLIED, ITEM_REVIEW_LIKED);
    }
}
