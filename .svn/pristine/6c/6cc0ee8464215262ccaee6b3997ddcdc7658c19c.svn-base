package com.iycharge.server.ccu.exec;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.iycharge.server.admin.message.listener.MessageChannel;
import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.request.RespRemoteParamSettingReq;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.domain.entity.utils.RedisUtil;

/**
 * 充电桩应答远程参数设置的业务处理逻辑
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_13)
@Component("MSG_" + MsgType._0X_13)
public class RespRemoteParamSettingExecutor extends RequestLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(RespRemoteParamSettingExecutor.class);
    
    @Resource
    private RedisUtil redisUtil;
    
    @Override
    public void run() {
        RespRemoteParamSettingReq request = new RespRemoteParamSettingReq(MsgType._0X_13, msgObject);
        if(request.check()) {
            logger.info("RemoteParamSettingResp=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            
            //更新参数设置结果
            redisUtil.sendMessage(MessageChannel.MSG_CHANNAL_PARAM, request.parse().toString());
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }   
}
