package com.iycharge.server.ccu.service;

import com.iycharge.server.ccu.msg.entity.BatteryTempData;

/**
 * 电池温度数据service接口
 * @author bwang
 */
public interface BatteryTempDataService {
    
    /**
     * 保存电池温度数据
     * @param batteryTempData
     */
    void save(BatteryTempData batteryTempData);
}
