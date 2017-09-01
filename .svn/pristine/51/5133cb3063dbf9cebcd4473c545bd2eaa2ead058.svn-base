package com.iycharge.server.ccu.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.entity.BmsErrorMsg;
import com.iycharge.server.ccu.repository.BmsErrorMsgRepository;
import com.iycharge.server.ccu.service.BmsErrorMsgService;

/**
 * BMS错误报文service接口
 * @author bwang
 */
@Service
public class BmsErrorMsgServiceImpl implements BmsErrorMsgService {
    
    @Autowired
    private BmsErrorMsgRepository bmsErrorMsgRepository;
    
    @Override
    public void save(BmsErrorMsg bmsErrorMsg) {
        bmsErrorMsgRepository.saveAndFlush(bmsErrorMsg);
    }
    
    
}
