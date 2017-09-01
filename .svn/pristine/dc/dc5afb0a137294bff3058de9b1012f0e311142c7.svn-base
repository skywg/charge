package com.iycharge.server.ccu.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.entity.BmsBaseData;
import com.iycharge.server.ccu.msg.request.BmsBaseDataReq;
import com.iycharge.server.ccu.service.BmsBaseDataService;
import com.iycharge.server.ccu.util.JConverter;

/**
 * BMS信息上传业务处理
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_80)
@Service("MSG_" + MsgType._0X_80)
public class BmsBaseDataExecutor extends RequestLogicExecutorBase {
    
    private Logger logger = LoggerFactory.getLogger(BmsBaseDataExecutor.class);
    
    @Autowired
    private BmsBaseDataService service;
    
    @Override
    public void run() {
        BmsBaseDataReq request = new BmsBaseDataReq(MsgType._0X_80, msgObject);
        if(request.check()) {
            logger.info("BmsBaseData=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            BmsBaseData bmsBaseData = new BmsBaseData();
            service.save(bmsBaseData);
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }
}
