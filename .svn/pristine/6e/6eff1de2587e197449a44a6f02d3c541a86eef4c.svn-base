package com.iycharge.server.ccu.exec;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.response.RemoteParamSetting;
import com.iycharge.server.ccu.util.JConverter;

/**
 * 运营平台下发远程参数设置业务处理逻辑
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_12)
@Component("MSG_" + MsgType._0X_12)
public class RemoteParamSettingExecutor extends ResponseLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(RemoteParamSettingExecutor.class);

    @Override
    public Object call() throws Exception {
        RemoteParamSetting remoteParamSetting = new RemoteParamSetting(MsgType._0X_12);
        remoteParamSetting.setResponseBody((Map<String, Object>)this.msgObject);
        remoteParamSetting.format();
        logger.info("remote param setting message : [" + JConverter.bytes2String(remoteParamSetting.getMsgObject()) + "]");
        return "success";
    }    
}
