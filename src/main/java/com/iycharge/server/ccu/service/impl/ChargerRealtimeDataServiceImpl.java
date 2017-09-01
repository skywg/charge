package com.iycharge.server.ccu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.entity.ChargerRealtimeData;
import com.iycharge.server.ccu.repository.ChargerRealtimeDataRepository;
import com.iycharge.server.ccu.service.ChargerRealtimeDataService;

/**
 * 充电桩实时数据service接口
 * @author bwang
 */
@Service
public class ChargerRealtimeDataServiceImpl implements ChargerRealtimeDataService {
    
    @Autowired
    private ChargerRealtimeDataRepository chargerRealtimeDataRepository;
    
    @Override
    public void save(ChargerRealtimeData chargerRealtimeData) {       
        chargerRealtimeDataRepository.saveAndFlush(chargerRealtimeData);
    }
}
  