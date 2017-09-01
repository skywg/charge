package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.util.BCDUtil;

import net.sf.json.JSONObject;

/**
 * 充电桩应答运营平台远程控制启动报文
 * @author bwang
 */
public class RespAppStartChargerReq extends RequestMsgBase<JSONObject> {

    /**
     * @param msgType
     * @param msgObject
     */
    public RespAppStartChargerReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }

    @Override
    public JSONObject parse() {
        JSONObject obj = new JSONObject();
        
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);
        int index = 0;
        
        obj.put("chargerCode", BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, (index += 8))));
        obj.put("chargerConn", String.valueOf(datas[index]));
        obj.put("orderId"    , BCDUtil.bcd2Str(Arrays.copyOfRange(datas, ++index, (index += 8))));
        obj.put("result"     , datas[index] & 0xFF);
        
        return obj;
    }

}
