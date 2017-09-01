package com.iycharge.server.ccu.exec;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.response.PeriodSetting;
import com.iycharge.server.ccu.util.JConverter;

/**
 * 运营平台设置充电桩周期上传报文上报周期业务处理逻辑
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_14)
@Component("MSG_" + MsgType._0X_14)
public class PeriodSettingExecutor extends ResponseLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(ChargerStartEventExecutor.class);

    @Override
    public Object call() throws Exception {
        PeriodSetting periodSetting = new PeriodSetting(MsgType._0X_14);
        periodSetting.setResponseBody((Map<String, Object>)this.msgObject);
        periodSetting.format();
        logger.info("period setting message : [" + JConverter.bytes2String(periodSetting.getMsgObject()) + "]");
        return "success";
    }   
    
    
}
