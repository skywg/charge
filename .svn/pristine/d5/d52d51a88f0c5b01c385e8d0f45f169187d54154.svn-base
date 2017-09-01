package com.iycharge.server.ccu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.entity.BatteryTempData;
import com.iycharge.server.ccu.repository.BatteryTempDataRepository;
import com.iycharge.server.ccu.service.BatteryTempDataService;

/**
 * 电池温度数据service接口
 * @author bwang
 */
@Service
public class BatteryTempDataServiceImpl implements BatteryTempDataService{
    
    @Autowired
    private BatteryTempDataRepository batteryTempDataRepository;
    
    @Override
    public void save(BatteryTempData batteryTempData) {
        batteryTempDataRepository.saveAndFlush(batteryTempData);
    }
}
