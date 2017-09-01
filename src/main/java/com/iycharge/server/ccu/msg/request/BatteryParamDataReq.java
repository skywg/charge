package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.BatteryParamData;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.OffsetCalcUtil;
import com.iycharge.server.ccu.util.OffsetCalcUtil.CurrentCfg;
import com.iycharge.server.ccu.util.OffsetCalcUtil.SigleBatteryVoltageCfg;
import com.iycharge.server.ccu.util.OffsetCalcUtil.TemperatureCfg;
import com.iycharge.server.ccu.util.OffsetCalcUtil.TotalVoltageCfg;

/**
 * 电池参数信息
 * @author bwang
 */
public class BatteryParamDataReq extends RequestMsgBase<BatteryParamData> {

    /**
     * @param msgType
     * @param msgObject
     */
    public BatteryParamDataReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }

    @Override
    public BatteryParamData parse() {
        
        BatteryParamData batteryParamData = new BatteryParamData();
        
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);        
        int index = 0;
        
        //充电桩编号
        batteryParamData.setChargerNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 8)));        
        //充电接口 
        batteryParamData.setIfName(Integer.toString(datas[index], 16));
        //单体动力电池最高充电电压
        batteryParamData.setAllowedMaxVoltage(
            OffsetCalcUtil.caculate(
                (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00),
                SigleBatteryVoltageCfg.bitVal,
                SigleBatteryVoltageCfg.offsetVal
             ).shortValue()
        );
        //最高允许充电电流
        batteryParamData.setAllowedMaxCurrent(
            OffsetCalcUtil.caculate(
                (short)((datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00)),
                CurrentCfg.bitVal,
                CurrentCfg.offsetVal
            ).shortValue()
        );
        //动力蓄电池标称总量
        batteryParamData.setTotalCapacity(
                (short)((datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00))
        );
        //最高允许充电总电压
        batteryParamData.setAllowedTotalVoltage(
            OffsetCalcUtil.caculate(        
                (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00),
                TotalVoltageCfg.bitVal,
                TotalVoltageCfg.offsetVal
            ).shortValue()
        );
        //最高允许温度
        batteryParamData.setAllowedMaxTemperature(
            OffsetCalcUtil.caculate( 
                datas[++index] & 0xFF,
                TemperatureCfg.bitVal,
                TemperatureCfg.offsetVal
            ).shortValue()
        );
        //整车动力蓄电池荷电状态 
        batteryParamData.setSocStatus(
                (short)((datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00))
        );
        //整车动力蓄电当前电池电压
        batteryParamData.setRealtimeVoltage(
                OffsetCalcUtil.caculate(        
                    (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00),
                    TotalVoltageCfg.bitVal,
                    TotalVoltageCfg.offsetVal
                ).shortValue()
        );
        //原始报文信息
        batteryParamData.setSource(JConverter.bytes2String(msgObject));
        
        return batteryParamData;
    }

}
