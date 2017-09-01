package com.iycharge.server.ccu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.entity.BmsBaseData;
import com.iycharge.server.ccu.repository.BmsBaseDataRepository;
import com.iycharge.server.ccu.service.BmsBaseDataService;

/**
 * BMS信息service接口
 * @author bwang
 */
@Service
public class BmsBaseDataServiceImpl implements BmsBaseDataService{

    @Autowired
    private BmsBaseDataRepository bmsBaseDataRepository;
    
    @Override
    public void save(BmsBaseData bmsBaseData) {
        bmsBaseDataRepository.saveAndFlush(bmsBaseData);
    }
}
