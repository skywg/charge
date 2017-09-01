package com.iycharge.server.ccu.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.entity.BmsErrorMsg;
import com.iycharge.server.ccu.msg.request.BmsErrorMsgReq;
import com.iycharge.server.ccu.service.BmsErrorMsgService;
import com.iycharge.server.ccu.util.JConverter;

/**
 * BMS 错误报文(0x86)上传业务处理
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_86)
@Service("MSG_" + MsgType._0X_86)
public class BmsErrorMsgExecutor extends RequestLogicExecutorBase {
    
    private Logger logger = LoggerFactory.getLogger(BmsErrorMsgExecutor.class);
    
    @Autowired
    private BmsErrorMsgService service;

    @Override
    public void run() {
        BmsErrorMsgReq request = new BmsErrorMsgReq(MsgType._0X_86, msgObject);
        if(request.check()) {
            logger.info("BmsErrorMsg=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            BmsErrorMsg bmsErrorMsg = request.parse(); 
            service.save(bmsErrorMsg);
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
                
    }
    
}
