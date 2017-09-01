package com.iycharge.server.domain.entity.account;

import java.util.Arrays;
import java.util.List;

/**
 * 注册会员状态定义
 *
 * @author bwang
 */
public enum  AccountStatus {
    NORMAL("正常"), FORBIDDEN("违规");

    private String title;

    AccountStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<AccountStatus> asList() {
        return Arrays.asList(NORMAL, FORBIDDEN);
    }
}
