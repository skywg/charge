package com.iycharge.server.domain.entity.account;

import java.util.Arrays;
import java.util.List;

/**
 * 会员类型
 * @author bwang
 */
public enum AccountType {
    
    PERSON(1, "个人会员"), COMPANY(2, "企业会员");
    
    private int code;
    
    private String name;
    
    private AccountType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static List<AccountType> asList() {
        return Arrays.asList(PERSON, COMPANY);
    }
}
