package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.BatteryVoltageData;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.OffsetCalcUtil;
import com.iycharge.server.ccu.util.OffsetCalcUtil.SigleBatteryVoltageCfg;

/**
 * 电池电压数据解析类
 * @author bwang
 */
public class BatteryVoltageDataReq extends RequestMsgBase<BatteryVoltageData> {

    /**
     * @param msgType
     * @param msgObject
     */
    public BatteryVoltageDataReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }

    @Override
    public BatteryVoltageData parse() {
        BatteryVoltageData batteryVoltageData = new BatteryVoltageData();
        
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);        
        int index = 0;
        
        //充电桩编号
        batteryVoltageData.setChargerNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 8)));
        //充电接口 
        batteryVoltageData.setIfName(Integer.toString(datas[index], 16));
        //单体动力力蓄电池电压
        StringBuilder sb = new StringBuilder();
        int voltage = 0;
        while(index < datas.length - 1) {
            voltage = (datas[++index] & 0xFF) +  (datas[++index] << 8 & 0xFF00);
            sb.append(OffsetCalcUtil.caculate(voltage, SigleBatteryVoltageCfg.bitVal, SigleBatteryVoltageCfg.offsetVal).intValue()).append(",");
        }
        
        //动力蓄电温度
        batteryVoltageData.setVoltageItems(sb.toString());
        //原始报文信息
        batteryVoltageData.setSource(JConverter.bytes2String(msgObject));
        
        return batteryVoltageData;
    }
}
