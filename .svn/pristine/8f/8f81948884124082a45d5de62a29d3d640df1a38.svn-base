package com.iycharge.server.ccu.msg.request;

import java.util.Arrays;

import com.iycharge.server.ccu.msg.RequestMsgBase;
import com.iycharge.server.ccu.msg.entity.ChargerRealtimeData;
import com.iycharge.server.ccu.util.BCDUtil;
import com.iycharge.server.ccu.util.JConverter;
import com.iycharge.server.ccu.util.OffsetCalcUtil;
import com.iycharge.server.ccu.util.OffsetCalcUtil.CurrentCfg;
import com.iycharge.server.ccu.util.OffsetCalcUtil.InputVoltageCfg;
import com.iycharge.server.ccu.util.OffsetCalcUtil.TotalVoltageCfg;

/**
 * 充电桩实时数据上传
 * @author bwang
 */
public class ChargerRealtimeDataReq extends RequestMsgBase<ChargerRealtimeData> {
    
    /**
     * @param msgType
     * @param msgObject
     */
    public ChargerRealtimeDataReq(short msgType, byte[] msgObject) {
        super(msgType, msgObject);
    }
        
    @Override
    public ChargerRealtimeData parse() {
        ChargerRealtimeData chargerRealtimeData = new ChargerRealtimeData();
        
        byte[] datas = Arrays.copyOfRange(msgObject, 4, msgObject.length - 2);
        
        int index =0;
        //充电桩编码
        chargerRealtimeData.setChargerNo(BCDUtil.bcd2Str(Arrays.copyOfRange(datas, index, index += 8)));
        //充电接口
        chargerRealtimeData.setIfName(Integer.toString(datas[index], 16));
        //充电接口类型
        chargerRealtimeData.setIfType(Integer.toString(datas[++index], 16));
        //充电接口工作状态
        chargerRealtimeData.setIfWorkStatus(Integer.toString(datas[++index], 16));
        //车辆连接状态
        chargerRealtimeData.setCarConnStatus(datas[++index]);
        //输入 A相电压
        chargerRealtimeData.setInputAv(
            OffsetCalcUtil.caculate(
                (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00), 
                InputVoltageCfg.bitVal, 
                InputVoltageCfg.offsetVal
            ).shortValue()
        );
        //输入 B相电压
        chargerRealtimeData.setInputBv(
            OffsetCalcUtil.caculate(
                (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00), 
                InputVoltageCfg.bitVal, 
                InputVoltageCfg.offsetVal
            ).shortValue()
        );
        //输入 C 相电压
        chargerRealtimeData.setInputCv(
            OffsetCalcUtil.caculate(
                (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00), 
                InputVoltageCfg.bitVal, 
                InputVoltageCfg.offsetVal
            ).shortValue()
        );
        //输出电压
        chargerRealtimeData.setOutputVoltage(
            OffsetCalcUtil.caculate(
                (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00), 
                TotalVoltageCfg.bitVal, 
                TotalVoltageCfg.offsetVal
            ).shortValue()
        );
        //输出电流
        chargerRealtimeData.setOutputCurrent(
            OffsetCalcUtil.caculate(
                (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00), 
                CurrentCfg.bitVal, 
                CurrentCfg.offsetVal
            ).shortValue()
        );
        //电表表底
        chargerRealtimeData.setInitElectricity((short)(
            (datas[++index] & 0xFF) + (datas[++index] << 8 & 0xFF00) + (datas[++index] << 16 & 0xFF0000) + (datas[++index] << 24 & 0xFF000000)
        ));
        //生命周期
        chargerRealtimeData.setLifecycle((short)(datas[++index] & 0xFF));
        //原始报文信息
        chargerRealtimeData.setSource(JConverter.bytes2String(msgObject));
        
        return chargerRealtimeData;
    }

}
