package com.iycharge.server.ccu.service;

import com.iycharge.server.ccu.msg.entity.BmsStopChargingMsg;

/**
 * BMS 中止充电报文service接口
 * @author bwang
 */
public interface BmsStopChargingMsgService {
    
    /**
     * 保存BMS 中止充电报文
     * @param bmsStopCharginMsg
     */
    void save(BmsStopChargingMsg bmsStopCharginMsg);
}
