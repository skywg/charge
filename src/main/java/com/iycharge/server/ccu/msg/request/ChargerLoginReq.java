package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.ChargerLoginMsg;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;

/**
 * 充电桩登录辨识报文
 * 
 * msg : 68 00 11 01 00 00 00 00 10 00 10 00 10 00 01 00 10 16 58 16
 * @author bwang
 */
public class ChargerLoginReq extends RequestMsgBase<ChargerLoginMsg> {

    public ChargerLoginReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }
    
    @Override
    public ChargerLoginMsg parse() {
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);
        int index = 0;
        
        ChargerLoginMsg chargerLoginMsg = new ChargerLoginMsg();
        chargerLoginMsg.setChargerNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 8)));
        chargerLoginMsg.setProtocolVersion(Integer.toString(datas[index], 16));
        chargerLoginMsg.setMajorVersion(JConverter.bytes2String(Arrays.copyOfRange(datas, ++index, index += 3)));
        chargerLoginMsg.setChargerType(Integer.toString(datas[index], 16));
        chargerLoginMsg.setMeterNum(datas[++index] & 0xFF);
        chargerLoginMsg.setSource(JConverter.bytes2String(msgObject));
        
        return chargerLoginMsg;
    }
}
