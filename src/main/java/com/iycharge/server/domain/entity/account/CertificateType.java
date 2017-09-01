package com.iycharge.server.domain.entity.account;

import java.util.Arrays;
import java.util.List;



/**
 * 证件类型
 * @author bwang
 */
public enum CertificateType {
    
    IDCARD(1, "身份证"),PASSPORT(2,"护照"),DRIVERLICENSE(3,"驾驶证"),ARMYID(4,"警官证"), OTHER(5, "其他");

    private int code;
    
    private String name;
    
    private CertificateType(int code, String name) {
       this.code = code;
       this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
    
    public static CertificateType getByCode(int code) {
        for(CertificateType type : CertificateType.values()) {
            if(type.getCode() == code) {
                return type;
            }
        }
        return null;
    }
    
    public static List<CertificateType> asList() {
        return Arrays.asList(IDCARD, OTHER);
    }

    @Override
    public String toString() {
        return name;
    }
}
