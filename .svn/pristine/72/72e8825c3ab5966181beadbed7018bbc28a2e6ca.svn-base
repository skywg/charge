package com.iycharge.server.ccu.msg.response;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import java.util.Map;

import com.iycharge.server.ccu.msg.ResponseMsgBase;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.CheckSumUtil;
import com.iycharge.server.ccu.util.Utility;
import com.iycharge.server.domain.entity.price.ParamNameConstant;

/**
 * 运营平台下发费率设置
 * @author bwang
 */
public class PriceSetting extends ResponseMsgBase<Map<String, Object>> {

    /**
     * @param msgType
     */
    public PriceSetting(short msgType) {
        super(msgType);
    }

    @Override
    public void format() {       
        Date          effectiveTime = (Date)responseBody.get("effectiveTime");
        Map<String, String> params  = (Map<String, String>)responseBody.get("params");
        
        final int periodNum = params.size() / 5;
        final int LENGTH = 14 + periodNum * 10;
        
        this.msgObject = new byte[LENGTH];
        
        int index = 0;       
        this.msgObject[index++] = MSG_HEAD;       
        this.msgObject[index++] = (byte)((LENGTH - 3) & 0xff); 
        this.msgObject[index++] = (byte)((LENGTH - 3) >>8 & 0xff);
        this.msgObject[index++] = (byte) msgType;
        
        //生效时间
        byte[] effectiveTimeBytes = BCDUtil.str2Bcd(Utility.formatDate(effectiveTime, "yyyyMMddHHmmss"));
        for(byte b : effectiveTimeBytes) {
            this.msgObject[index++] = b;
        }
        //时段长度
        this.msgObject[index++] = (byte)(periodNum & 0xFF);
        
        //分段设置
        for(int i=0; i<periodNum; i++) {
            byte[] startAt = BCDUtil.str2Bcd(params.get(ParamNameConstant.PRICE_START_TIME + i).replace(":", ""));
            
            for(int j=0; j<startAt.length; j++) {
                this.msgObject[index++] = startAt[j];
            }
            
            for(int k=0; k<3 - startAt.length; k++) {
                this.msgObject[index++] = FILLED_VAL;
            }
            
            byte[] endAt = BCDUtil.str2Bcd(params.get(ParamNameConstant.PRICE_END_TIME + i).replace(":", ""));
            
            for(int j=0; j<startAt.length; j++) {
                this.msgObject[index++] = endAt[j];
            }
            
            for(int k=0; k<3 - endAt.length; k++) {
                this.msgObject[index++] = FILLED_VAL;
            }
            
            int price = new BigDecimal(params.get(ParamNameConstant.PRICE_PRICE + i)).multiply(new BigDecimal(100)).intValue();           
            this.msgObject[index++] = (byte)(price & 0xFF);
            this.msgObject[index++] = (byte)(price >> 8 & 0xFF);
            
            int fee = new BigDecimal(params.get(ParamNameConstant.PRICE_FEE + i)).multiply(new BigDecimal(100)).intValue();             
            this.msgObject[index++] = (byte)(fee & 0xFF);
            this.msgObject[index++] = (byte)(fee >> 8 & 0xFF);
        }
        
        this.msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        this.msgObject[index++] = MSG_FOOTER;
    }
}
