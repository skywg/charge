package com.iycharge.server.domain.entity.admin;

import java.util.Arrays;
import java.util.List;

/**
 * 日志操作类型
 * @author bwang
 */
public enum LogType {
    
    LOGIN ("1", "登录"), 
    LOGOUT("2", "注销"),
    ADD("3", "添加"),
    EDIT("4", "修改"),
    DELETE("5", "删除"),
    REMIT("6", "划账"),
    RECHARGE("7", "充值"),
    AUDIT("8", "审核"),
    PUBLISH("9", "发布"),
    LOSSCARD("10", "挂失"),
    CANCELCARD("11", "销卡"),
    ENABLE("12", "启用"),
    DISABLE("13", "禁用"),
    RESET("14", "重置密码"),
    OFFSHELVE("15", "下架"),
    ISSUED("16", "下发"),
    EXPORT("17", "导出");
    
    private String code;
    
    private String name;
    
    private LogType(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static List<LogType> asList() {
        return Arrays.asList(LOGIN, LOGOUT);
    }
}
