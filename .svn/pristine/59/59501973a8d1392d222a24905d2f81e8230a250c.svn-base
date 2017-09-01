package com.iycharge.server.ccu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.entity.ChargerStopEvent;
import com.iycharge.server.ccu.repository.ChargerStopEventRepository;
import com.iycharge.server.ccu.service.ChargerStopEventService;

/**
 * 充电桩停机事件service接口
 * @author bwang
 */
@Service
public class ChargerStopEventServiceImpl implements ChargerStopEventService {
    
    @Autowired
    private ChargerStopEventRepository chargerStopEventRepository;

    @Override
    public void save(ChargerStopEvent chargerStopEvent) {
        chargerStopEventRepository.saveAndFlush(chargerStopEvent);
    }
}
