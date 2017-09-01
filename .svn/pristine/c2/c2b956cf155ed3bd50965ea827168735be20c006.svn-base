package com.iycharge.server.ccu.exec;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.response.PriceSetting;
import com.iycharge.server.ccu.util.JConverter;

/**
 * 费率下发业务处理逻辑
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_10)
@Component("MSG_" + MsgType._0X_10)
public class PriceSettingExecutor extends ResponseLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(PriceSettingExecutor.class);

    @Override
    public Object call() throws Exception {
        PriceSetting priceSetting = new PriceSetting(MsgType._0X_10);
        priceSetting.setResponseBody((Map<String, Object>)msgObject);
        priceSetting.format();        
        channel.writeAndFlush(priceSetting.getMsgObject());
        
        logger.info("price setting message : [" + JConverter.bytes2String(priceSetting.getMsgObject()) + "]");
        return "success";
    }
    
}
