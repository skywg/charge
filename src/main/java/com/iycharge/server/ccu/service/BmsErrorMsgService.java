package com.iycharge.server.ccu.service;

import com.iycharge.server.ccu.msg.entity.BmsErrorMsg;

/**
 * BMS错误报文service接口
 * @author bwang
 */
public interface BmsErrorMsgService {
    
    /**
     * 保存BMS错误报文
     * @param bmsErrorMsg
     */
    void save(BmsErrorMsg bmsErrorMsg);
}
