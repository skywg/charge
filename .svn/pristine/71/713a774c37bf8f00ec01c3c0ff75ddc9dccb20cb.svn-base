package com.iycharge.server.ccu.exec;

import org.springframework.stereotype.Component;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.response.ChargingAuthResp;

import net.sf.json.JSONObject;

/**
 * 充电认证下发报文业务逻辑处理
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_50)
@Component("MSG_" + MsgType._0X_50)
public class ChargingAuthExecutor extends ResponseLogicExecutorBase {

    @Override
    public Object call() throws Exception {      
        ChargingAuthResp chargingAuthResp = new ChargingAuthResp(MsgType._0X_50);
        try {
            chargingAuthResp.setResponseBody((JSONObject)msgObject);
            chargingAuthResp.format();
        } catch (Exception e) {
            e.printStackTrace();
        }
        channel.writeAndFlush(chargingAuthResp.getMsgObject());
        
        return "success";
    } 
}
