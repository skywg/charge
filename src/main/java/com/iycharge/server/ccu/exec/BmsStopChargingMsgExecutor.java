package com.iycharge.server.ccu.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.entity.BmsStopChargingMsg;
import com.iycharge.server.ccu.msg.request.BmsStopChargingMsgReq;
import com.iycharge.server.ccu.service.BmsStopChargingMsgService;
import com.iycharge.server.ccu.util.JConverter;

/**
 * BMS 中止充电报文
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_84)
@Service("MSG_" + MsgType._0X_84)
public class BmsStopChargingMsgExecutor extends RequestLogicExecutorBase {
    
    private Logger logger = LoggerFactory.getLogger(BmsStatisticDataExecutor.class);
    
    @Autowired
    private BmsStopChargingMsgService service;

    @Override
    public void run() {
        BmsStopChargingMsgReq request = new BmsStopChargingMsgReq(MsgType._0X_84, msgObject);
        if(request.check()) {
            logger.info("BmsStopChargingMsg=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            BmsStopChargingMsg bmsStopChargingMsg = request.parse();            
            service.save(bmsStopChargingMsg);
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
        
    }
}
