package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.ChargerStartEvent;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.Utility;

/**
 * 充电桩启动事件
 * message: 68 00 1c 60 00 00 00 00 10 00 10 01 10 02 00 10 00 10 00 00 00 01 20 16 09 17 15 05 23 47 16
 * @author bwang
 */
public class ChargerStartEventReq extends RequestMsgBase<ChargerStartEvent> {

    /**
     * @param msgType
     * @param msgObject
     */
    public ChargerStartEventReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }

    @Override
    public ChargerStartEvent parse() {
        ChargerStartEvent startEvent = new ChargerStartEvent();
        
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);
        
        int index = 0;
        //充电桩编号
        startEvent.setChargerNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 8)));
        //充电接口
        startEvent.setIfName(Integer.toString(datas[index], 16));
        //充电认证方式
        startEvent.setAuthType((byte)(datas[index += 1] & 0xFF));
        //加电卡卡号/流水号压
        startEvent.setOrderId(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index += 1, index += 8)));
        //事件发生时间 
        startEvent.setStartTime(
            Utility.parseDate(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 7)), "yyyyMMddHHmmss")
                
        );
        //原始报文
        startEvent.setSource(JConverter.bytes2String(msgObject));
        return startEvent;
    }
    
}
