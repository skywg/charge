package com.iycharge.server.ccu.exec;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.iycharge.server.admin.message.listener.MessageChannel;
import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.request.RespPeriodSettingReq;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.domain.entity.utils.RedisUtil;

/**
 * 充电桩应答平台设置充电桩周期上传报文上报周期业务处理逻辑
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_15)
@Component("MSG_" + MsgType._0X_15)
public class RespPeriodSettingExecutor extends RequestLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(RespPeriodSettingExecutor.class);
    
    @Resource
    private RedisUtil redisUtil;
    
    @Override
    public void run() {
        RespPeriodSettingReq request = new RespPeriodSettingReq(MsgType._0X_15, msgObject);
        if(request.check()) {
            logger.info("PeriodSettingResp=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
            //更新电桩参数设置结果
            redisUtil.sendMessage(MessageChannel.MSG_CHANNAL_INTERVAL, request.parse().toString());
        } else {
            //非法请求
            logger.error("invalid request=>[from : " + channel.remoteAddress() + ", message : " + JConverter.bytes2String(msgObject) + "]");
        }
    }
}
