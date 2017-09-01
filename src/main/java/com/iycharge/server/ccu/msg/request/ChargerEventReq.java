package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.MsgAndExecAnnotation;
import com.iycharge.server.ccu.msg.MsgType;
import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.ChargerEvent;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.Utility;

/**
 * 告警事件
 * @author bwang
 */
@MsgAndExecAnnotation(msgType=MsgType._0X_66)
public class ChargerEventReq extends RequestMsgBase<ChargerEvent> {

    public ChargerEventReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }
    
    @Override
    public ChargerEvent parse() {
        
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);
        
        ChargerEvent event = new ChargerEvent();
        
        int index = 0;
        //充电桩编号
        event.setChargerNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 8)));
        //充电接口
        event.setIfName(Integer.toString(datas[index], 16));
        //故障代码
        event.setEventCode(datas[++index]);
        //故障产生时间
        event.setEventTime(
            Utility.parseDate(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, ++index, index += 7)), "yyyyMMddHHmmss")
        );
        //原始报文信息
        event.setSource(JConverter.bytes2String(msgObject));
        
        return event;
    }

}
