package com.iycharge.server.ccu.exec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.response.AppStartChargerResp;
import com.iycharge.server.ccu.util.JConverter;

import net.sf.json.JSONObject;

/**
 * 下发启动电桩的指令
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_54)
@Service("MSG_" + MsgType._0X_54)
public class AppStartChargerExecutor extends ResponseLogicExecutorBase {
    
    Logger logger = LoggerFactory.getLogger(AppStartChargerExecutor.class);
    
    @Override
    public Object call() throws Exception {
        AppStartChargerResp appStartChargerResp = new AppStartChargerResp(MsgType._0X_54);
        appStartChargerResp.setResponseBody((JSONObject)msgObject);
        appStartChargerResp.format();
        channel.writeAndFlush(appStartChargerResp.getMsgObject());
        
        logger.info("app start charger message : [" + JConverter.bytes2String(appStartChargerResp.getMsgObject()) + "]");
        return "success";
    }
    
    
      
}
