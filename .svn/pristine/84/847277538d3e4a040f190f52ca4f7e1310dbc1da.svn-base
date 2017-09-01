package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.ChargingRealtimeData;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.OffsetCalcUtil;
import com.iycharge.server.ccu.util.OffsetCalcUtil.ChargingElectricCfg;
import com.iycharge.server.ccu.util.OffsetCalcUtil.CurrentCfg;
import com.iycharge.server.ccu.util.OffsetCalcUtil.TemperatureCfg;
import com.iycharge.server.ccu.util.OffsetCalcUtil.TotalVoltageCfg;

/**
 * 充电桩充电实时数据
 * @author bwang
 */
public class ChargingRealtimeDataReq extends RequestMsgBase<ChargingRealtimeData> {

    /**
     * @param msgType
     * @param msgObject
     */
    public ChargingRealtimeDataReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }

    @Override
    public ChargingRealtimeData parse() {
        ChargingRealtimeData chargingRealtimeData = new ChargingRealtimeData();
        
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);
        
        //充电桩编号
        chargingRealtimeData.setChargeNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, 0, 8)));
        //充电接口
        chargingRealtimeData.setIfName(Integer.toString(datas[8], 16));
        //电压需求
        chargingRealtimeData.setRequiredVoltage(
            OffsetCalcUtil.caculate(
                (datas[9] & 0xFF) + (datas[10] << 8 & 0xFF00), 
                TotalVoltageCfg.bitVal, 
                TotalVoltageCfg.offsetVal
            ).shortValue()
        );
        //电流需求
        chargingRealtimeData.setRequiredCurrent(
            OffsetCalcUtil.caculate(
                (datas[11] & 0xFF) + (datas[12] << 8 & 0xFF00), 
                CurrentCfg.bitVal, 
                CurrentCfg.offsetVal
            ).shortValue()
        );
        //充电模式
        chargingRealtimeData.setChargingModel(datas[13]);
        //电池侧充电电压测量值
        chargingRealtimeData.setRealtimeChargingVoltage(
            OffsetCalcUtil.caculate(
                (datas[14] & 0xFF) + (datas[15] << 8 & 0xFF00), 
                TotalVoltageCfg.bitVal, 
                TotalVoltageCfg.offsetVal
            ).shortValue()
        );
        //电池侧充电电流测量值
        chargingRealtimeData.setRealtimeChargingCurrent(
            OffsetCalcUtil.caculate(
                (datas[16] & 0xFF) + (datas[17] << 8 & 0xFF00), 
                CurrentCfg.bitVal, 
                CurrentCfg.offsetVal
            ).shortValue()
        );
        //最高单体动力蓄电池电压及其组号
        chargingRealtimeData.setMaxVoltage((short)((datas[18] & 0xFF) + (datas[19] << 8 & 0xFF00)));
        //当前电荷状态 SOC%
        chargingRealtimeData.setRealtimeSocStatus(datas[20]);
        //估算剩余充电时间(单位：min )
        chargingRealtimeData.setRemainedChargingTime((short)((datas[21] & 0xFF) + (datas[22] << 8 & 0xFF00)));
        //充电电量
        chargingRealtimeData.setChargingNum(
            OffsetCalcUtil.caculate(
                (datas[23] & 0xFF) + (datas[24] << 8 & 0xFF00), 
                ChargingElectricCfg.bitVal, 
                ChargingElectricCfg.offsetVal
            )
        );
        //充电时长
        chargingRealtimeData.setChargingTime((short)((datas[25] & 0xFF) + (datas[26] << 8 & 0xFF00)));
        //最高单体动力蓄电池电压所在编号
        chargingRealtimeData.setMaxVoltageCheckpointNo(datas[27]);
        //最高动力电池温度
        chargingRealtimeData.setMaxTemperature(
            OffsetCalcUtil.caculate(
                datas[28] & 0xFF, 
                TemperatureCfg.bitVal, 
                TemperatureCfg.offsetVal
            ).shortValue()
        );

        //最高温度检测点编号
        chargingRealtimeData.setMaxTemperatureCheckpointNo(datas[29]);
        //最低动力蓄电池温度
        chargingRealtimeData.setMinTemperature(
            OffsetCalcUtil.caculate(
                datas[30] & 0xFF, 
                TemperatureCfg.bitVal, 
                TemperatureCfg.offsetVal
            ).shortValue()                
        );
        //最低动力蓄电池温度检测点编号
        chargingRealtimeData.setMinTemperatureCheckpointNo(datas[31]);
        
        String byteStr = Integer.toBinaryString(datas[32] & 0xFF);
        while(byteStr.length() < 8) {
            byteStr = "0" + byteStr;
        }
        //单体动力蓄电池电压过高/过低
        chargingRealtimeData.setVoltageStatus(JConverter.bit2byte(byteStr.substring(0, 2)));
        //整车动力蓄电池荷电状态SOC 过高/过低
        chargingRealtimeData.setSocStatus(JConverter.bit2byte(byteStr.substring(2, 4))); 
        //动力蓄电池充电过电流
        chargingRealtimeData.setCurrentStatus(JConverter.bit2byte(byteStr.substring(4, 6)));
        //动力蓄电池温度过高
        chargingRealtimeData.setTemperatureStatus(JConverter.bit2byte(byteStr.substring(6, 8)));
        
        byteStr = Integer.toBinaryString(datas[33] & 0xFF);
        while(byteStr.length() < 8) {
            byteStr = "0" + byteStr;
        }
        
        //动力蓄电池绝缘状态
        chargingRealtimeData.setInsulationStatus(JConverter.bit2byte(byteStr.substring(2, 4)));
        //动力蓄电池组输出连接器连接状态
        chargingRealtimeData.setConnectorStatus(JConverter.bit2byte(byteStr.substring(2, 4)));
        //BMS 是否充电允许
        chargingRealtimeData.setConnectorStatus(JConverter.bit2byte(byteStr.substring(4, 8)));
        //原始报文信息
        chargingRealtimeData.setSource(JConverter.bytes2String(msgObject));
        return chargingRealtimeData;
    }
}
