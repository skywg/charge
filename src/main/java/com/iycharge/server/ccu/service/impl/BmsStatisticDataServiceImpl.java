package com.iycharge.server.ccu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.entity.BmsStatisticData;
import com.iycharge.server.ccu.repository.BmsStatisticDataRepository;
import com.iycharge.server.ccu.service.BmsStatisticDataService;

/**
 * BMS 统计数service接口
 * @author bwang
 */
@Service
public class BmsStatisticDataServiceImpl implements BmsStatisticDataService {
    
    @Autowired
    private BmsStatisticDataRepository bmsStatisticDataRepository;

    @Override
    public void save(BmsStatisticData bmsStatisticData) {
        bmsStatisticDataRepository.saveAndFlush(bmsStatisticData);
    }
}
