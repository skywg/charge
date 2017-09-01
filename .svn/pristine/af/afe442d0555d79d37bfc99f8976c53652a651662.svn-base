package com.iycharge.server.ccu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.entity.BatteryParamData;
import com.iycharge.server.ccu.repository.BatteryParamDataRepository;
import com.iycharge.server.ccu.service.BatteryParamDataService;

/**
 * 电池参数信息Service接口 实现类
 * @author bwang
 */
@Service
public class BatteryParamDataServiceImpl implements BatteryParamDataService{
        
    @Autowired
    private BatteryParamDataRepository  batteryParamDataRepository;
    
    @Override
    public void save(BatteryParamData batteryParamData) {
        batteryParamDataRepository.saveAndFlush(batteryParamData);
    }
    
}
