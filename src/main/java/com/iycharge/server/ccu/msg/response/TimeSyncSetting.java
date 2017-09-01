package com.iycharge.server.ccu.msg.response;

import java.util.Arrays;
import java.util.Map;

import com.iycharge.server.ccu.msg.ResponseMsgBase;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.CheckSumUtil;

/**
 * server端发送时间同步报文
 * @author bwang
 */
public class TimeSyncSetting extends ResponseMsgBase<Map<String, Object>>{
    
    /**
     * 消息体长度
     */
    private static final int LENGTH = 21;
    
    public TimeSyncSetting(short msgType) {
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
        
        byte[] currentTimeBytes = BCDUtil.str2Bcd(responseBody.get("currentTime").toString());
        for(byte b : currentTimeBytes) {
            this.msgObject[index++] = b;
        }
        
        this.msgObject[index++] = (byte)CheckSumUtil.makeChecksum(Arrays.copyOfRange(msgObject, 3, LENGTH-2));
        
        this.msgObject[index++] = MSG_FOOTER;
    }
    
//    public static void main(String[] args) {
///*        byte[] arr = {258 >>8 & 0xff, 258 & 0xff};
//        System.out.println(Integer.toString(258, 16));
//        System.out.println(Integer.toString(258 >>8, 16));
//        System.out.println(Integer.toString(258 & 0xFF, 16));
//        System.out.println((arr[0] << 8) + arr[1]);
//        
//        System.out.println(JConverter.bytes2String(arr));*/
//        
///*        ServerTimeSyncResp resp = new ServerTimeSyncResp(MsgType._0X_02);
//        Map<String, Object> respBody = new HashMap<String, Object>();
//        respBody.put("chargerNo"  , "1001001");
//        respBody.put("currentTime", Utility.formatDate(new Date(), "yyyyMMddHHmmss"));
//       resp.setResponseBody(respBody);
//        
//        resp.format();
//        
//        System.out.println(JConverter.bytes2String(resp.getMsgObject()));*/
//       
///*        byte[] arr = {(byte)(65535 >> 8 & 0xff), (byte)(65535 & 0xff)};
//        System.out.println((arr[0] << 8 & 0xff00) +  (arr[1]&0xff));*/
//    }
}
