package com.iycharge.server.ccu.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.entity.BatteryTempData;
import com.iycharge.server.ccu.msg.request.BatteryTempDataReq;
import com.iycharge.server.ccu.service.BatteryTempDataService;
import com.iycharge.server.ccu.util.JConverter;

/**
 * 电池温度数据上传业务处理逻辑
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_83)
@Service("MSG_" + MsgType._0X_83)
public class BatteryTempDataExecutor extends RequestLogicExecutorBase {
    
    private Logger logger = LoggerFactory.getLogger(BatteryTempDataExecutor.class);
    
    @Autowired
    private BatteryTempDataService service;

    @Override
    public void run() {
        BatteryTempDataReq request = new BatteryTempDataReq(MsgType._0X_83, msgObject);
        if(request.check()) {
            logger.info("BatteryTempData=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            BatteryTempData batteryTempData = request.parse();
            service.save(batteryTempData);
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
        
    } 
}
