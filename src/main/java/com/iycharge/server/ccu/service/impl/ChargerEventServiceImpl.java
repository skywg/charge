package com.iycharge.server.ccu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.entity.ChargerEvent;
import com.iycharge.server.ccu.repository.ChargerEventRepository;
import com.iycharge.server.ccu.service.ChargerEventService;

/**
 * 告警事件service接口实现类
 * @author bwang
 */
@Service
public class ChargerEventServiceImpl implements ChargerEventService {
    
    @Autowired
    ChargerEventRepository repositroy;
    
    @Override
    public void save(ChargerEvent event) {
        repositroy.saveAndFlush(event);
    }

}
