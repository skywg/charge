package com.iycharge.server.ccu.service;

import com.iycharge.server.ccu.msg.entity.ChargingRealtimeData;

/**
 * 充电桩充电实时数据service接口
 * @author bwang
 */
public interface ChargingRealtimeDataService {
    
    /**
     * 保存充电桩充电实时数据
     * @param chargingRealtimeData
     */
    void save(ChargingRealtimeData chargingRealtimeData);
}
