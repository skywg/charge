package com.iycharge.server.ccu.service;

import java.util.concurrent.Future;

import net.sf.json.JSONObject;

/**
 * 充电流程相关业务操作接口
 * @author bwang
 */
public interface ChargingFlowService {
    
    /**
     * 运营平台下发认证报文
     * @param params        下发认证报文需要相关参数
     * @return
     */
    Future<Object> sendAuthMsg(JSONObject params);
    
    /**
     * 运营平台远程控制充电桩启动
     * @param params        下发启动电桩报文需要相关参数
     * @return
     */
    Future<Object> sendStarChargerMsg(JSONObject params);
    
    /**
     * 运营平台远程控制充电桩停机
     * @param params        下发停机报文需要相关参数
     * @return
     */
    Future<Object> sendStopChargerMsg(JSONObject params);
}
