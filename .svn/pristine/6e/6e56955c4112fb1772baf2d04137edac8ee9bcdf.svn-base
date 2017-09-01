package com.iycharge.server.domain.entity.account;

import java.util.Arrays;
import java.util.List;

/**
 * 证件类型
 * @author bwang
 */
public enum CardType {
    
    CUPCARD(1, "CPU卡"),M1CARD(2,"M1卡"), OTHER(3, "其他");
    
    private int code;
    
    private String name;
    
    private CardType(int code, String name) {
       this.code = code;
       this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    public static CardType getByCode(int code) {
        for(CardType type : CardType.values()) {
            if(type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
    
    public static List<CardType> asList() {
        return Arrays.asList(CUPCARD,M1CARD, OTHER);
    }

    @Override
    public String toString() {
        return name;
    }
}
