package com.iycharge.server.ccu.msg.response;

import java.util.Arrays;
import java.util.Map;

import com.iycharge.server.ccu.msg.ResponseMsgBase;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.CheckSumUtil;
import com.iycharge.server.domain.entity.price.ParamNameConstant;

/**
 * 运营平台设置充电桩周期上传报文上报周期
 * @author bwang
 */
public class PeriodSetting extends ResponseMsgBase<Map<String, Object>> {
    
    /**
     * 消息体长度
     */
    private static final int LENGTH = 17;
     
    /**
     * @param msgType
     */
    public PeriodSetting(short msgType) {
        super(msgType);
    }


    @Override
    public void format() {
        String chargerCode = (String)responseBody.get("chargerCode");
        Map<String, String> params = (Map<String, String>)responseBody.get("params");
        
        this.msgObject = new byte[LENGTH];
        
        int index = 0;
        this.msgObject[index++] = MSG_HEAD;        
        this.msgObject[index++] = (byte)((LENGTH - 3) & 0xff); 
        this.msgObject[index++] = (byte)((LENGTH - 3) >>8 & 0xff);
        this.msgObject[index++] = (byte) msgType;
        
        // 电桩编号
        byte[] chargerNoBytes = BCDUtil.str2Bcd(chargerCode);
        for(int i=0; i<8 - chargerNoBytes.length; i++) {
            this.msgObject[index++] = 0x00;
        }
        for(byte b : chargerNoBytes) {
            this.msgObject[index++] = b;
        }
        
        //报文编码
        this.msgObject[index++] = RESERVED_VAL;
        
        int interval = Integer.parseInt(params.get(ParamNameConstant.INTERVAL));
        
        //上报周期        
        this.msgObject[index++] = (byte)(interval & 0xFF);
        this.msgObject[index++] = (byte)(interval >>8 & 0xFF);
        
        this.msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        this.msgObject[index++] = MSG_FOOTER;
       
    }
}
