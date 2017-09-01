package com.iycharge.server.domain.entity.price;

import java.util.Arrays;
import java.util.List;

/**
 * 模板状态
 *
 * @author bwang
 */
public enum TemplateStatus {
    VALID("有效"), INVALID("无效");

    private String title;

    TemplateStatus(String title) {
        this.title = title;
    }

    public static List<TemplateStatus> asList() {
        return Arrays.asList(VALID, INVALID);
    }

    public String getTitle() {
        return title;
    }
}
