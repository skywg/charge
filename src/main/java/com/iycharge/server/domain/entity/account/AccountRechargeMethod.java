package com.iycharge.server.domain.entity.account;

import java.util.Arrays;
import java.util.List;

/**
 * 企业会员充值方式
 * @author bwang
 */
public enum AccountRechargeMethod {
    
    CASH(1, "现金支付"), OFFLINE(2, "线下转账");
    
    private int code;
    
    private String name;
    
    private AccountRechargeMethod(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    public static List<AccountRechargeMethod> asList() {
        return Arrays.asList(CASH, OFFLINE);
    }
}
