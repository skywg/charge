package com.iycharge.server.ccu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.entity.ChargingRealtimeData;
import com.iycharge.server.ccu.repository.ChargingRealtimeDataRepository;
import com.iycharge.server.ccu.service.ChargingRealtimeDataService;

/**
 * 充电桩充电实时数据service接口实现类
 * @author bwang
 */
@Service
public class ChargingRealtimeDataServiceImpl implements ChargingRealtimeDataService{
    
    @Autowired
    private ChargingRealtimeDataRepository chargingRealtimeDataRepository;

    @Override
    public void save(ChargingRealtimeData chargingRealtimeData) {
        chargingRealtimeDataRepository.saveAndFlush(chargingRealtimeData);
    }
}
