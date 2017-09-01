package com.iycharge.server.ccu.msg.response;

import java.util.Arrays;
import java.util.Map;

import com.iycharge.server.ccu.msg.ResponseMsgBase;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.CheckSumUtil;

/**
 * 运营平台应答充电桩实时数据
 * @author bwang
 */
public class ChargerRealtimeDataResp extends ResponseMsgBase<Map<String, Object>> {
    
    /**
     * 消息体长度
     */
    private static final int LENGTH = 14;
    
    public ChargerRealtimeDataResp(short msgType) {
        super(msgType);
    }

    @Override
    public void format() {      
        this.msgObject = new byte[LENGTH];
        int index = 0;
        this.msgObject[index++] = MSG_HEAD;        
        this.msgObject[index++] = (byte)((LENGTH - 3) & 0xff); 
        this.msgObject[index++] = (byte)((LENGTH - 3) >>8 & 0xff);
        this.msgObject[index++] = (byte)msgType;
        
        byte[] chargerNoBytes = BCDUtil.str2Bcd(responseBody.get("chargerNo").toString());
        for(int i=0; i<8 - chargerNoBytes.length; i++) {
            this.msgObject[index++] = FILLED_VAL;
        }
        for(byte b : chargerNoBytes) {
            this.msgObject[index++] = b;
        }
        
        this.msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        this.msgObject[index++] = MSG_FOOTER;
    }

}
