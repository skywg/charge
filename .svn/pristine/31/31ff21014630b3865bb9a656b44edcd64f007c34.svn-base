package com.iycharge.server.ccu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.entity.BmsStopChargingMsg;
import com.iycharge.server.ccu.repository.BmsStopChargingMsgRepository;
import com.iycharge.server.ccu.service.BmsStopChargingMsgService;

/**
 * BMS 中止充电报文service接口
 * @author bwang
 */
@Service
public class BmsStopChargingMsgServiceImpl implements BmsStopChargingMsgService {
    
    
    @Autowired
    private BmsStopChargingMsgRepository  bmsStopChargingMsgRepository;

    @Override
    public void save(BmsStopChargingMsg bmsStopCharginMsg) {
        bmsStopChargingMsgRepository.saveAndFlush(bmsStopCharginMsg);
    }
}
