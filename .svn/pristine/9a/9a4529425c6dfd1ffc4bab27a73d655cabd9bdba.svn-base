package com.iycharge.server.ccu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.entity.ChargingRecord;
import com.iycharge.server.ccu.repository.ChargingRecordRepository;
import com.iycharge.server.ccu.service.ChargingRecordService;

/**
 * 充电记录service接口实现类
 * @author bwang
 */
@Service
public class ChargingRecordServiceImp implements ChargingRecordService {

    @Autowired
    private ChargingRecordRepository repository;
    
    @Override
    public void save(ChargingRecord record) {
        repository.saveAndFlush(record);
    }
}
