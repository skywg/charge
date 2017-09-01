package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.util.BCDUtil;

/**
 * 充电装应答时间报文
 * @author bwang
 */
public class RespTimeSynSettingReq extends RequestMsgBase<Map<String, Object>> {

    /**
     * @param msgType
     * @param msgObject
     */
    public RespTimeSynSettingReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }

    @Override
    public Map<String, Object> parse() {
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);
        int index = 0;
        
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("chargerNo"  , BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, (index += 8))));
        obj.put("currentTime", BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, (index += 7))));
        
        return obj;
    }   
}
 