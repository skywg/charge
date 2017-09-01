package com.iycharge.server.ccu.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.entity.BatteryVoltageData;
import com.iycharge.server.ccu.msg.request.BatteryVoltageDataReq;
import com.iycharge.server.ccu.service.BatteryVoltageDataService;
import com.iycharge.server.ccu.util.JConverter;

/**
 * 电池电压数据上传业务处理逻辑
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_82)
@Service("MSG_" + MsgType._0X_82)
public class BatteryVoltageDataExecutor extends RequestLogicExecutorBase {
    
    private Logger logger = LoggerFactory.getLogger(BatteryVoltageDataExecutor.class);
    
    @Autowired
    private BatteryVoltageDataService service;

    @Override
    public void run() {
        BatteryVoltageDataReq request = new BatteryVoltageDataReq(MsgType._0X_83, msgObject);
        if(request.check()) {
            logger.info("BatteryVoltageData=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            BatteryVoltageData batteryVoltageData = request.parse();
            service.save(batteryVoltageData);
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
        
    } 
}
