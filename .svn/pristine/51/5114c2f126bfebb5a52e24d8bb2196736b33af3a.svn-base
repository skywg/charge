package com.iycharge.server.ccu.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.entity.BmsStatisticData;
import com.iycharge.server.ccu.msg.request.BmsStatisticDataReq;
import com.iycharge.server.ccu.service.BmsStatisticDataService;
import com.iycharge.server.ccu.util.JConverter;

/**
 * BMS 统计数据上传业务处理
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_85)
@Service("MSG_" + MsgType._0X_85)
public class BmsStatisticDataExecutor extends RequestLogicExecutorBase {
    
    private Logger logger = LoggerFactory.getLogger(BmsStatisticDataExecutor.class);
    
    @Autowired
    private BmsStatisticDataService service;

    @Override
    public void run() {        
        BmsStatisticDataReq request = new BmsStatisticDataReq(MsgType._0X_85, msgObject);
        if(request.check()) {
            logger.info("BmsStatisticData=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            BmsStatisticData bmsStatisticData = new BmsStatisticData();
            service.save(bmsStatisticData);
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    } 
}
