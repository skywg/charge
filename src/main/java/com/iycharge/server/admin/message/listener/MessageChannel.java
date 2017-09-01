package com.iycharge.server.admin.message.listener;

/**
 * 消息订阅/发布频道
 * @author bwang
 */
public interface MessageChannel {
    
    /**
     * 电桩连接/断连时消息的订阅/发布频道
     */
    String MSG_CHANNAL_CONN = "msg:conn";
    
    /**
     * 远程参数设置结果的订阅/发布频道
     */
    String MSG_CHANNAL_PARAM = "msg:param";
    
    /**
     * 电桩费率设置结果的订阅/发布频道
     */
    String MSG_CHANNAL_PRICE = "msg:param:price";
    
    /**
     * 充电桩报文上传周期
     */
    String MSG_CHANNAL_INTERVAL = "msg:param:interval";
    
    /**
     * 充电记录的订阅/发布频道
     */
    String MSG_CHANNAL_RECORD = "msg:record";
    
    /**
     * 告警事件的订阅/发布频道
     */
    String MSG_CHANNAL_EVENT = "msg:event";    
}
