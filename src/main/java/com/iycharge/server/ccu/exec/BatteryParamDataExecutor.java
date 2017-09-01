package com.iycharge.server.ccu.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.entity.BatteryParamData;
import com.iycharge.server.ccu.msg.request.BatteryParamDataReq;
import com.iycharge.server.ccu.service.BatteryParamDataService;
import com.iycharge.server.ccu.util.JConverter;

/**
 * 电池参数信息上传业务处理
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_81)
@Service("MSG_" + MsgType._0X_81)
public class BatteryParamDataExecutor extends RequestLogicExecutorBase {
    
    private Logger logger = LoggerFactory.getLogger(BatteryParamDataExecutor.class);
    
    @Autowired
    private BatteryParamDataService service;
    
    @Override
    public void run() {
        BatteryParamDataReq request = new BatteryParamDataReq(MsgType._0X_81, msgObject);
        if(request.check()) {
            logger.info("BatteryParamData=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            BatteryParamData batteryParamData = request.parse();
            service.save(batteryParamData);
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }
    
    
}
