package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.util.BCDUtil;

import net.sf.json.JSONObject;

/**
 * 充电桩应答远程参数设置
 * @author bwang
 */
public class RespRemoteParamSettingReq extends RequestMsgBase<JSONObject> {

    /**
     * @param msgType
     * @param msgObject
     */
    public RespRemoteParamSettingReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }

    @Override
    public JSONObject parse() {
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);
        int index = 0;
        
        JSONObject obj = new JSONObject();
        obj.put("chargerCode", BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, (index += 8))));
        obj.put("result"   , datas[index] & 0xFF);
        
        return obj;
    }
}
