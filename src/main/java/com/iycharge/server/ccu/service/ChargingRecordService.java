package com.iycharge.server.ccu.service;

import com.iycharge.server.ccu.msg.entity.ChargingRecord;

/**
 * 充电桩充电记录service接口
 * @author bwang
 */
public interface ChargingRecordService {
    
    /**
     * 保存充电桩充电记录数据
     * @param record
     */
    void save(ChargingRecord record);
}
