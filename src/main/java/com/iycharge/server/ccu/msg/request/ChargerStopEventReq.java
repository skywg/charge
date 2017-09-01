package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.ChargerStopEvent;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.Utility;

/**
 * 充电桩启动事件
 * @author bwang
 */
public class ChargerStopEventReq extends RequestMsgBase<ChargerStopEvent> {

    /**
     * @param msgType
     * @param msgObject
     */
    public ChargerStopEventReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }

    @Override
    public ChargerStopEvent parse() {
        ChargerStopEvent stopEvent = new ChargerStopEvent();
        
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);
        
        int index = 0;       
        //充电桩编号
        stopEvent.setChargerNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, (index += 8))));
        //充电接口
        stopEvent.setIfName(Integer.toString(datas[index], 16));
        //充电认证方式
        stopEvent.setAuthType(datas[++index]);
        //加电卡卡号/流水号压
        stopEvent.setOrderId(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, ++index, (index += 8))));
        //事件发生生时间 
        stopEvent.setStopTime(
            Utility.parseDate(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, (index += 7))), "yyyyMMddHHmmss")
                
        );
        //停机原因
        stopEvent.setStopCause((short)(datas[index] & 0xFF));
        //原始报文
        stopEvent.setSource(JConverter.bytes2String(msgObject));
        
        return stopEvent;
    }

}
