package com.iycharge.server.ccu.msg.response;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.iycharge.server.ccu.msg.ResponseMsgBase;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.CheckSumUtil;

/**
 * 运营平台应答用户信息认证报文
 * @author bwang
 */
public class CardAuthResp extends ResponseMsgBase<Map<String, Object>> {
    
    /**
     * 消息体长度
     */
    private static final int LENGTH = 30;
    
    /**
     * @param msgType
     */
    public CardAuthResp(short msgType) {
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
        //响应码
        this.msgObject[index++] = (byte)(Integer.parseInt(responseBody.get("respCode").toString()) & 0xFF);
                
        //APP 账户余额, 单位分 
        BigDecimal balance = (BigDecimal)responseBody.get("balance");
        balance = balance.multiply(new BigDecimal(100));

        this.msgObject[index++] = (byte)(balance.intValue() & 0xFF);
        this.msgObject[index++] = (byte)(balance.intValue() >>  8 & 0xFF);
        this.msgObject[index++] = (byte)(balance.intValue() >> 16 & 0xFF);
        this.msgObject[index++] = (byte)(balance.intValue() >> 24 & 0xFF);
        
        //手机号
        byte[] phoneBytes = StringUtils.isEmpty(responseBody.get("phone")) ? null : responseBody.get("phone").toString().getBytes();
        
        if(phoneBytes == null) {
            for(int i=0; i<11; i++) {
                this.msgObject[index++] = FILLED_VAL;
            }
        } else {
            for(int i=0; i<11 - phoneBytes.length; i++) {
                this.msgObject[index++] = FILLED_VAL;
            }
            
            for(byte b : phoneBytes) {
                this.msgObject[index++] = b;
            }
        }
        
        //校验和
        this.msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        //16H
        this.msgObject[index++] = MSG_FOOTER;
    }

}
