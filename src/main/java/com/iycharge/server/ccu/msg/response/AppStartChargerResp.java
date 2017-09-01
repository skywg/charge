package com.iycharge.server.ccu.msg.response;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.ResponseMsgBase;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.CheckSumUtil;
import com.iycharge.server.ccu.util.JConverter;

import net.sf.json.JSONObject;

/**
 * app控制电桩启动报文
 * @author bwang
 */
public class AppStartChargerResp extends ResponseMsgBase<JSONObject> {
    
    /**
     * 报文长度
     */
    private static final int LENGTH = 23;
    
    /**
     * @param msgType
     */
    public AppStartChargerResp(short msgType) {
        super(msgType);
    }

    @Override
    public void format() {
        
        String chargerCode  = (String)responseBody.get("chargerCode");
        String chargerConn  = (String)responseBody.get("chargerConn");
        String orderId      = (String)responseBody.get("orderId");
        
        this.msgObject = new byte[LENGTH];
        
        int index = 0;
        this.msgObject[index++] = MSG_HEAD;        
        this.msgObject[index++] = (byte)((LENGTH - 3) & 0xff); 
        this.msgObject[index++] = (byte)((LENGTH - 3) >>8 & 0xff);
        this.msgObject[index++] = (byte) msgType;
        
        // 电桩编号
        byte[] chargerNoBytes = BCDUtil.str2Bcd(chargerCode);
        for(int i=0; i<8 - chargerNoBytes.length; i++) {
            this.msgObject[index++] = FILLED_VAL;
        }
        for(byte b : chargerNoBytes) {
            this.msgObject[index++] = b;
        }
        
        //充电接口
        this.msgObject[index++] = JConverter.string2Bytes(chargerConn)[0];
        
        // 流水号
        byte[] seqCodeBytes = BCDUtil.str2Bcd(orderId);
        for(int i=0; i<8 - seqCodeBytes.length; i++) {
            this.msgObject[index++] = FILLED_VAL;
        }
        for(byte b : seqCodeBytes) {
            this.msgObject[index++] = b;
        }
        
        this.msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        this.msgObject[index++] = MSG_FOOTER;
    }

}
