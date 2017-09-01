package com.iycharge.server.ccu.exec;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.iycharge.server.admin.message.listener.MessageChannel;
import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.request.RespPriceSettingReq;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.domain.entity.utils.RedisUtil;

/**
 * 充电桩应答费率设置
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_11)
@Component("MSG_" + MsgType._0X_11)
public class RespPriceSettingExecutor extends RequestLogicExecutorBase{
    
    Logger logger = LoggerFactory.getLogger(RespPriceSettingExecutor.class);
    
    @Resource
    private RedisUtil redisUtil;
    
    @Override
    public void run() {
        RespPriceSettingReq request = new RespPriceSettingReq(MsgType._0X_11, msgObject);
        if(request.check()) {
            logger.info("PriceSettingResult=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");          
            
            //更新费率设置结果
            redisUtil.sendMessage(MessageChannel.MSG_CHANNAL_PRICE, request.parse().toString());
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    } 
}
