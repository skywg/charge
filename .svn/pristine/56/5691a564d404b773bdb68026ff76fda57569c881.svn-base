package com.iycharge.server.ccu.msg.response;

import java.math.BigDecimal;
import java.util.Arrays;

import org.springframework.util.StringUtils;

import com.iycharge.server.ccu.msg.ResponseMsgBase;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.CheckSumUtil;
import com.iycharge.server.ccu.util.JConverter;

import net.sf.json.JSONObject;

/**
 * 运营平台下发认证报文
 * @author bwang
 */
public class ChargingAuthResp extends ResponseMsgBase<JSONObject> {
    
    /**
     * 消息体长度
     */
    private static final int LENGTH = 55;
    
    /**
     * @param msgType
     */
    public ChargingAuthResp(short msgType) {
        super(msgType);
    }

    @Override
    public void format() {
        this.msgObject = new byte[LENGTH];
        int index = 0;
        this.msgObject[index++] = MSG_HEAD;       
        this.msgObject[index++] = (byte)((LENGTH - 3) & 0xFF); 
        this.msgObject[index++] = (byte)((LENGTH - 3) >>8 & 0xFF);
        this.msgObject[index++] = (byte)msgType;
        
        //充电桩编号
        byte[] chargerNoBytes = BCDUtil.str2Bcd(responseBody.getString("chargerCode"));
        for(int i=0; i<8 - chargerNoBytes.length; i++) {
            this.msgObject[index++] = FILLED_VAL;
        }
        
        for(byte b : chargerNoBytes) {
            this.msgObject[index++] = b;
        }
        
       //充电接口
        this.msgObject[index++] = JConverter.string2Bytes(responseBody.getString("chargerConn"))[0];
        
        //流水号 
        byte[] orderIdBytes = BCDUtil.str2Bcd(responseBody.get("orderId").toString());
        for(int i=0; i<8 - orderIdBytes.length; i++) {
            this.msgObject[index++] = FILLED_VAL;
        }
        
        for(byte b : orderIdBytes) {
            this.msgObject[index++] = b;
        }
        
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
                this.msgObject[index++] = RESERVED_VAL;
            }
        } else {
            for(int i=0; i<11 - phoneBytes.length; i++) {
                this.msgObject[index++] = FILLED_VAL;
            }
            
            for(byte b : phoneBytes) {
                this.msgObject[index++] = b;
            }
        }
     
        //车辆 VIN码 
        byte[] vinBytes = responseBody.get("vin") != null ? responseBody.get("vin").toString().getBytes() : null;
                
        if(vinBytes == null) {
            for(int i=0; i<17; i++) {
                this.msgObject[index++] = RESERVED_VAL;
            }
        } else {
            for(int i=0; i<17 - vinBytes.length; i++) {
                this.msgObject[index++] = FILLED_VAL;
            }
            
            for(byte b : vinBytes) {
                this.msgObject[index++] = b;
            }
        }
        
        this.msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        this.msgObject[index++] = MSG_FOOTER;
    }
}
