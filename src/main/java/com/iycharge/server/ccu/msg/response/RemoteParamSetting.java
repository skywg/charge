package com.iycharge.server.ccu.msg.response;

import java.util.Arrays;
import java.util.Map;
import java.util.StringTokenizer;

import com.iycharge.server.ccu.msg.ResponseMsgBase;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.CheckSumUtil;
import com.iycharge.server.domain.entity.price.ParamNameConstant;

/**
 * 运营平台下发远程参数设置
 * @author bwang
 */
public class RemoteParamSetting extends ResponseMsgBase<Map<String, Object>>{

    /**
     * 消息体长度
     */
    private static final int LENGTH = 20;
    
    /**
     * @param msgType
     */
    public RemoteParamSetting(short msgType) {
        super(msgType);
    }

    @Override
    public void format() {
        String chargerCode  = (String)responseBody.get("chargerCode");
        Map<String, String> params = (Map<String, String>)responseBody.get("params");
        
        this.msgObject = new byte[LENGTH];
        int index = 0;
        this.msgObject[index++] = MSG_HEAD;        
        this.msgObject[index++] = (byte)((LENGTH - 3) & 0xFF); 
        this.msgObject[index++] = (byte)((LENGTH - 3) >>8 & 0xFF);
        this.msgObject[index++] = (byte)msgType;
        
        //充电桩编号
        byte[] chargerNoBytes = BCDUtil.str2Bcd(chargerCode);
        for(int i=0; i<8 - chargerNoBytes.length; i++) {
            this.msgObject[index++] = FILLED_VAL;
        }
        for(byte b : chargerNoBytes) {
            this.msgObject[index++] = b;
        }
        
        //远程ip
        long ip = parseLong(params.get(ParamNameConstant.REMOTE_IP));
        this.msgObject[index++] = (byte)((ip >> 24) & 0xFF);
        this.msgObject[index++] = (byte)((ip >> 16) & 0xFF);
        this.msgObject[index++] = (byte)((ip >>  8) & 0xFF);
        this.msgObject[index++] = (byte)(ip & 0xFF);
        
        //远程端口
        int port = Integer.parseInt(params.get(ParamNameConstant.REMOTE_PORT));
        this.msgObject[index++] = (byte)((port >> 8) & 0xFF);
        this.msgObject[index++] = (byte)(port & 0xFF);
        
        this.msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        this.msgObject[index++] = MSG_FOOTER;
    }
    
    public static long parseLong(String ip) {
        long result = 0;
        StringTokenizer token = new StringTokenizer(ip, ".");
        for (int i = 0; i < 4; i++) {
            int v = Integer.parseInt(token.nextToken());
            result = result << 8;
            result = result | v;
        }
        return result;
    }
}
