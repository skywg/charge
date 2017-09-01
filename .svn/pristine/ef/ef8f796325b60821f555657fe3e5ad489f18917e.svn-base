package com.iycharge.server.domain.entity.price;

import java.util.Arrays;
import java.util.List;

/**
 * 下发参数类型
 * @author bwang
 */
public enum ParamType {
    
    PRICE("电价"), PARAM("参数"), INTERVAL("上报周期"), UPDATE("升级");
    
    private String title;
    
    private ParamType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    
    public static List<ParamType> asList() {
        return Arrays.asList(PRICE,PARAM,INTERVAL,UPDATE);
    }
     
}
