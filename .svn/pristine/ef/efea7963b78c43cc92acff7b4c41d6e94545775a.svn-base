package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.util.BCDUtil;

/**
 * 充电桩上传用户信息认证报文
 * @author bwang
 */
public class CardAuthReq extends RequestMsgBase<Map<String, Object>> {
    
    public CardAuthReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }
    
    @Override
    public Map<String, Object> parse() {
        Map<String, Object> result = new HashMap<>();
        
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);
        
        int index = 0;
        //充电桩编号
        result.put("chargerNo", BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 8)));
        //加电卡卡号
        result.put("cardNo", BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 8)));
        //用户密码 
        result.put("cardPass", BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 3)));
        
        return result;
    }
   
}
