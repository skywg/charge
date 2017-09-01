package com.iycharge.server.ccu.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.request.RespChargingAuthReq;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.domain.entity.utils.RedisUtil;

import net.sf.json.JSONObject;

/**
 * 处理充电桩应答认证报文
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_51)
@Component("MSG_" + MsgType._0X_51)
public class RespChargingAuthExecutor extends RequestLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(RespChargingAuthExecutor.class);
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Override
    public void run() {
        RespChargingAuthReq authResult = new RespChargingAuthReq(MsgType._0X_51, msgObject);
        if(authResult.check()) {
            logger.info("ChargingAuthResult=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");           
            JSONObject result = authResult.parse();
            
            redisUtil.set(RedisUtil.PREFIX_AUTH + result.get("orderId"), result.toString(), 24 * 3600L);
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }
}
