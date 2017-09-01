package com.iycharge.server.ccu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.entity.ChargerStartEvent;
import com.iycharge.server.ccu.repository.ChargerStartEventRepository;
import com.iycharge.server.ccu.service.ChargerStartEventService;

/**
 * 充电桩启动事件service接口
 * @author bwang
 */
@Service
public class ChargerStartEventServiceImpl implements ChargerStartEventService {
    
    @Autowired
    private ChargerStartEventRepository chargerStartEventRepository;
    
    @Override
    public void save(ChargerStartEvent chargerStartEvent) {
        chargerStartEventRepository.saveAndFlush(chargerStartEvent);
    }
}
