package com.iycharge.server.ccu.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.request.RespAppStopChargerReq;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.domain.entity.utils.RedisUtil;

import net.sf.json.JSONObject;

/**
 * 充电桩应答运营平台远程控制停机
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_57)
@Component("MSG_" + MsgType._0X_57)
public class RespAppStopChargerExecutor extends RequestLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(RespAppStopChargerExecutor.class);
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Override
    public void run() {
        RespAppStopChargerReq reqMsg = new RespAppStopChargerReq(MsgType._0X_57, msgObject);
        if(reqMsg.check()) {
            logger.info("RespAppStopChargerReq=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            JSONObject datas = reqMsg.parse();
            
            redisUtil.set(RedisUtil.PREFIX_STOP + datas.get("orderId"), datas.toString(), 24 * 3600L);
            
        } else {
          //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }
}
