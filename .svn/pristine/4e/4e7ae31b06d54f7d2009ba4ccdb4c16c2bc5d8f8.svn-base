package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.BmsStopChargingMsg;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;

/**
 * BMS停止报文
 * @author bwang
 */
public class BmsStopChargingMsgReq extends RequestMsgBase<BmsStopChargingMsg> {

    /**
     * @param msgType
     * @param msgObject
     */
    public BmsStopChargingMsgReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }

    @Override
    public BmsStopChargingMsg parse() {
        BmsStopChargingMsg bmsStopChargingMsg =  new BmsStopChargingMsg();
        
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);
        int index = 0;
        
        //充电桩编号 
        bmsStopChargingMsg.setChargerNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 8)));
        //充电接口
        bmsStopChargingMsg.setIfName(Integer.toString(datas[index], 16));
        //BMS 中止充电报文 
        bmsStopChargingMsg.setStopMsg(String.valueOf(datas[++index] & 0xFF));
        //BMS 中止充电故障原因
        bmsStopChargingMsg.setStopFaultMsg(String.valueOf((datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF)));
        //BMS 中止充电错误原因
        bmsStopChargingMsg.setStopErrorMsg(String.valueOf(datas[++index] & 0xFF));       
        //原始报文信息
        bmsStopChargingMsg.setSource(JConverter.bytes2String(msgObject));
        
        return bmsStopChargingMsg;
    }

}
