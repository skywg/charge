package com.iycharge.server.ccu.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.response.AppStopChargerResp;
import com.iycharge.server.ccu.util.JConverter;

import net.sf.json.JSONObject;

/**
 * 下发停机指令
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_56)
@Component("MSG_" + MsgType._0X_56)
public class AppStopChargerExecutor extends ResponseLogicExecutorBase {
    Logger logger = LoggerFactory.getLogger(AppStopChargerExecutor.class);

    @Override
    public Object call() throws Exception {
        AppStopChargerResp appStopChargerResp = new AppStopChargerResp(MsgType._0X_56);
        appStopChargerResp.setResponseBody((JSONObject)msgObject);
        appStopChargerResp.format();
        channel.writeAndFlush(appStopChargerResp.getMsgObject());
        
        logger.info("app stop charger message : [" + JConverter.bytes2String(appStopChargerResp.getMsgObject()) + "]");
        return "success";
    }  
    
}
