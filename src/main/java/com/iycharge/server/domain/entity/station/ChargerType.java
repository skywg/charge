package com.iycharge.server.domain.entity.station;

import java.util.Arrays;
import java.util.List;

/**
 * 电站运营的电桩种类
 * @author bwang
 */
public enum ChargerType {
    
    FAST("快充"), SLOW("慢充"), ALL("快充&慢充");
    
    private String title;
    
    private ChargerType(String title) {
        this.title = title;
    }
    
    public String getTitle() {
        return title;
    }
    
    public static List<ChargerType> asList() {
        return Arrays.asList(FAST, SLOW, ALL);
    }

    @Override
    public String toString() {       
        return this.title;
    }
    
    
}
