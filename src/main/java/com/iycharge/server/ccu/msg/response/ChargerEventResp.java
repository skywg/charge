package com.iycharge.server.ccu.msg.response;

import java.util.Arrays;
import java.util.Map;

import com.iycharge.server.ccu.msg.ResponseMsgBase;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.CheckSumUtil;

/**
 * 运营平台应答告警事件
 * @author bwang
 */
public class ChargerEventResp extends ResponseMsgBase<Map<String, Object>> {
    
    /**
     * 消息体长度
     */
    private static final int LENGTH = 14;
    
    public ChargerEventResp(short msgType) {
        super(msgType);
    }
    
    @Override
    public void format() {
        this.msgObject = new byte[LENGTH];
        int index = 0;
        //68H
        this.msgObject[index++] = MSG_HEAD;
        
        //ASDU长度        
        this.msgObject[index++] = (byte)((LENGTH - 3) & 0xff); 
        this.msgObject[index++] = (byte)((LENGTH - 3) >>8 & 0xff);
        
        //消息类型
        this.msgObject[index++] = (byte)msgType;
        
        //电桩编号
        byte[] chargerNoBytes = BCDUtil.str2Bcd(responseBody.get("chargerNo").toString());
        for(int i=0; i<8 - chargerNoBytes.length; i++) {
            this.msgObject[index++] = FILLED_VAL;
        }
        for(byte b : chargerNoBytes) {
            this.msgObject[index++] = b;
        }
        
        //校验和
        this.msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        //16H
        this.msgObject[index++] = MSG_FOOTER;
    }

}
