package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.BmsStatisticData;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.OffsetCalcUtil;
import com.iycharge.server.ccu.util.OffsetCalcUtil.SigleBatteryVoltageCfg;
import com.iycharge.server.ccu.util.OffsetCalcUtil.TemperatureCfg;

/**
 * BMS 统计数据
 * @author bwang
 */
public class BmsStatisticDataReq extends RequestMsgBase<BmsStatisticData> {

    /**
     * @param msgType
     * @param msgObject
     */
    public BmsStatisticDataReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }

    @Override
    public BmsStatisticData parse() {
        BmsStatisticData bmsStatisticData = new BmsStatisticData();
        
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);        
        int index = 0;
        
        //充电桩编号
        bmsStatisticData.setChargerNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 8)));
        //充电接口
        bmsStatisticData.setIfName(Integer.toString(datas[index], 16));
        //终止荷电状态SOC(%)
        bmsStatisticData.setSocStatus((byte)(datas[++index] & 0xFF));
        //动力力蓄电池单体最低电压(V) 
        bmsStatisticData.setMinVoltage(
            OffsetCalcUtil.caculate(
                (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00),
                SigleBatteryVoltageCfg.bitVal,
                SigleBatteryVoltageCfg.offsetVal
             ).shortValue()
        );
        //动力蓄电池单体最高电压 
        bmsStatisticData.setMaxVoltage(
            OffsetCalcUtil.caculate(
                (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00),
                SigleBatteryVoltageCfg.bitVal,
                SigleBatteryVoltageCfg.offsetVal
             ).shortValue()
        );
        //动力蓄电池最低温度
        bmsStatisticData.setMinTemperature(
            OffsetCalcUtil.caculate( 
                datas[++index] & 0xFF,
                TemperatureCfg.bitVal,
                TemperatureCfg.offsetVal
            ).shortValue()
        );
        //动力蓄电池最高温度
        bmsStatisticData.setMaxTemperature(
            OffsetCalcUtil.caculate( 
                datas[++index] & 0xFF,
                TemperatureCfg.bitVal,
                TemperatureCfg.offsetVal
            ).shortValue()
        );
        //原始报文信息
        bmsStatisticData.setSource(JConverter.bytes2String(msgObject));
                
        return bmsStatisticData;
    }

}
