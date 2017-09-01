package com.iycharge.server.ccu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.entity.BatteryVoltageData;
import com.iycharge.server.ccu.repository.BatteryVoltageDataRepository;
import com.iycharge.server.ccu.service.BatteryVoltageDataService;

/**
 * 电池电压数据service接口实现类
 * @author bwang
 */
@Service
public class BatteryVoltageDataServiceImpl implements BatteryVoltageDataService{

    @Autowired
    private BatteryVoltageDataRepository batteryVoltageDataRepository;
    
    @Override
    public void save(BatteryVoltageData batteryVoltageData) {
        batteryVoltageDataRepository.saveAndFlush(batteryVoltageData);
    }
    

}
