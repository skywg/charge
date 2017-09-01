package com.iycharge.server.ccu.service;

import com.iycharge.server.ccu.msg.entity.ChargerEvent;

/**
 * 告警事件service接口
 * @author bwang
 */
public interface ChargerEventService {
    
    /**
     * 保存告警事件数据
     * @param event
     */
    void save(ChargerEvent event);
}
