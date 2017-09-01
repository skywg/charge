package com.iycharge.server.domain.entity.admin;

import java.util.Arrays;
import java.util.List;

/**
 *  日志所属模块
 * @author bwang
 */
public enum LogModule {
   
    PUBLIC("1", "公共模块"),
    CHARGER("2", "充电桩"),
    STATION("3", "充电站"),
    PARAMTEMPLATE("4", "参数模板"),
    PARAMSETTING("5", "参数下发"),
    ACCOUNT("6", "会员管理"),
    OPERATION("7", "运营商管理"),
    CARD("8", "充电卡管理"),
    CONTENT("9", "内容管理"),
    MESSAGE("10", "消息管理"),
    MANAGER("11", "用户管理"),
    ROLE("12", "角色分配"),
	EVENT("13", "告警信息"),
	ORDER("14", "订单管理");
    private String code;
    
    private String name;
    
    private LogModule(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    public static List<LogModule> asList() {
        return Arrays.asList(PUBLIC);
    }
}
